package com.example.portfolio.exemplos.features.exampleDataRetry


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.annotations.Async
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException


interface PostRepository {
    /**
     * Retorna lista de posts.
     * Em caso de falha, retorna Result.failure(exception).
     */
    suspend fun fetchPosts(): Result<List<Post>>

    /**
     * Retorna um post por id.
     * Em caso de falha, retorna Result.failure(exception).
     */
    suspend fun fetchPostById(id: Int): Result<Post>
}

class PostRepositoryImpl(
    private val api: ApiService,
    private val maxRetries: Int = 2 // máximo de retries (além da tentativa inicial)
) : PostRepository {

    override suspend fun fetchPosts(): Result<List<Post>> = withContext(Dispatchers.IO) {
        executeWithRetry(
            call = {
                api.getPosts().map { it.toDomain() }
            }
        )
    }

    override suspend fun fetchPostById(id: Int): Result<Post> = withContext(Dispatchers.IO) {
        executeWithRetry(
            call = {
                api.getPostById(id).toDomain()
            }
        )
    }

    /**
     * Executa a chamada com até (1 + maxRetries) tentativas.
     * Regras:
     * - UnknownHostException (sem internet): não faz retry, retorna erro imediatamente.
     * - 4xx (ClientRequestException): não faz retry (erro do cliente).
     * - 5xx (ServerResponseException) e timeouts: permite retry até o limite.
     * - Outras exceções: permite retry até o limite.
     */
    private suspend fun <T> executeWithRetry(call: suspend () -> T): Result<T> {
        var attempt = 0
        var lastError: Throwable? = null

        val totalAttempts = 1 + maxRetries

        while (attempt < totalAttempts) {
            try {
                return Result.success(call())
            } catch (ce: CancellationException) {
                // Propaga cancelamento de coroutines
                throw ce
            } catch (ex: Throwable) {
                // Sem internet: não retentar
                if (ex is UnknownHostException) {
                    return Result.failure(ex)
                }
                // 4xx: não retentar
                if (ex is ClientRequestException) {
                    return Result.failure(ex)
                }

                lastError = ex
                attempt++

                // Se atingiu o limite de tentativas, retorna falha
                if (attempt >= totalAttempts) {
                    return Result.failure(lastError ?: ex)
                }

                // Pequeno backoff (opcional). Comente se não desejar.
                // delay(300L * attempt)
            }
        }

        return Result.failure(lastError ?: IllegalStateException("Erro desconhecido"))
    }
}

interface ApiService {
    suspend fun getPosts(): List<PostDto>
    suspend fun getPostById(id: Int): PostDto
}

class ApiServiceImpl(
    private val clientProvider: HttpClientProvider = HttpClientProvider,
    private val baseUrl: String = "https://jsonplaceholder.typicode.com"
) : ApiService {

    private val client get() = clientProvider.client

    override suspend fun getPosts(): List<PostDto> {
        // GET /posts
        val response = client.get("$baseUrl/posts")
        return response.body()
    }

    override suspend fun getPostById(id: Int): PostDto {
        // GET /posts/{id}
        val response = client.get("$baseUrl/posts/$id")
        return response.body()
    }
}

object HttpClientProvider {

    val client: HttpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 15_000
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}

@Serializable
data class PostDto(
    @SerialName("userId") val userId: Int,
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String
)

// Modelo de domínio simples (se desejar manter apenas no data, pode ficar aqui também)
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun PostDto.toDomain(): Post = Post(
    id = id,
    userId = userId,
    title = title,
    body = body
)


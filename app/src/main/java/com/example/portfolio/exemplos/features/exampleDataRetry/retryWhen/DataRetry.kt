package com.example.portfolio.exemplos.features.exampleDataRetry.retryWhen


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Serializable
data class PostDto(
    @SerialName("userId") val userId: Int,
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String
)

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
        val response = client.get("$baseUrl/posts")
        return response.body()
    }

    override suspend fun getPostById(id: Int): PostDto {
        val response = client.get("$baseUrl/posts/$id")
        return response.body()
    }
}

sealed class NetworkError {
    object NoInternet : NetworkError() // UnknownHostException
    data class ClientError(val statusCode: Int, val message: String?) : NetworkError() // 4xx
    data class ServerError(val statusCode: Int, val message: String?) : NetworkError() // 5xx
    object Timeout : NetworkError()
    data class Unknown(val cause: Throwable) : NetworkError()
}

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val error: NetworkError) : NetworkResult<Nothing>()
}

interface PostRepository {
    fun fetchPosts(): Flow<NetworkResult<List<Post>>>
    fun fetchPostById(id: Int): Flow<NetworkResult<Post>>
}


class PostRepositoryImpl(
    private val api: ApiService,
    private val maxRetries: Long = 2L // máximo de retries além da primeira tentativa
) : PostRepository {

    override fun fetchPosts() = flow<NetworkResult<List<Post>>> {
        val data = api.getPosts().map { it.toDomain() }
        emit(NetworkResult.Success(data))
    }
        .retryWhen { cause, attempt ->
            // attempt é 0-based: 0 => primeiro retry, 1 => segundo retry
            if (attempt >= maxRetries) return@retryWhen false

            val shouldRetry = shouldRetry(cause)
            if (shouldRetry) {
                // Backoff simples opcional
                val delayMs = 300L * (attempt + 1)
                delay(delayMs)
            }
            shouldRetry
        }
        .catch { e ->
            emit(NetworkResult.Error(mapThrowable(e)))
        }
        .flowOn(Dispatchers.IO)

    override fun fetchPostById(id: Int) = flow<NetworkResult<Post>> {
        val data = api.getPostById(id).toDomain()
        emit(NetworkResult.Success(data))
    }
        .retryWhen { cause, attempt ->
            if (attempt >= maxRetries) return@retryWhen false
            val shouldRetry = shouldRetry(cause)
            if (shouldRetry) {
                val delayMs = 300L * (attempt + 1)
                delay(delayMs)
            }
            shouldRetry
        }
        .catch { e ->
            emit(NetworkResult.Error(mapThrowable(e)))
        }
        .flowOn(Dispatchers.IO)

    /**
     * Regras de retry:
     * - Não retry: UnknownHostException (sem internet), ClientRequestException (4xx)
     * - Retry: ServerResponseException (5xx), timeouts, e erros desconhecidos (transitórios)
     */
    private fun shouldRetry(cause: Throwable): Boolean = when (cause) {
        is UnknownHostException -> false
        is ClientRequestException -> false // 4xx
        is ServerResponseException -> true // 5xx
        is HttpRequestTimeoutException,
        is SocketTimeoutException -> true

        else -> true // outros erros possivelmente transitórios
    }

    /**
     * Mapeia Throwable -> NetworkError para emissão final (Error)
     */
    private fun mapThrowable(ex: Throwable): NetworkError = when (ex) {
        is UnknownHostException -> NetworkError.NoInternet
        is ClientRequestException -> NetworkError.ClientError(ex.response.status.value, ex.message)
        is ServerResponseException -> NetworkError.ServerError(ex.response.status.value, ex.message)
        is HttpRequestTimeoutException,
        is SocketTimeoutException -> NetworkError.Timeout

        else -> NetworkError.Unknown(ex)
    }
}
package com.example.portfolio.exemplos.core.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        json: Json,
//        navigationEventBus: NavigationEventBusInterface
    ): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(json)
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }

//            // Interceptor para tratar erros Exemplo: 401, 400
//            install(HttpCallValidator) {
//                httpValidateResponse(navigationEventBus)
//                exceptionResponse(navigationEventBus)
//            }

            defaultRequest {
                header("Content-Type", "application/json")
            }
        }
    }
}

//private fun HttpCallValidatorConfig.exceptionResponse(navigationEventBus: NavigationEventBusInterface) {
//    handleResponseExceptionWithRequest { exception, _ ->
//        when (exception) {
//            is ClientRequestException -> {
//                when (exception.response.status.value) {
//                    400, 401 -> {
//                        CoroutineScope(Dispatchers.Main).launch {
//                            navigationEventBus.emitEvent(NavigationEvent.NavigateToHome)
//                        }
//                    }
//
//                    else -> Unit
//                }
//            }
//        }
//    }
//}
//
//private fun HttpCallValidatorConfig.httpValidateResponse(navigationEventBus: NavigationEventBusInterface) {
//    validateResponse { response ->
//        when (response.status.value) {
//            400, 401 -> {
//                CoroutineScope(Dispatchers.Main).launch {
//                    navigationEventBus.emitEvent(NavigationEvent.NavigateToHome)
//                }
//            }
//
//            else -> Unit
//        }
//    }
//}
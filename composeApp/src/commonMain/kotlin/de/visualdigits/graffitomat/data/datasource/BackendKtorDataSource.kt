package de.visualdigits.graffitomat.data.datasource

import co.touchlab.kermit.Logger
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.graffitomat.data.http.safeCall
import de.visualdigits.graffitomat.data.model.CreateGraffitoRequestDto
import de.visualdigits.graffitomat.data.model.RequestMethod
import de.visualdigits.graffitomat.data.provider.HostUrlProvider
import de.visualdigits.graffitomat.domain.model.errorhandling.DataError
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackendKtorDataSource(
    private val httpClient: HttpClient,
    private val hostUrlProvider: HostUrlProvider
) : BackendDataSource {

    override suspend fun createGraffitoRequest(request: CreateGraffitoRequestDto): Result<Unit, DataError.Remote> = withContext(Dispatchers.IO) {
        call(
            url = "${hostUrlProvider.hostUrl}/create",
            body = request,
            method = RequestMethod.POST
        )
    }

    @OptIn(InternalAPI::class)
    override suspend fun call(
        url: String,
        parameters: Map<String, String>,
        body: Any?,
        method: RequestMethod
    ): Result<Unit, DataError.Remote> {
        Logger.i("Calling url: $url?${parameters.toList().joinToString("&") { (key, value) -> "$key=$value" }}")
        return when (method) {
            RequestMethod.GET -> {
                safeCall<Unit> {
                    httpClient.get(urlString = url) {
                        parameters.forEach { (key,value) ->
                            parameter(key, value)
                        }
                        body?.also { b -> setBody(b) }
                    }
                }
            }
            RequestMethod.PUT -> {
                safeCall<Unit> {
                    httpClient.put(urlString = url) {
                        parameters.forEach { (key,value) ->
                            parameter(key, value)
                        }
                        body?.also { b -> setBody(b) }
                    }
                }
            }
            RequestMethod.POST -> {
                safeCall<Unit> {
                    httpClient.post(urlString = url) {
                        parameters.forEach { (key,value) ->
                            parameter(key, value)
                        }
                        body?.also { b -> setBody(b) }
                    }
                }
            }
            RequestMethod.DELETE -> {
                safeCall<Unit> {
                    httpClient.delete(urlString = url) {
                        parameters.forEach { (key,value) ->
                            parameter(key, value)
                        }
                        body?.also { b -> setBody(b) }
                    }
                }
            }
        }
    }
}

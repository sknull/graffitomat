package de.visualdigits.graffitomat.data.datasource

import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.graffitomat.data.model.CreateGraffitoRequestDto
import de.visualdigits.graffitomat.data.model.RequestMethod
import de.visualdigits.graffitomat.domain.model.errorhandling.DataError
import java.awt.image.BufferedImage

interface BackendDataSource {

    suspend fun createGraffitoRequest(request: CreateGraffitoRequestDto): Result<Unit, DataError.Remote>

    suspend fun call(
        url: String,
        parameters: Map<String, String> = mapOf(),
        body: Any? = null,
        method: RequestMethod = RequestMethod.GET
    ): Result<Unit, DataError.Remote>
}

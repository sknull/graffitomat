package de.visualdigits.graffitomat.domain.repository

import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.graffitomat.domain.model.errorhandling.DataError
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import java.awt.image.BufferedImage

interface BackendRepository {

    suspend fun createGraffitoRequest(request: CreateRequestForm): Result<Unit, DataError.Remote>
}

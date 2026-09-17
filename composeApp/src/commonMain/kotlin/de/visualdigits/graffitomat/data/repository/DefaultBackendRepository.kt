package de.visualdigits.graffitomat.data.repository

import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.graffitomat.data.datasource.BackendDataSource
import de.visualdigits.graffitomat.data.mapper.toCreateGraffitoRequestDto
import de.visualdigits.graffitomat.domain.model.errorhandling.DataError
import de.visualdigits.graffitomat.domain.repository.BackendRepository
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage

class DefaultBackendRepository(
    private val backendDataSource: BackendDataSource
) : BackendRepository {

    override suspend fun createGraffitoRequest(request: CreateRequestForm): Result<Unit, DataError.Remote> = withContext(Dispatchers.IO) {
        backendDataSource.createGraffitoRequest(request.toCreateGraffitoRequestDto())
    }
}

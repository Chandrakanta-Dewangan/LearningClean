package com.learning.data.core.dao

import com.learning.data.core.network.api.ClassifiedListingApi
import com.learning.data.core.network.toDomain
import com.learning.domain.model.Cakes
import com.learning.domain.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClassifiedListingDao @Inject constructor(
    private val api: ClassifiedListingApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getAllCakes(): Result<List<Cakes>> =
        withContext(ioDispatcher) {
            try {
                val result = api.getAllCakes()
                Result.Success(result.toDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

}

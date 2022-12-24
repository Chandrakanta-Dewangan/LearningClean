package com.learning.domain.repository

import com.learning.domain.model.Cakes
import com.learning.domain.model.Result

interface ClassifiedListingRepository {
    suspend fun getAllCakes(): Result<List<Cakes>>
}
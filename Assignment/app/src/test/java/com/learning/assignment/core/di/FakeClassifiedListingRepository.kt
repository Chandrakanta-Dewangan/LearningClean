package com.learning.assignment.core.di

import com.learning.domain.model.Cakes
import com.learning.domain.model.Result
import com.learning.domain.repository.ClassifiedListingRepository
import com.learning.assignment.core.utils.FakeDataGenerator

class FakeClassifiedListingRepository : ClassifiedListingRepository {
    private val cakes = FakeDataGenerator.getFakeCakes()
    override suspend fun getAllCakes(): Result<List<Cakes>> {
        return Result.Success(cakes)
    }
}
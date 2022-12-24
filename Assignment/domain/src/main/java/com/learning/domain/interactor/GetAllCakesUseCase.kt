package com.learning.domain.interactor

import com.learning.domain.interactor.base.UseCase
import com.learning.domain.model.Cakes
import com.learning.domain.model.OrderType
import com.learning.domain.model.Result
import com.learning.domain.repository.ClassifiedListingRepository
import javax.inject.Inject

class GetAllCakesUseCase @Inject constructor(
    private val repo: ClassifiedListingRepository
) : UseCase<Result<List<Cakes>>, GetAllCakesUseCase.Params>() {

    override suspend fun execute(params: Params?): Result<List<Cakes>> {
        params!!
        return when (val result = repo.getAllCakes()) {
            is Result.Error -> {
                result
            }
            is Result.Success<List<Cakes>> -> {
                val sortedList = when (params.orderType) {
                    OrderType.Ascending -> result.data.sortedBy { it.title }
                    OrderType.Descending -> result.data.sortedByDescending { it.title }
                }
                Result.Success(sortedList)
            }
        }
    }

    data class Params(val orderType: OrderType)
}
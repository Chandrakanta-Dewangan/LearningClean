package com.learning.assignment.interactor

import com.google.common.truth.Truth.assertThat
import com.learning.domain.interactor.GetAllCakesUseCase
import com.learning.domain.model.Cakes
import com.learning.domain.model.Result
import com.learning.domain.model.OrderType
import com.learning.assignment.core.di.FakeClassifiedListingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllCakesUseCaseTest {

    private lateinit var getAllCakesUseCase: GetAllCakesUseCase
    private lateinit var fakeRepository: FakeClassifiedListingRepository

    @Before
    fun setUp() {
        fakeRepository = FakeClassifiedListingRepository()
        getAllCakesUseCase = GetAllCakesUseCase(fakeRepository)
    }

    @Test
    fun `Get_Cakes_Count`() = runBlocking {

        val result = getAllCakesUseCase.execute(GetAllCakesUseCase.Params(OrderType.Ascending))

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val cakesResult = result as Result.Success<List<Cakes>>

        assertThat(cakesResult.data).isNotNull()

        val cakes = cakesResult.data

        assertThat(cakes).isNotNull()

        assertThat(cakes).hasSize(5)
    }

    @Test
    fun `Get_Cakes_Title`() = runBlocking {

        val result = getAllCakesUseCase.execute(GetAllCakesUseCase.Params(OrderType.Ascending))

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val cakesResult = result as Result.Success<List<Cakes>>

        assertThat(cakesResult.data).isNotNull()

        val cakes = cakesResult.data

        assertThat(cakes).isNotNull()

        for (i in cakes.indices) {
            assertThat(cakes[i].title).isEqualTo("Lemon cheesecake ${i + 1}")
        }
    }

    @Test
    fun `Get_Cakes_Sort_By_Name_Ascending`() = runBlocking {

        val result = getAllCakesUseCase.execute(GetAllCakesUseCase.Params(OrderType.Ascending))

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val cakesResult = result as Result.Success<List<Cakes>>

        assertThat(cakesResult.data).isNotNull()

        val cakes = cakesResult.data

        assertThat(cakes).isNotNull()

        for(i in 0..cakes.size - 2) {
            assertThat(cakes[i].title).isLessThan(cakes[i+1].title)
        }
    }

    @Test
    fun `Get_Cakes_Sort_By_Name_Descending`() = runBlocking {

        val result = getAllCakesUseCase.execute(GetAllCakesUseCase.Params(OrderType.Descending))

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val cakesResult = result as Result.Success<List<Cakes>>

        assertThat(cakesResult.data).isNotNull()

        val cakes = cakesResult.data

        assertThat(cakes).isNotNull()

        for(i in 0..cakes.size - 2) {
            assertThat(cakes[i].title).isGreaterThan(cakes[i+1].title)
        }
    }
}
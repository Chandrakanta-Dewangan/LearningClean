package com.learning.assignment.core.utils

import com.learning.domain.model.Cakes
import java.util.*

object FakeDataGenerator {

    fun getFakeCakes(): List<Cakes> {
        val cakes = mutableListOf<Cakes>()
        for (i in 1 until 6) {
            cakes.add(
                Cakes(
                    title = "Lemon cheesecake $i",
                    desc = "A cheesecake made of lemon",
                    image="https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"
                )
            )
        }
        return cakes
    }


}
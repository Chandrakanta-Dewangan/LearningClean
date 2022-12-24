package com.learning.data.core.network

import com.learning.data.core.network.api.Result
import com.learning.domain.model.Cakes

fun Set<Result>.toDomain(): MutableList<Cakes> {
    val result = mutableListOf<Cakes>()
    this.forEach {
        result.add(it.toDomain())
    }

    return result
}

fun Result.toDomain(): Cakes = Cakes(
    title = title,
    desc = desc,
    image = image
)
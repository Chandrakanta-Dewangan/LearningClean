package com.learning.domain.model

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}

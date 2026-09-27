package com.mybarber.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class Barber(
    val id: String,
    val name: String,
    val rating: Float,
    val reviewCount: Int,
    val address: String,
    val distance: String,
    val contactNumber: String,
    @DrawableRes val profileImage: Int,
    val services: List<BarberService>,
    val hours: List<OperatingHours>,
    val reviews: List<BarberReview>,
    val ratingDistribution: Map<Int, Float>, // Star rating to percentage (0.0 to 1.0)
    @DrawableRes val gallery: List<Int>
)

@Serializable
data class BarberService(
    val name: String,
    val price: String,
    @DrawableRes val icon: Int? = null
)

@Serializable
data class OperatingHours(
    val days: String,
    val time: String
)

@Serializable
data class BarberReview(
    val authorName: String,
    val date: String,
    val rating: Int,
    val comment: String,
    @DrawableRes val authorAvatar: Int,
    val likes: Int,
    val dislikes: Int
)

package model

import java.util.Date

data class BodyTemperature(
    val creationDate: Date,
    val startDate: Date,
    val endDate: Date,
    val value: Double,
    val unit: String,
)

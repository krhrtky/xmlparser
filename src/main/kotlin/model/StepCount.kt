package model

import java.util.Date

data class StepCount(
    val value: Int,
    val unit: String,
    val creationDate: Date,
    val startDate: Date,
    val endDate: Date,
)

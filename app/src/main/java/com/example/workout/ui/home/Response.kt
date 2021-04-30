package com.example.workout.ui.home

data class Response (
    val articles: List<ResponseItem>,
    val totalResults: Int,
    val status: String
)
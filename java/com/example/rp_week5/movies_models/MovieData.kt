package com.example.rp_week5.movies_models

data class MovieData(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
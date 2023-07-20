package com.example.moviesapp.model

data class MovieResponse(
    val page: Int,
    val next: String,
    val entries: Int,
    val results: List<MovieData>
)


data class MovieData(
    val _id: String,
    val id: String,
    val primaryImage: ImageData?,
    val titleType: TitleTypeData,
    val titleText: TitleTextData,
    val originalTitleText: TitleTextData,
    val releaseYear: YearRangeData,
    val releaseDate: ReleaseDateData?
)

data class ImageData(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    val caption: CaptionData
)

data class CaptionData(
    val plainText: String
)

data class TitleTypeData(
    val text: String,
    val id: String,
    val isSeries: Boolean,
    val isEpisode: Boolean
)

data class TitleTextData(
    val text: String
)

data class YearRangeData(
    val year: Int,
    val endYear: Int?
)

data class ReleaseDateData(
    val day: Int?,
    val month: Int?,
    val year: Int
)

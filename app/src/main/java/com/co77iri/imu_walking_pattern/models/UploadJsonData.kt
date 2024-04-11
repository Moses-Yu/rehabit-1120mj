package com.co77iri.imu_walking_pattern.models

data class UploadJsonData(
    val id: String,
    val title: String,
    val subtitle: String,
    val totaltime: Int,
    val totalwalk: Int,
    val left: String,
    val right: String,
    val cadence: Int,
    val length: Double,
    val height: Double,
    val step: Double,
    val avg: Int,
    val gait: Int
)
package com.co77iri.imu_walking_pattern.models

import kotlinx.serialization.Serializable

@Serializable
data class ProfileData(
    val hospital: String,
    val name: String,
    val phoneNumber: String,
    val height: Double,
    val weight: Double,
    val birthDate: String,
    val caliMinDistance: Int,
    val caliMaxValue: Double,
    val lastAccessTime: String,
    val filename: String = ""
)

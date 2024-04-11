package com.co77iri.imu_walking_pattern.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

class ProfileViewModel_old(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>()
    var profileList by mutableStateOf(emptyList<ProfileTitleInfo>())
    val selectedProfileData = mutableStateOf<ProfileInputData?>(null)

    fun scanProfilesDirectory(filesDir: File) {

        viewModelScope.launch(Dispatchers.IO) {
            val newProfileList = mutableListOf<ProfileTitleInfo>()

            val profilesDirectory = File(filesDir, "profiles")
            if( profilesDirectory.exists() ) {
                val files = profilesDirectory.listFiles()

                files?.forEach { file ->
                    Log.d("test", file.toString())
                    val profileTitleInfo = parseFileNameToProfileTitleInfo(file.name)
                    if( profileTitleInfo != null) {
                        newProfileList.add(profileTitleInfo)
                    }
                }

                profileList = newProfileList
            }
        }
    }

    fun createJsonFile( data: ProfileInputData) {
        val sdf = SimpleDateFormat("yyyyMMddHHmm")
        val currentDataAndTime: String = sdf.format(Date())
        val fileName = "${currentDataAndTime}_${data.hospital}_${data.name}.json"

        try {
            val json = JSONObject()
            json.put("name", data.name)
            json.put("hospital", data.hospital)
            json.put("tel", data.tel)
            json.put("birthdate", data.birthdate)
            json.put("height", data.height)
            json.put("weight", data.weight)
            json.put("gender", data.gender)
            json.put("lastVisit", data.lastVisit)
            json.put("cal_minDistance", data.cal_min_distance)
            json.put("cal_minHeight", data.cal_min_height)

            // 1. profiles 폴더의 경로를 얻습니다.
            val profilesDir = File(context.filesDir, "profiles")

            // 2. profiles 폴더가 없다면 폴더를 생성합니다.
            if (!profilesDir.exists()) {
                profilesDir.mkdirs()
                Log.d("test", "profiles dir not exist. created.")
            } else {
                Log.d("test", "profiles dir exist.")
            }

            // 해당 폴더에 파일을 생성합니다.
            val file = File(profilesDir, fileName)
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(json.toString().toByteArray())
            fileOutputStream.close()

            scanProfilesDirectory(context.filesDir)
        } catch (e: Exception) {
            Log.e("JSONFile", "Error in Writing: $e")
        }
    }

    private fun parseFileNameToProfileTitleInfo(fileName: String): ProfileTitleInfo? {
        val pattern = Pattern.compile("^(.+)_(.+)_(.+).json$")
        val matcher = pattern.matcher(fileName)

        return if( matcher.find() ) {
            val hospital = matcher.group(2)
            val name = matcher.group(3)
            val lastVisit = matcher.group(1)

            if( name != null && hospital != null && lastVisit != null) {
                ProfileTitleInfo(name, hospital, lastVisit)
            } else {
                null
            }
        } else {
            null
        }
    }

    fun generateFileNameFromProfileInfo(profile: ProfileTitleInfo): String {
        return "${profile.lastVisit}_${profile.hospital}_${profile.name}.json"
    }

    data class ProfileTitleInfo(
        val name: String,
        val hospital: String,
        val lastVisit: String
    )

    data class ProfileInputData(
        val name: String,
        val hospital: String,
        val lastVisit: String,
        val tel: String,
        val birthdate: String,
        val height: Double,
        val weight: Double,
        val gender: String,
        val cal_min_height: Double = 40.0,
        val cal_min_distance: Int = 10,
    )

    fun readProfileData(profileFileName: String): ProfileInputData? {
        val file = File(context.filesDir, "profiles/" + profileFileName)
        Log.d("test333", file.path.toString())
        return if (file.exists()) {
            val content = file.readText()
            val json = JSONObject(content)
            ProfileInputData(
                name = json.getString("name"),
                hospital = json.getString("hospital"),
                tel = json.getString("tel"),
                birthdate = json.getString("birthdate"),
                height = json.getString("height").toDouble(),
                weight = json.getString("weight").toDouble(),
                gender = json.getString("gender"),
                lastVisit = json.getString("lastVisit"),
                cal_min_distance = json.getString("cal_minDistance").toInt(),
                cal_min_height = json.getString("cal_minHeight").toDouble()
            )
        } else {
            return null
        }
    }

    fun deleteProfile(fileName: String): Boolean {
        val file = File(context.filesDir, "profiles/" + fileName)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }
}
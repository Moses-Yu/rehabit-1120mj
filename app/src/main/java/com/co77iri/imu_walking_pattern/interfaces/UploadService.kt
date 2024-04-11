package com.co77iri.imu_walking_pattern.interfaces

import com.co77iri.imu_walking_pattern.models.UploadJsonData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadService {
    @Multipart
    @POST("upload")
//    fun uploadFiles(@Part requestBody: MultipartBody): Call<ResponseBody>
    fun uploadCsv(@Part files: List<MultipartBody.Part>): Call<Void>
}

interface JsonUploadService {
    @Multipart
    @POST("/upload_test")
    fun uploadFiles(
        @Part file_l: MultipartBody.Part,
        @Part file_r: MultipartBody.Part,
        @Part("data") data: RequestBody
    ): Call<ResponseBody>
//    @POST("upload_json")
//    fun uploadData(@Body data: List<UploadJsonData>): Call<Void>
}

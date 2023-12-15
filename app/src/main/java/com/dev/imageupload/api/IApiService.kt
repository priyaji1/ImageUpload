package com.dev.imageupload.api

import com.dev.imageupload.model.ImageUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface IApiService {

    @Multipart
    @POST("upload")
    fun uploadImageApi(
        @Part image: MultipartBody.Part,
        @Part("key") apiKey: RequestBody?
    ): Call<Any>



}
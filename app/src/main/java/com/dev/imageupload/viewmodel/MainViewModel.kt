package com.dev.imageupload.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.imageupload.api.APICallHandler
import com.dev.imageupload.api.APICallManager
import com.dev.imageupload.api.APIType
import com.dev.imageupload.model.Errors
import com.dev.imageupload.model.ImageUploadResponse

class MainViewModel : ViewModel(), APICallHandler<Any> {
    var imageUploadSuccess = MutableLiveData<ImageUploadResponse>()
    var error =
        MutableLiveData<Errors>()

    override fun onSuccess(apiType: APIType, response: Any?) {
        when (apiType) {

            APIType.IMAGE_UPLOAD -> {
                val res11 = response as ImageUploadResponse
                imageUploadSuccess.value = res11

            }

            else -> {}
        }
    }

    override fun onFailure(apiType: APIType, code: Int, error: Errors) {
        this.error.value = error
    }

    fun uploadPhoto(image: String) {
        val apiCallManager =
            APICallManager(APIType.IMAGE_UPLOAD, this)
        apiCallManager.uploadImageApi(image)
    }
}
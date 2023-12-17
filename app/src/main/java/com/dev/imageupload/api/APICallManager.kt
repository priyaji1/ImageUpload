package com.dev.imageupload.api
import com.app.treadmap.api.APIClient
import com.dev.imageupload.model.Errors
import com.dev.imageupload.model.ImageUploadResponse
import com.dev.imageupload.util.AppConstant
import com.dev.imageupload.util.AppUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class APICallManager<T>(
    private val mCallType: APIType,
    private val mAPICallHandler: APICallHandler<T>
) : retrofit2.Callback<T> {





    override fun onResponse(call: Call<T>, response: Response<T>) {

        if (response.code() == APIStatusCode.OK ) {
            val body = response.body()

            if (body != null) {
                mAPICallHandler.onSuccess(mCallType, body)
            }
        }
        else {
            val errors = Errors()
            val errorMessage ="Something went wrong"
            errors.errorMessage = errorMessage
            mAPICallHandler.onFailure(mCallType, response.code(), errors)
        }
    }

    override fun onFailure(call: Call<T>, throwable: Throwable) {
        val errorCode = 0
        val message: String? =
            if (throwable is UnknownHostException || throwable is SocketException || throwable is SocketTimeoutException) {
                "Something went wrong"
            } else {
                throwable.message
            }


        val errors = Errors()
        errors.errorMessage = message
        mAPICallHandler.onFailure(mCallType, errorCode, errors)
    }

    fun uploadImageApi(image:String) {

       val imageMultipart = AppUtil.getMediaMultiPart(image, AppConstant.IMAGE)

        val keyBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), AppConstant.API_DEFAULT_KEY)

        val call = APIClient.getClient().uploadImageApi(imageMultipart, keyBody)
        call.enqueue(this@APICallManager as retrofit2.Callback<ImageUploadResponse>)

    }
}

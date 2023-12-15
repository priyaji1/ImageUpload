package com.dev.imageupload.api

import com.dev.imageupload.model.Errors

interface APICallHandler<T> {

    fun onSuccess(apiType: APIType, response: T?)

    fun onFailure(apiType: APIType, code: Int, error: Errors)
}
package com.dev.imageupload.util

import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.Locale

object AppUtil {

    fun getMediaMultiPart(
        profileImagePath: String,
        key: String
    ): MultipartBody.Part {
        val file = File(profileImagePath)
        val requestFileProfilePic =
            RequestBody.create(getMimeType(profileImagePath).toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(key, file.name, requestFileProfilePic)
    }
    fun getMimeType(url: String?): String {
        val path = Uri.fromFile(File(url))
        val type_map = MimeTypeMap.getSingleton()
        var extension = MimeTypeMap.getFileExtensionFromUrl(path.toString())
        extension = extension.toLowerCase(Locale.ROOT)
        if (extension.contains(".")) {
            extension = extension.substring(extension.lastIndexOf("."))
        }
        return type_map.getMimeTypeFromExtension(extension).toString()
    }

}
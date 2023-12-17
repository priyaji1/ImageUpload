package com.dev.imageupload.util

import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import com.dev.imageupload.base.BaseActivity
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

     fun getFileName(uri: Uri,activity:BaseActivity): String {
        val cursor = activity.contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor?.moveToFirst()
        val name = cursor?.getString(nameIndex ?: 0)
        cursor?.close()
        return name ?: ""
    }

     fun getFileType(uri: Uri,activity:BaseActivity): String {
        val type = activity.contentResolver.getType(uri)
        return type ?: ""
    }

}
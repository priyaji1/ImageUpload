package com.dev.imageupload.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dev.imageupload.base.BaseActivity
import com.dev.imageupload.databinding.ActivityMainBinding
import com.dev.imageupload.util.AppUtil
import com.dev.imageupload.util.PathUtil.getPath
import com.dev.imageupload.viewmodel.MainViewModel


class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var selectedImageUri: Uri? = null
    private lateinit var ui: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        setListener()
        setObserable()
    }




    private fun setListener() {
        ui.apply {
            btnChooseImage.setOnClickListener {
                openImageChooser()
            }
            btnPreview.setOnClickListener {
                displaySelectedImage(selectedImageUri)
            }
            btnSubmit.setOnClickListener {

                submitImage()
            }
        }

    }

    private fun setObserable() {
        viewModel.imageUploadSuccess.observe(this, Observer {
            hideProgressBar()

        })
        viewModel.error.observe(this, Observer {
            hideProgressBar()
            showToast(it.errorMessage.toString())
        })
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private fun submitImage() {
        if (selectedImageUri != null) {
            val filePath = getPath(this, selectedImageUri!!)
            showProgressBar()
            filePath?.let { viewModel.uploadPhoto(it) }
        } else {
            showToast("Please choose an image first.")
        }

    }

    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    selectedImageUri = uri

                }
            }
        }

    private fun displaySelectedImage(uri: Uri?) {
        ui.apply {
            imagePreview.visibility = View.VISIBLE
            tvFileInfo.visibility = View.VISIBLE

            imagePreview.setImageURI(uri)

            val fileName = uri?.let { AppUtil.getFileName(it,this@MainActivity) }
            val fileType = uri?.let { AppUtil.getFileType(it,this@MainActivity) }

            tvFileInfo.text = "File: $fileName\nType: $fileType"
        }

    }




    private fun showToast(message: String) {
        // Display a toast message (You can implement your own toast mechanism)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


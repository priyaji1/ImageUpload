package com.dev.imageupload.base


import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent

import androidx.appcompat.app.AppCompatActivity
import com.dev.imageupload.R

import com.dev.imageupload.util.AppUtil
import java.util.Objects


abstract class BaseActivity : AppCompatActivity() {


    private var progressDialog: ProgressDialog? = null

    open fun layoutRes(): Int {
        return 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {

            super.onCreate(savedInstanceState)
            setContentView(layoutRes())

        } catch (e: Exception) {
            println("SplashActivity ${e.message}")
        }
    }

    open fun showProgressBar(msg: String, isCancel: Boolean) {

        hideProgressBar()

        if (!this@BaseActivity.isFinishing) {

            progressDialog = ProgressDialog.show(this@BaseActivity, "", msg, false)

            progressDialog?.setOnKeyListener(DialogInterface.OnKeyListener { dialog: DialogInterface?, keyCode: Int, event: KeyEvent? ->
                hideProgressBar()
                false
            })

            progressDialog?.setCanceledOnTouchOutside(isCancel)

            progressDialog?.setContentView(R.layout.progress_layout)

            Objects.requireNonNull(progressDialog?.window)
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    /**
     * Show progress bar with single ocurrent object
     */
    open fun showProgressBar() {
        hideProgressBar()
        if (!this@BaseActivity.isFinishing()) {
            progressDialog = ProgressDialog.show(this@BaseActivity, "", "", true)
            if (progressDialog != null) {
                progressDialog?.setCanceledOnTouchOutside(true)
                progressDialog?.setContentView(R.layout.progress_layout)
                Objects.requireNonNull(progressDialog?.getWindow())
                    ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }




    open fun hideProgressBar() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }













}
package com.project.template.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class CommonUtils {

    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun showSnackBar(view: View, displayText: String) {
            Snackbar.make(view, displayText, Snackbar.LENGTH_LONG).show()
        }

        fun showOrHideProgressBar(progressBarView: View, showOrHide: Int) {
            progressBarView.visibility = showOrHide
        }
    }
}
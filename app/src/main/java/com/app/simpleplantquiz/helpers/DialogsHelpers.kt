package com.app.simpleplantquiz.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.example.plantquiz.R

class DialogsHelpers {

    companion object {

        fun DialogCancelable(context: Context,title: String,message: String): AlertDialog{
            return AlertDialog.Builder(context,R.style.DialogTheme)
                .setTitle(title)
                .setMessage(message)
                .create()
        }

        fun DialogSimpleOkButton(context: Context, title: String, message: String, positiveButton: DialogInterface.OnClickListener): AlertDialog{
            return AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok_text,positiveButton)
                .setCancelable(false)
                .create()
        }

        fun DialogSimpleCancelButton(context: Context, title: String, message: String, positiveButton: DialogInterface.OnClickListener): AlertDialog{
            return AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.cancel,positiveButton)
                .setCancelable(false)
                .create()
        }

        fun DialogOkAndCancel(context: Context, title: String, message: String, positiveButton: DialogInterface.OnClickListener, negativeButton: DialogInterface.OnClickListener): AlertDialog {
            return AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok_text, positiveButton)
                .setNegativeButton(R.string.cancel, negativeButton)
                .setCancelable(false)
                .create()
        }

    }
}
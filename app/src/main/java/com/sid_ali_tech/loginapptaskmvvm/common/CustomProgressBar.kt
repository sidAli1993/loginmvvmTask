package com.sid_ali_tech.loginapptaskmvvm.common

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.sid_ali_tech.loginapptaskmvvm.R


class CustomProgressBar {
    private var dialog: Dialog? = null
    fun show(context: Context): Dialog? {
        return show(context, null)
    }

    fun show(context: Context, title: CharSequence?): Dialog? {
        return show(context, title, false)
    }

    fun show(context: Context, title: CharSequence?, cancelable: Boolean): Dialog? {
        return show(context, title, cancelable, null)
    }

    fun show(
        context: Context, title: CharSequence?, cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ): Dialog? {
        val inflator = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflator.inflate(R.layout.loader, null)
        if (title != null) {
//            val tv = view.findViewById(R.id.id_title) as TextView
//            tv.text = title
        }
        if (dialog==null){
            dialog = Dialog(context, R.style.NewDialog)
        }
        dialog!!.setContentView(view)
        dialog!!.setCancelable(cancelable)
        dialog!!.setOnCancelListener(cancelListener)
        dialog!!.show()
        return dialog
    }

    fun setTextDialog(text:String){
        dialog?.setTitle(text)
    }

    fun getDialog(): Dialog? {
        return dialog
    }
}
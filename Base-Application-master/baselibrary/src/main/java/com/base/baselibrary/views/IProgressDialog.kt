package com.base.baselibrary.views

import android.app.Dialog
import android.content.Context
import com.base.baselibrary.R

class IProgressDialog(context: Context) : Dialog(context, android.R.style.ThemeOverlay_Material_Dialog_Alert) {
    init {
        setContentView(R.layout.ui_progress_dialog)
        setCancelable(false)
    }
}
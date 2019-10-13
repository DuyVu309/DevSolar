package com.example.duyvd1.utils.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.duyvd1.R
import com.example.duyvd1.databinding.DialogYesNoBinding

class DialogYesNo : DialogFragment() {

    var mTitle: String? = null
    var mRightTitle: String? = null
    var mLeftTitle: String? = null
    private var mPositive: () -> Unit = {}
    private var mNegative: () -> Unit = {}

    companion object {
        val TAG: String = DialogYesNo::class.java.simpleName

        fun newInstance(): DialogYesNo = DialogYesNo()
    }

    fun setTitle(title: String): DialogYesNo {
        mTitle = title
        return this
    }

    fun setPositiveButtonTitle(right: String): DialogYesNo {
        mRightTitle = right
        return this
    }

    fun setNegativeButtonTitle(left: String): DialogYesNo {
        mLeftTitle = left
        return this
    }

    fun setNegative(callback: () -> Unit): DialogYesNo {
        mNegative = callback
        return this
    }

    fun setPositive(callback: () -> Unit): DialogYesNo {
        mPositive = callback
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_yes_no)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dialogBinding = DataBindingUtil.inflate<DialogYesNoBinding>(LayoutInflater.from(context), R.layout.dialog_yes_no, container, false)
        dialogBinding.dialog = this
        return dialogBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mTitle == null) mTitle = getString(R.string.title)
        if (mRightTitle == null) mRightTitle = getString(R.string.yes)
        if (mLeftTitle == null) mLeftTitle = getString(R.string.no)
    }

    fun onNegative(view: View) {
        dismiss()
        mNegative()
    }

    fun onPositive(view: View) {
        dismiss()
        mPositive()
    }
}
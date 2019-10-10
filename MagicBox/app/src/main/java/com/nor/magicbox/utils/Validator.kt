package com.nor.magicbox.utils

import android.widget.EditText

object Validator {
    fun isEmpty(vararg edts: EditText): Boolean {
        var isEmpty = false
        edts.forEach {
            if (it.text.toString().isEmpty()) {
                isEmpty = true
                it.error = "Data empty"
            }
        }
        return isEmpty
    }
}
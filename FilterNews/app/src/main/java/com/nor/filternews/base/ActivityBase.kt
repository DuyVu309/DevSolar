package com.nor.filternews.base

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class ActivityBase<BD: ViewDataBinding> : AppCompatActivity() {

    private val REQUEST_PERMISSION = 1

    protected lateinit var binding: BD
    private var onAllow: (() -> Unit)? = null
    private var onDenied: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            getLayoutId())
        initAct()
    }

    protected open fun initAct() {

    }

    abstract fun getLayoutId(): Int

    private fun checkPermission(permissions: Array<String>) : Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.forEach {
                if(checkSelfPermission(it) ==
                    PackageManager.PERMISSION_DENIED) {
                    return false
                }
            }
        }
        return true
    }

    protected fun doRequestPermission(permissions: Array<String>,
                                      onAllow: () -> Unit = {}, onDenied: () -> Unit = {} ) {
        this.onAllow = onAllow
        this.onDenied = onDenied
        if (checkPermission(permissions)) {
            onAllow()
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, REQUEST_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (checkPermission(permissions)) {
            onAllow?.invoke()
        }else {
            onDenied?.invoke()
        }
    }
}
package com.base.baselibrary.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.base.baselibrary.viewmodel.BaseViewModel
import com.base.baselibrary.viewmodel.Event
import com.base.baselibrary.viewmodel.EventAction
import com.base.baselibrary.views.IProgressDialog

abstract class BaseActivity<BD: ViewDataBinding, VM: BaseViewModel>(private val clz: Class<VM>) : AppCompatActivity(),
    Observer<Event> {

    private val REQUEST_PERMISSION = 1

    protected lateinit var binding: BD
    private var onAllow: (() -> Unit)? = null
    private var onDenied: (() -> Unit)? = null
    protected val progressDialog: IProgressDialog by lazy {
        IProgressDialog(this)
    }

    protected val viewModel: VM by lazy {
        ViewModelProviders.of(this)[clz]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            getLayoutId())
        initAct()
        viewModel.event.observe(this, this)
    }

    protected open fun initAct() {

    }

    abstract fun getLayoutId(): Int

    fun checkPermission(permissions: Array<String>) : Boolean {
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

    override fun onChanged(t: Event?) {
        if (t?.isLoading == true) {
            progressDialog.show()
        } else {
          progressDialog.dismiss()
          if (t?.error != null) {
              // show dialog error
              when (t?.action) {
                  EventAction.GET_NEWS_FEED -> {

                  }
              }
          }
        }
    }
}
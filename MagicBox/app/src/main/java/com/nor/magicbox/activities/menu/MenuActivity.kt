package com.nor.magicbox.activities.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nor.magicbox.MainActivity
import com.nor.magicbox.R
import com.nor.magicbox.databinding.ActivityMenuBinding
import com.nor.magicbox.utils.Validator


class MenuActivity : AppCompatActivity(), MenuListener {

    private val REQUEST_PLAY = 1

    private lateinit var binding: ActivityMenuBinding

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu)
        binding.listner = this
    }

    override fun onStartGame() {
        if (Validator.isEmpty(binding.edtPlayerName)) {
            return
        }
        startActivityForResult(
            MainActivity.newInstance(this, binding.edtPlayerName.text.toString()),
            REQUEST_PLAY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PLAY && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringExtra(EXTRA_RESULT)
            binding.result = result
        }
    }
}

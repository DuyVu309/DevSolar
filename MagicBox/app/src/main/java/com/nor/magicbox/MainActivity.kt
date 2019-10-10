package com.nor.magicbox

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.nor.magicbox.activities.menu.MenuActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val RESULT_WIN = "You Win"
    private val RESULLOSE = "You Lose"
    private val rd = Random()
    private var sum = 0
    private var time = 5
    private val WIN = 30

    companion object {
        private const val EXTRA_NAME = "extra.name"
        fun newInstance(context: Context, name: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        btn_5.setOnClickListener(this)
        btn_6.setOnClickListener(this)
        btn_7.setOnClickListener(this)
        btn_8.setOnClickListener(this)
        btn_9.setOnClickListener(this)
        tv_time.setOnClickListener(this)
        updateTime()
    }

    private fun updateTime() {
        tv_time.text = "Time: $time"
    }

    override fun onClick(v: View?) {
        if (v !is Button) {
            return
        }
        if(time <= 0) {
            return
        }
        time--
        v?.isEnabled = false
        v?.setBackgroundColor(Color.RED)
        val value = rd.nextInt(11)
        sum += value
        (v as Button).text = value.toString()

        if(sum >= WIN) {
            Toast.makeText(this, "You win", Toast.LENGTH_LONG).show()
            time = 0
            finishGame(RESULT_WIN)
            return
        }
        if(time == 0) {
            Toast.makeText(this, "You lose", Toast.LENGTH_LONG).show()
            finishGame(RESULT_WIN)
        }
        updateTime()
    }

    private fun finishGame(result: String) {
        val intent = Intent()
        intent.putExtra(MenuActivity.EXTRA_RESULT, result)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}

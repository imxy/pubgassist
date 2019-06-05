package com.pubg.sb.pubgassist.zb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pubg.sb.pubgassist.R
import kotlinx.android.synthetic.main.zb_first_activity.*


class ZBFirstActivity : AppCompatActivity() {
    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ZBSecondActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zb_first_activity)
        initView()
    }

    private fun initView() {
        tvSession1.setOnClickListener { ZBSecondActivity.start(this, "1") }
        tvSession2.setOnClickListener { ZBSecondActivity.start(this, "2") }
        tvSession3.setOnClickListener { ZBSecondActivity.start(this, "3") }
        tvSession4.setOnClickListener { ZBSecondActivity.start(this, "4") }
        tvSession5.setOnClickListener { ZBSecondActivity.start(this, "5") }
    }
}
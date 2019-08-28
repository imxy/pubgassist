package com.pubg.sb.pubgassist.coroutine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pubg.sb.pubgassist.R
import kotlinx.android.synthetic.main.layout_coroutine_activity.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.LogUtil

/**
 *  @author XY on 2019/7/18
 *  @apiNote 协程 activity
 */
class CoroutineActivity : AppCompatActivity() {


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CoroutineActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_coroutine_activity)
        initView()
    }

    private fun initView() {
        btn1.setOnClickListener {
            testRepeatCoroutine()
        }

        btn2.setOnClickListener {
            testRepeatThread()
        }
    }

    private fun testRepeatThread() {
        repeat(100_000) {
            Thread {
                Thread.sleep(1000)
                LogUtil.e("$it")
            }.start()
        }
    }

    private fun testRepeatCoroutine() = runBlocking {
        repeat(100_000) {
            launch {
                kotlinx.coroutines.delay(1000)
                LogUtil.e("$it")
            }
        }
    }
}
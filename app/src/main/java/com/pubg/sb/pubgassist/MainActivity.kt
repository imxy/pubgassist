package com.pubg.sb.pubgassist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener { _ ->
            execShellCmd("sendevent /dev/input/event1 1 158 1")
            execShellCmd("sendevent /dev/input/event1 1 158 0")

//            Thread(Runnable {
//                Thread.sleep(300)
//                execShellCmd("sendevent /dev/input/event1 1 158 0")
//            }).start()
        }
        button2.setOnClickListener { _ -> execShellCmd("sendevent /dev/input/event1 1 158 0") }
        button3.setOnClickListener { _ -> execShellCmd("sendevent /dev/input/event1 1 158 1") }
    }

    /**
     * 执行shell命令
     */
    private fun execShellCmd(cmd: String) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            val process = Runtime.getRuntime().exec("su")
            // 获取输出流
            val outputStream = process.outputStream
            val dataOutputStream = DataOutputStream(
                    outputStream)
            dataOutputStream.writeBytes(cmd)
            dataOutputStream.flush()
            dataOutputStream.close()
            outputStream.close()
        } catch (t: Throwable) {
            t.printStackTrace()
        }

    }
}

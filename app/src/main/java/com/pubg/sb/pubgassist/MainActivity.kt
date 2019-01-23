package com.pubg.sb.pubgassist

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.accessibility.AccessibilityManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startKeyboardService()
        initView()
    }

    override fun onResume() {
        super.onResume()
        button1.text = if (isServiceEnabled()) "关闭键盘监听" else "打开键盘监听"
    }

    private fun initView() {
        button1.setOnClickListener { _ ->
            openAccSetting()
        }
    }

    private fun startKeyboardService() {
        if (!isServiceEnabled()) {
            openAccSetting()
        }
    }

    private fun openAccSetting() {
        //打开系统无障碍设置界面
        val accessibleIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(accessibleIntent)
    }

    //检查服务是否开启
    private fun isServiceEnabled(): Boolean {
        val accessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        val accServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        accServices.forEach {
            if (it.id.contains("service.KeyBoardService")) {
                return true
            }
        }
        return false
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

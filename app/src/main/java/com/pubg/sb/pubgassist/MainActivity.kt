package com.pubg.sb.pubgassist

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hunter.library.debug.HunterDebug
import com.pubg.sb.pubgassist.adb.SuProcess
import com.pubg.sb.pubgassist.camera.CameraActivity
import com.pubg.sb.pubgassist.coroutine.CoroutineActivity
import com.pubg.sb.pubgassist.service.FloatingService
import com.pubg.sb.pubgassist.service.FloatingService.isStarted
import com.pubg.sb.pubgassist.service.FloatingService2
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        startKeyboardService()
        initView()
    }

    override fun onResume() {
        super.onResume()
        button1.text = if (isServiceEnabled()) "关闭键盘监听" else "打开键盘监听"
    }

    @HunterDebug
    private fun initView() {
        button1.setOnClickListener {
            openAccSetting()
        }

        button2.setOnClickListener {
            startFloatingButtonService()
        }

        button22.setOnClickListener {
            startFloatingButtonService2()
        }

        button3.setOnClickListener {
            CameraActivity.start(this)
        }
        button4.setOnClickListener {
            testScreenShot()
        }

        button5.setOnClickListener {
            CoroutineActivity.start(this)
        }

    }


    private fun testScreenShot() {
        val fileDir = File(Environment.getExternalStorageDirectory().toString() + File.separator + "1")
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }

        SuProcess.screenShot(fileDir.toString() + File.separator + System.currentTimeMillis() + ".jpg")
    }


    private fun startFloatingButtonService() {
        if (isStarted) {
            return
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show()
            startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")), 0)
        } else {
            startService(Intent(this@MainActivity, FloatingService::class.java))
        }
    }

    private fun startFloatingButtonService2() {
        if (FloatingService2.isStarted) {
            return
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show()
            startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")), 0)
        } else {
            startService(Intent(this@MainActivity, FloatingService2::class.java))
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

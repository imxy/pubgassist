package com.pubg.sb.pubgassist.ocr

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.googlecode.tesseract.android.TessBaseAPI
import com.pubg.sb.pubgassist.R
import kotlinx.android.synthetic.main.ocr_activity.*
import util.LogUtil
import java.io.File

/**
 *  @author XY on 2019/6/10
 *  @apiNote ocr 识别 Activity
 */
class OCRActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, OCRActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ocr_activity)

        initView()
    }


    @SuppressLint("CheckResult")
    private fun initView() {
        val rootPath = Environment.getExternalStorageDirectory().toString()
        Glide.with(iv).load(File("$rootPath/1/test1.jpg")).into(iv)

        btnStart.setOnClickListener { startOcr() }
    }

    private fun startOcr() {

        try {
            val startTime = System.currentTimeMillis()
            //初始化 ocr
            val rootPath = Environment.getExternalStorageDirectory().toString()
            val tessBaseAPI = TessBaseAPI()
            tessBaseAPI.init("$rootPath/1/", "testlang")
            tessBaseAPI.pageSegMode = TessBaseAPI.PageSegMode.PSM_AUTO

            //开始识别？？？
            tessBaseAPI.setImage(File("$rootPath/1/test1.jpg"))
            val resultText = tessBaseAPI.utF8Text
            tvResult.text = resultText
            LogUtil.e("startOcr $resultText")

            //关闭ocr
            tessBaseAPI.end()
            val endTime = System.currentTimeMillis()
            LogUtil.e("Ocr 耗时 = " + (endTime - startTime))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
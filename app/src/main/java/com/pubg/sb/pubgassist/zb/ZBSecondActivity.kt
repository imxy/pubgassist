package com.pubg.sb.pubgassist.zb

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CheckedTextView
import android.widget.TextView
import android.widget.Toast
import com.pubg.sb.pubgassist.R
import kotlinx.android.synthetic.main.zb_second_activity.*
import util.SharedPreferencesUtils


class ZBSecondActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val KEY_INDEX_SCORE = "KEY_INDEX_SCORE"

        fun start(context: Context, index: String) {
            val intent = Intent(context, ZBSecondActivity::class.java)
            intent.putExtra(KEY_INDEX_SCORE, index)
            context.startActivity(intent)
        }
    }

    private var mCurrentSelectedCT: CheckedTextView? = null
    private var mSavedBean: SaveInfoBean? = null
    private var mKeySaveInfoBean = "KEY_SAVE_INFO_BEAN"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zb_second_activity)
        initView()
        restoreLastInfo()
    }


    @SuppressLint("SetTextI18n")
    private fun restoreLastInfo() {
        val index = intent?.getStringExtra(KEY_INDEX_SCORE)
        mKeySaveInfoBean += index
        tvSessionIndex.text = "第 $index 场"

        mSavedBean = SharedPreferencesUtils.getInstance(this).getObject(mKeySaveInfoBean) as SaveInfoBean?
        if (null == mSavedBean) {
            mSavedBean = SaveInfoBean()
        }

        tvScoreA1_1.text = mSavedBean!!.mA1Score[0]
        tvScoreA1_2.text = mSavedBean!!.mA1Score[1]
        tvScoreA1_3.text = mSavedBean!!.mA1Score[2]
        tvScoreA1_4.text = mSavedBean!!.mA1Score[3]
        tvScoreA1_5.text = mSavedBean!!.mA1Score[4]


        tvScoreA2_1.text = mSavedBean!!.mA2Score[0]
        tvScoreA2_2.text = mSavedBean!!.mA2Score[1]
        tvScoreA2_3.text = mSavedBean!!.mA2Score[2]
        tvScoreA2_4.text = mSavedBean!!.mA2Score[3]
        tvScoreA2_5.text = mSavedBean!!.mA2Score[4]

        tvScoreA3_1.text = mSavedBean!!.mA3Score[0]
        tvScoreA3_2.text = mSavedBean!!.mA3Score[1]
        tvScoreA3_3.text = mSavedBean!!.mA3Score[2]
        tvScoreA3_4.text = mSavedBean!!.mA3Score[3]
        tvScoreA3_5.text = mSavedBean!!.mA3Score[4]

        tvScoreA4_1.text = mSavedBean!!.mA4Score[0]
        tvScoreA4_2.text = mSavedBean!!.mA4Score[1]
        tvScoreA4_3.text = mSavedBean!!.mA4Score[2]
        tvScoreA4_4.text = mSavedBean!!.mA4Score[3]
        tvScoreA4_5.text = mSavedBean!!.mA4Score[4]

        tvScoreA5_1.text = mSavedBean!!.mA5Score[0]
        tvScoreA5_2.text = mSavedBean!!.mA5Score[1]
        tvScoreA5_3.text = mSavedBean!!.mA5Score[2]
        tvScoreA5_4.text = mSavedBean!!.mA5Score[3]
        tvScoreA5_5.text = mSavedBean!!.mA5Score[4]

        tvScoreA6_1.text = mSavedBean!!.mA6Score[0]
        tvScoreA6_2.text = mSavedBean!!.mA6Score[1]
        tvScoreA6_3.text = mSavedBean!!.mA6Score[2]
        tvScoreA6_4.text = mSavedBean!!.mA6Score[3]
        tvScoreA6_5.text = mSavedBean!!.mA6Score[4]

    }

    private fun initView() {
        tvBack.setOnClickListener { back() }
        tvClear.setOnClickListener { clear() }
        tvSave.setOnClickListener { save() }
        ivAdd.setOnClickListener { addScore(mCurrentSelectedCT) }
        ivMinus.setOnClickListener { minusScore(mCurrentSelectedCT) }

        tvScoreA1_1.setOnClickListener(this)
        tvScoreA1_2.setOnClickListener(this)
        tvScoreA1_3.setOnClickListener(this)
        tvScoreA1_4.setOnClickListener(this)
        tvScoreA1_5.setOnClickListener(this)

        tvScoreA2_1.setOnClickListener(this)
        tvScoreA2_2.setOnClickListener(this)
        tvScoreA2_3.setOnClickListener(this)
        tvScoreA2_4.setOnClickListener(this)
        tvScoreA2_5.setOnClickListener(this)

        tvScoreA3_1.setOnClickListener(this)
        tvScoreA3_2.setOnClickListener(this)
        tvScoreA3_3.setOnClickListener(this)
        tvScoreA3_4.setOnClickListener(this)
        tvScoreA3_5.setOnClickListener(this)

        tvScoreA4_1.setOnClickListener(this)
        tvScoreA4_2.setOnClickListener(this)
        tvScoreA4_3.setOnClickListener(this)
        tvScoreA4_4.setOnClickListener(this)
        tvScoreA4_5.setOnClickListener(this)


        tvScoreA5_1.setOnClickListener(this)
        tvScoreA5_2.setOnClickListener(this)
        tvScoreA5_3.setOnClickListener(this)
        tvScoreA5_4.setOnClickListener(this)
        tvScoreA5_5.setOnClickListener(this)

        tvScoreA6_1.setOnClickListener(this)
        tvScoreA6_2.setOnClickListener(this)
        tvScoreA6_3.setOnClickListener(this)
        tvScoreA6_4.setOnClickListener(this)
        tvScoreA6_5.setOnClickListener(this)
    }

    override fun onBackPressed() {
        back()
    }

    private fun back() {
        DialogBackConfirm(this).apply {
            this.show()
            this.findViewById<View>(R.id.tvDelete).setOnClickListener {
                finish()
            }
        }
    }

    private fun clear() {
        DialogBackConfirm(this).apply {
            this.show()
            this.findViewById<TextView>(R.id.tvContent).text = "确认清空?"
            this.findViewById<View>(R.id.tvDelete).setOnClickListener {
                clearText()
                //删除
                SharedPreferencesUtils.getInstance(this@ZBSecondActivity).removeValue(mKeySaveInfoBean)
                this.dismiss()
            }
        }
    }

    private fun clearText() {
        tvScoreA1_1.text = "0"
        tvScoreA1_2.text = "0"
        tvScoreA1_3.text = "0"
        tvScoreA1_4.text = "0"
        tvScoreA1_5.text = "0"


        tvScoreA2_1.text = "0"
        tvScoreA2_2.text = "0"
        tvScoreA2_3.text = "0"
        tvScoreA2_4.text = "0"
        tvScoreA2_5.text = "0"

        tvScoreA3_1.text = "0"
        tvScoreA3_2.text = "0"
        tvScoreA3_3.text = "0"
        tvScoreA3_4.text = "0"
        tvScoreA3_5.text = "0"

        tvScoreA4_1.text = "0"
        tvScoreA4_2.text = "0"
        tvScoreA4_3.text = "0"
        tvScoreA4_4.text = "0"
        tvScoreA4_5.text = "0"

        tvScoreA5_1.text = "0"
        tvScoreA5_2.text = "0"
        tvScoreA5_3.text = "0"
        tvScoreA5_4.text = "0"
        tvScoreA5_5.text = "0"
    }

    private fun save() {

        mSavedBean!!.mA1Score[0] = tvScoreA1_1.text.toString()
        mSavedBean!!.mA1Score[1] = tvScoreA1_2.text.toString()
        mSavedBean!!.mA1Score[2] = tvScoreA1_3.text.toString()
        mSavedBean!!.mA1Score[3] = tvScoreA1_4.text.toString()
        mSavedBean!!.mA1Score[4] = tvScoreA1_5.text.toString()


        mSavedBean!!.mA2Score[0] = tvScoreA2_1.text.toString()
        mSavedBean!!.mA2Score[1] = tvScoreA2_2.text.toString()
        mSavedBean!!.mA2Score[2] = tvScoreA2_3.text.toString()
        mSavedBean!!.mA2Score[3] = tvScoreA2_4.text.toString()
        mSavedBean!!.mA2Score[4] = tvScoreA2_5.text.toString()

        mSavedBean!!.mA3Score[0] = tvScoreA3_1.text.toString()
        mSavedBean!!.mA3Score[1] = tvScoreA3_2.text.toString()
        mSavedBean!!.mA3Score[2] = tvScoreA3_3.text.toString()
        mSavedBean!!.mA3Score[3] = tvScoreA3_4.text.toString()
        mSavedBean!!.mA3Score[4] = tvScoreA3_5.text.toString()

        mSavedBean!!.mA4Score[0] = tvScoreA4_1.text.toString()
        mSavedBean!!.mA4Score[1] = tvScoreA4_2.text.toString()
        mSavedBean!!.mA4Score[2] = tvScoreA4_3.text.toString()
        mSavedBean!!.mA4Score[3] = tvScoreA4_4.text.toString()
        mSavedBean!!.mA4Score[4] = tvScoreA4_5.text.toString()

        mSavedBean!!.mA5Score[0] = tvScoreA5_1.text.toString()
        mSavedBean!!.mA5Score[1] = tvScoreA5_2.text.toString()
        mSavedBean!!.mA5Score[2] = tvScoreA5_3.text.toString()
        mSavedBean!!.mA5Score[3] = tvScoreA5_4.text.toString()
        mSavedBean!!.mA5Score[4] = tvScoreA5_5.text.toString()

        mSavedBean!!.mA6Score[0] = tvScoreA6_1.text.toString()
        mSavedBean!!.mA6Score[1] = tvScoreA6_2.text.toString()
        mSavedBean!!.mA6Score[2] = tvScoreA6_3.text.toString()
        mSavedBean!!.mA6Score[3] = tvScoreA6_4.text.toString()
        mSavedBean!!.mA6Score[4] = tvScoreA6_5.text.toString()

        SharedPreferencesUtils.getInstance(this).putObject(mKeySaveInfoBean, mSavedBean)

        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
    }


    override fun onClick(v: View?) {
        if (v is CheckedTextView) {
            clickScoreView(v)
        }
    }

    private fun clickScoreView(it: View?) {
        val checkedTextView = it as CheckedTextView
        if (checkedTextView.isChecked) {
            addScore(checkedTextView)
        }
        mCurrentSelectedCT?.isChecked = false
        mCurrentSelectedCT?.setTypeface(checkedTextView.typeface, Typeface.NORMAL)
        checkedTextView.isChecked = true
        checkedTextView.setTypeface(checkedTextView.typeface, Typeface.BOLD)
        mCurrentSelectedCT = checkedTextView

    }

    //分数加1
    private fun addScore(checkedTextView: CheckedTextView?) {
        checkedTextView?.let {
            val number = (checkedTextView.text.toString().toIntOrNull() ?: 0) + 1
            checkedTextView.text = number.toString()
        }
    }

    //分数-1
    private fun minusScore(checkedTextView: CheckedTextView?) {
        checkedTextView?.let {
            var number = (checkedTextView.text.toString().toIntOrNull() ?: 0) - 1
            if (number < 0) {
                number = 0
            }
            checkedTextView.text = number.toString()
        }
    }
}
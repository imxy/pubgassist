package com.pubg.sb.pubgassist.widget.irregular

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pubg.sb.pubgassist.R
import kotlinx.android.synthetic.main.irregular_activity.*

/**
 * Created by XY on 2019/8/27.
 *
 */
class IrregularViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.irregular_activity)
        irregularView.setOnClickListener {
            Log.e("XYS", "" + it.getTag(it.id))
        }
    }

}
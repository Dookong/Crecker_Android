package org.techtown.crecker.law.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_video.*
import org.techtown.crecker.R

class BrandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branding)
        brand_back_img.setOnClickListener {
            finish()
        }
    }
}

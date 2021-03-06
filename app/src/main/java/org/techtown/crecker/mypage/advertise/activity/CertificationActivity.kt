package org.techtown.crecker.mypage.advertise.activity

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_certification.*
import org.techtown.crecker.R
import org.techtown.crecker.mypage.advertise.data.VideoInfo
import org.techtown.crecker.mypage.api.UserAdServiceImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CertificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)

        tv_cert_dueDate.text = intent.getStringExtra("t")

        btn_goBack.setOnClickListener { finish() }

        btn_check.setOnClickListener {
            val pd = ProgressDialog(this).apply {
                setTitle("URL을 확인하는 중입니다..")
                show()
            }
            val map = HashMap<String, Any>().apply { put("url", cert_et_url.text) }

            UserAdServiceImpl.service.getVideoInfo(map).enqueue(object : Callback<VideoInfo>{
                override fun onFailure(call: Call<VideoInfo>, t: Throwable) {
                    pd.dismiss()
                    Toast.makeText(this@CertificationActivity, "동영상을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<VideoInfo>, response: Response<VideoInfo>) {
                    val d = response.takeIf { it.isSuccessful }?.body()?.data!!

                    Glide.with(this@CertificationActivity)
                        .load(d.thumbnails)
                        .into(img_thumnail)

                    cert_et_date.setText(d.publishedAt)

                    pd.dismiss()
                }
            })
        }

        cert_btn_complete.setOnClickListener {
            val map = HashMap<String, Any>()
            map["review"] = editText3.text.toString()
            map["adIdx"] = intent.getIntExtra("idx", 1)
            map["url"] = cert_et_url.text.toString()

            UserAdServiceImpl.service.postVideoInfo(map).enqueue(object : Callback<org.techtown.crecker.mypage.api.Response>{
                override fun onFailure(
                    call: Call<org.techtown.crecker.mypage.api.Response>,
                    t: Throwable
                ) {
                    Toast.makeText(this@CertificationActivity, "실패", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<org.techtown.crecker.mypage.api.Response>,
                    response: Response<org.techtown.crecker.mypage.api.Response>
                ) {
                    response.takeIf { it.isSuccessful }?.body()?.message?.
                        let {
                            Toast.makeText(this@CertificationActivity, it, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                }
            })
        }
    }
}
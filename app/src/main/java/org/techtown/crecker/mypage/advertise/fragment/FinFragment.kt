package org.techtown.crecker.mypage.advertise.fragment

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.filtering_my_ad_dialog.*
import kotlinx.android.synthetic.main.fragment_my_ad_assign.view.*
import org.techtown.crecker.R
import org.techtown.crecker.module.NavBarSetting
import org.techtown.crecker.module.RcvItemDeco
import org.techtown.crecker.module.putLog
import org.techtown.crecker.mypage.advertise.data.UserAdData
import org.techtown.crecker.mypage.api.UserAdServiceImpl
import org.techtown.crecker.mypage.contents.myAd.MyAdAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinFragment : Fragment() {
    lateinit var adapter: MyAdAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_my_ad_assign, container, false)
        v.ad_apply_filter.setOnClickListener { showFilter(v) }

        UserAdServiceImpl.service.getUserAds(4).enqueue(object : Callback<UserAdData> {
            override fun onFailure(call: Call<UserAdData>, t: Throwable) {
                "UserAdService 실패".putLog()
            }

            override fun onResponse(call: Call<UserAdData>, response: Response<UserAdData>) {
                val data = response.takeIf { it.isSuccessful }?.body()?.data
                adapter = MyAdAdapter(v.context, data as ArrayList<UserAdData.Data>, 4)

                v.rv_ad_apply_list.apply {
                    this.adapter = this@FinFragment.adapter
                    layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
                    addItemDecoration(RcvItemDeco(v.context, false, 20))
                }
            }
        })

        return v
    }

    private fun showFilter(v: View) {
        val dialog = BottomSheetDialog(v.context)
            .apply {
                setContentView(R.layout.filtering_my_ad_dialog)
                category_latest.setOnClickListener {
                    adapter.sort("최신순")
                    v.textView39.text = "최신순"
                    dismiss()
                }
                category_dead.setOnClickListener {
                    adapter.sort("마감순")
                    v.textView39.text = "마감순"
                    dismiss()
                }

                when(v.textView39.text.toString()){
                    "최신순" -> {
                        category_latest.typeface = Typeface.DEFAULT_BOLD
                        category_dead.typeface = Typeface.DEFAULT
                    }
                    "마감순" -> {
                        category_latest.typeface = Typeface.DEFAULT
                        category_dead.typeface = Typeface.DEFAULT_BOLD
                    }
                }
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
            NavBarSetting.setWhite(dialog)
        dialog.show()
    }
}
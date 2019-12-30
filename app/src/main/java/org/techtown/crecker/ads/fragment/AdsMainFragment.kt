package org.techtown.crecker.ads.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.constants.IndicatorStyle
import com.zhpan.bannerview.utils.BannerUtils
import kotlinx.android.synthetic.main.ad_main_fragment.view.*
import org.techtown.crecker.R
import org.techtown.crecker.ads.api.AdsServiceImpl
import org.techtown.crecker.ads.contents.AdsAdapter
import org.techtown.crecker.ads.contents.AdData
import org.techtown.crecker.ads.contents.data.AdsRandom
import org.techtown.crecker.ads.banner.BannerData
import org.techtown.crecker.ads.banner.BannerVH
import org.techtown.crecker.ads.contents.data.Ads
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class AdsMainFragment : Fragment() {
    private var mBannerList: MutableList<BannerData> = ArrayList()
    private var mViewPager: BannerViewPager<BannerData, BannerVH>? = null

    private lateinit var rcmdAdapter: AdsAdapter
    private lateinit var popularAdapter: AdsAdapter
    private lateinit var recentAdapter: AdsAdapter

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context.applicationContext
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.ad_main_fragment, container, false)
        initRemoteData()
        initView(view)
        return view
    }

    override fun onPause() {
        super.onPause()
        mViewPager?.stopLoop()
    }

    override fun onResume() {
        super.onResume()
        mViewPager?.startLoop()
    }

    private fun initRemoteData() {
        AdsServiceImpl.service.getRandomAds().enqueue(
            object : Callback<AdsRandom> {
                override fun onFailure(call: Call<AdsRandom>, t: Throwable) {
                    "실패: $t".putLog("Fail")
                }

                override fun onResponse(call: Call<AdsRandom>, response: Response<AdsRandom>) {
                    Log.d("initRemoteData", response.isSuccessful.toString())
                    response.takeIf { it.isSuccessful }?.body()?.data?.
                        let {
                        mBannerList.add(BannerData(it.title, it.subtitle, it.dday, it.thumbnail))
                    } ?: run{
                        Toast.makeText(mContext, "서버로부터 정보를 받아올 수 없습니다..", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        AdsServiceImpl.service.getPopularAds().enqueue(object : Callback<Ads>{
            override fun onFailure(call: Call<Ads>, t: Throwable) {
                
            }

            override fun onResponse(call: Call<Ads>, response: Response<Ads>) {

            }

        })
    }

    private fun initLocalData() {
        val title = arrayOf("Christmas Soap", "aaa", "bbb","ccc", "ddd")
        val des = arrayOf("크리스마스 비누 2구 세트", "aaa", "bbb","ccc", "ddd")
        val dday = arrayOf("D-7", "D-1", "D-14","D-2", "D-4")
        val imgs = arrayOf(R.drawable.img_main_banner, R.drawable.img_main_banner, R.drawable.img_main_banner, R.drawable.img_main_banner, R.drawable.img_main_banner)

        val list = ArrayList<BannerData>()
        for(i in 0..4){
            val bean = BannerData()
            bean.imageTitle = title[i]
            bean.imageDescription = des[i]
            bean.imageDday = dday[i]
            bean.imgLocalUrl = imgs[i]
            list.add(bean)
        }
        mBannerList = list
    }

    private fun initView(view: View) {
        mViewPager = view.findViewById(R.id.ad_view_pager)
        mViewPager!!.setIndicatorGap(BannerUtils.dp2px(6f))
            .setRoundCorner(BannerUtils.dp2px(6f))
            .setHolderCreator{ BannerVH() }
        setupIndicator()

        val dummy = AdData("", R.drawable.img_thum1, "모모스 커피", "Momos Coffee",10000, 7)
        val dummy2 = AdData("", R.drawable.img_thum2, "모모스 커피", "Momos Coffee",10000, 24)
        val dummy3 = AdData("", R.drawable.img_thum2, "데저트 크림", "Desert Cream",8000, 30)

        rcmdAdapter = AdsAdapter(mContext)
        rcmdAdapter.data = arrayListOf(dummy, dummy2, dummy, dummy2)
        view.rv_ad_recommend.adapter = rcmdAdapter
        view.rv_ad_recommend.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

        popularAdapter = AdsAdapter(mContext)
        popularAdapter.data = arrayListOf(dummy2, dummy, dummy2, dummy)
        view.rv_ad_popular.adapter = popularAdapter
        view.rv_ad_popular.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

        recentAdapter = AdsAdapter(mContext)
        recentAdapter.data = arrayListOf(dummy3, dummy, dummy, dummy2, dummy3, dummy2)
        view.rv_ad_recent.adapter = recentAdapter
        view.rv_ad_recent.layoutManager = GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false)
    }

    private fun setupIndicator() {
        mViewPager!!.setIndicatorStyle(IndicatorStyle.DASH)
            .setIndicatorHeight(5)
            .setIndicatorGravity(IndicatorGravity.CENTER)
            .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            .setIndicatorGap(0)
            .setPageMargin(resources.getDimensionPixelOffset(R.dimen.dp_3))
            .setIndicatorWidth(200)
            .setIndicatorColor(
                Color.parseColor("#c9cdd2"),
                Color.parseColor("#000000")
            ).create(mBannerList)
    }
}
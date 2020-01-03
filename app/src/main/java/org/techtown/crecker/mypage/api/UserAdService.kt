package org.techtown.crecker.mypage.api

import org.techtown.crecker.ads.api.TOKEN
import org.techtown.crecker.mypage.advertise.data.BasicInfo
import org.techtown.crecker.mypage.advertise.data.TagAdData
import org.techtown.crecker.mypage.advertise.data.UserAdData
import org.techtown.crecker.mypage.advertise.data.VideoInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface UserAdService {
    //My Ad
    @Headers("token: $TOKEN")
    @GET("userad/{idx}")
    fun getUserAds(@Path("idx") idx: Int): Call<UserAdData>

    //My Ad
    @Headers("token: $TOKEN")
    @GET("userad/{idx}")
    fun getTagAds(@Path("idx") idx: Int): Call<TagAdData>

    //My Ad - 세부사항
//    @Headers("token: $TOKEN")
//    @GET("userad/{i}/{j}")
//    fun getUserAdDetail(@Path("i") i: Int, @Path("j") j: Int): Call<> //TODO: 잠시 대기

    //바깥쪽
    @Headers("token: $TOKEN")
    @GET("userad/length")
    fun getBasicInfo(): Call<BasicInfo>

    //등록일, 썸네일 get
    @Headers("token: $TOKEN")
    @FormUrlEncoded
    @POST("userad/auth/auth/auth")
    fun getVideoInfo(@FieldMap payLoad: HashMap<String, Any>): Call<VideoInfo>

    //영상 인증
    @Headers("token: $TOKEN")
    @FormUrlEncoded
    @POST("userad/auth")
    fun postVideoInfo(@FieldMap payLoad: HashMap<String, Any>): Call<Response>
}
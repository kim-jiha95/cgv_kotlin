package com.example.rp_week5.config

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.example.rp_week5.MainActivity
import com.example.rp_week5.R
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class ApplicationClass : Application() {

    companion object {
        // 전역변수 >> 객체를 한 번만 선언해서 싱글톤으로 사용한다.
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
        sSharedPreferences =
            applicationContext.getSharedPreferences("SP", MODE_PRIVATE)
    }
}
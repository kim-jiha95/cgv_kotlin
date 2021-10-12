package com.example.rp_week5.ticket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rp_week5.LoginActivity
import com.example.rp_week5.MainActivity
import com.example.rp_week5.config.ApplicationClass
import com.example.rp_week5.databinding.TicketBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError

import com.kakao.sdk.user.UserApiClient

class TicketActivity : AppCompatActivity() {

    lateinit var binding: TicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TicketBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                        //로그인 필요
                        intent=Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        //기타 에러
                    }
                } else {
                    //토큰 유효성 체크 성공(필요시 토큰 갱신됨)
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("사용자 정보 요청 실패", error.toString())
                        } else if (user != null) {
                            binding.myName.text = user.kakaoAccount?.profile?.nickname
                            binding.myEmail.text = user.kakaoAccount?.email
                            Log.i("사용자 정보 요청 성공",
                                "\n회원번호: ${user.id}" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}")
                        }
                    }
                    binding.okBtn.setOnClickListener {
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    binding.movieLocation.text = intent.getStringExtra("location")
                    binding.movieDays.setText(ApplicationClass.sSharedPreferences.getString("day",intent.getStringExtra("day")))
                    binding.movieLocation.setText(ApplicationClass.sSharedPreferences.getString("location",intent.getStringExtra("location")))
                    binding.movieName.setText(ApplicationClass.sSharedPreferences.getString("name",intent.getStringExtra("name")))
                }

            }
        } else {
            //로그인필요
            intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }








    }



}
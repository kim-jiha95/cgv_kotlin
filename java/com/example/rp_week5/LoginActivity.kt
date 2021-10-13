package com.example.rp_week5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rp_week5.databinding.LoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                //Login Fail
                Log.e("Login-Fail:", error.toString())
            } else if (token != null) {
                //Login Success
                Log.d("Token: ", token.accessToken)
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.kakaoLogin.setOnClickListener {
            UserApiClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    loginWithKakaoTalk(this@LoginActivity, callback = callback)
                } else {
                    loginWithKakaoAccount(this@LoginActivity, callback = callback)
                }
            }
        }

        binding.kakaoLogout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error!=null){
                    Log.i("Logout-Fail: ", error.toString())
                }else{
                    Log.i("Logout-Success!","SDK에서 토근 삭제됨")
                    intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }





    }

}
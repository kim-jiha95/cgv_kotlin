package com.example.rp_week5

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.*
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.rp_week5.databinding.ActivityMainBinding
import com.example.rp_week5.movies_models.MovieData
import com.example.rp_week5.ticket.TicketActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


data class Movies(
//    var img: Int,
        var img: String,
        var name: String,
        var egg_per: String,
        var ratio: String,
)

val images = arrayOf(
        "https://img.cgv.co.kr/Front/Main/2021/0913/16315036396800.jpg",
        "https://img.cgv.co.kr/Front/Main/2021/1013/16340960278770.jpg",
        "https://img.cgv.co.kr/Front/Main/2021/1012/16340063550260.jpg",
)

class MainActivity : AppCompatActivity() {


    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }

    var MoviesArrayList = ArrayList<Movies>()
//    var handler = Handler(Looper.getMainLooper())


    private lateinit var movieAdapter: MovieAdapter


    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try{
            val info = packageManager.getPackageInfo(packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = info.signingInfo.apkContentsSigners
            val md = MessageDigest.getInstance("SHA")
            for(signature in signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                for (signature in signatures){
                    val md: MessageDigest
                    md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val key = String(Base64.encode(md.digest(), 0))
                    Log.d("Hash Key", "!!$key!!")
                }
            }
        }catch (e: Exception){
            Log.e("name not found", e.toString())
        }
//        var keyHash = Utility.getKeyHash(this)
//        Log.d("hash", keyHash)

        movieAdapter = MovieAdapter(this, MoviesArrayList)
        binding.movieRv.adapter = movieAdapter

        binding.mainAd.offscreenPageLimit = 1 // ViewPager가 상태를 유지할 수 있는 페이지의 갯수를 지정

        imageSliderAdapter = ImageSliderAdapter(this, images)
        binding.mainAd.adapter = imageSliderAdapter
        binding.mainAd.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.mainAd.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.d("TEST onPageScrolled", position.toString())
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 다른 페이지로 스크롤 됬을때 ViewPager 의 현재 페이지 텍스트뷰를 갱신해준다.
                Log.d("TEST onPageSelected", position.toString())
                binding.nowIndi.text = (position + 1).toString()
            }
        })

        binding.totalIndi.text = imageSliderAdapter.itemCount.toString()


        binding.ticketIcon.setOnClickListener {
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { _, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                            //로그인 필요
                            intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            //기타 에러
                        }
                    } else {
                        //토큰 유효성 체크 성공(필요시 토큰 갱신됨)

                        intent = Intent(this, TicketActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
            } else {
                //로그인필요
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.menuIcon.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        getMovieData("e3269631c1a855227a37cefab44ad995", "ko-KR", 1, "KR")

//        MoviesArrayList.add(0, movieAdapter.dataList[0])

//        binding.movieRv.layoutManager=LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//
//        MoviesArrayList.add(
//            Movies(
//                R.drawable.movie1,
//                "싱크홀",
//                "ddd",
//                "qqq"
//            )
//        )
    }


    private fun getMovieData(api_key: String, language: String, page: Int, region: String) {

        val movieInterface = RetrofitClient.sRetrofit.create(MovieInterface::class.java)

        movieInterface.getNowPlaying(api_key, language, page, region).enqueue(object :
                Callback<MovieData> {

            override fun onResponse(
                    call: Call<MovieData>,
                    response: Response<MovieData>,
            ) {
                if (response.isSuccessful) {

                    val result = response.body() as MovieData
                    for (i in 0..5) {
                        MoviesArrayList.add(
                                Movies(
                                        "https://image.tmdb.org/t/p/w500" + result.results[i].poster_path,
                                        result.results[i].title,
                                        result.results[i].popularity.toString(),
                                        result.results[i].vote_average.toString()
                                )
                        )
                    }

                    movieAdapter.notifyDataSetChanged()

                    Log.d("DataCheck", MoviesArrayList.toString())

                } else {
                    Log.d("MainActivity", "getMovieData - onResponse : Error code")
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d("MainActivity", t.message ?: "통신오류")
            }
        })
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
    }


}
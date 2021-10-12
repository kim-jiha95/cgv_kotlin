package com.example.rp_week5

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.rp_week5.cgv_models.CgvData
import com.example.rp_week5.cgv_models.LOCALDATA031302
import com.example.rp_week5.config.ApplicationClass
import com.example.rp_week5.databinding.TicketingBinding
import com.example.rp_week5.movies_models.MovieData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class Location(
    var cgv_location: String,
)

class CgvActivity : AppCompatActivity() {

    var LocationArrayList = ArrayList<Location>()

    private lateinit var cgvAdapter: CgvAdapter

    private lateinit var binding: TicketingBinding
    val editor = ApplicationClass.sSharedPreferences.edit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TicketingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cgvAdapter = CgvAdapter(this, LocationArrayList)
        binding.locationRv.adapter = cgvAdapter

        binding.mvTitle.text = intent.getStringExtra("name")


        Log.e("aaa", binding.locationTv.text.toString())
        ApplicationClass.sSharedPreferences.getString("day", binding.movieDay.text.toString())
        editor.putString("day", binding.movieDay.text.toString())
        editor.apply()


        binding.hongTxt.setOnClickListener {
            getLocationHong()
                binding.locationRv.visibility = View.VISIBLE

            if (binding.hongTxt.tag.toString().equals("true")){
                binding.hongTxt.tag="false"
                binding.shinTxt.tag="false"
                binding.yeouidoTxt.tag="false"
                }else{
                binding.hongTxt.tag="true"
                binding.shinTxt.tag="false"
                binding.yeouidoTxt.tag="false"
                binding.hongTxt.setBackgroundColor(Color.parseColor("#f5f5f5"))
                binding.shinTxt.setBackgroundColor(Color.parseColor("#ffffff"))
                binding.yeouidoTxt.setBackgroundColor(Color.parseColor("#ffffff"))

            }

        }

        binding.shinTxt.setOnClickListener {
            getLocationShin()
                binding.locationRv.visibility = View.VISIBLE

            if (binding.shinTxt.tag.toString().equals("true")){
                binding.shinTxt.tag="false"
                binding.hongTxt.tag="false"
                binding.yeouidoTxt.tag="false"
            }else{
                binding.shinTxt.tag="true"
                binding.hongTxt.tag="false"
                binding.yeouidoTxt.tag="false"
                binding.shinTxt.setBackgroundColor(Color.parseColor("#f5f5f5"))
                binding.yeouidoTxt.setBackgroundColor(Color.parseColor("#ffffff"))
                binding.hongTxt.setBackgroundColor(Color.parseColor("#ffffff"))


            }

        }

        binding.yeouidoTxt.setOnClickListener {
            getLocationY()

                binding.locationRv.visibility = View.VISIBLE


            if (binding.yeouidoTxt.tag.toString().equals("true")){
                binding.yeouidoTxt.tag="false"
                binding.hongTxt.tag="false"
                binding.shinTxt.tag="false"
            }else{
                binding.yeouidoTxt.tag="true"
                binding.hongTxt.tag="false"
                binding.shinTxt.tag="false"
                binding.yeouidoTxt.setBackgroundColor(Color.parseColor("#f5f5f5"))
                binding.shinTxt.setBackgroundColor(Color.parseColor("#ffffff"))
                binding.hongTxt.setBackgroundColor(Color.parseColor("#ffffff"))


            }
        }

        binding.backIcon.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }




//        LocationArrayList.add(
//            Location(
//                "어"
//            )
//        )
    }

    private fun getLocationHong() {

        val movieInterface = RetrofitClient.cRetrofit.create(CgvInterface::class.java)

        movieInterface.getCgv().enqueue(object :
            Callback<CgvData> {

            override fun onResponse(
                call: Call<CgvData>,
                response: Response<CgvData>,
            ) {
                if (response.isSuccessful) {

                    val result = response.body() as CgvData

                    LocationArrayList.clear()

                    Log.d("dd", result.LOCALDATA_031302.row.size.toString())
                    for (i in 0 until result.LOCALDATA_031302.row.size) {
                        if (result.LOCALDATA_031302.row[i].BPLCNM.contains("CGV홍대")) {
                            LocationArrayList.add(
                                Location(
                                    result.LOCALDATA_031302.row[i].BPLCNM
                                )
                            )
                        }
                    }
                    cgvAdapter.notifyDataSetChanged()

                    Log.d("DataCheck2", LocationArrayList.toString())

                } else {
                    Log.d("CgvActicity", "getMovieData - onResponse : Error code")
                }
            }

            override fun onFailure(call: Call<CgvData>, t: Throwable) {
                Log.d("CgvActicity", t.message ?: "통신오류")
            }
        })
    }

    private fun getLocationShin() {

        val movieInterface = RetrofitClient.cRetrofit.create(CgvInterface::class.java)

        movieInterface.getCgv().enqueue(object :
            Callback<CgvData> {

            override fun onResponse(
                call: Call<CgvData>,
                response: Response<CgvData>,
            ) {
                if (response.isSuccessful) {

                    val result = response.body() as CgvData
                    LocationArrayList.clear()

                    Log.d("dd", result.LOCALDATA_031302.row.size.toString())
                    for (i in 0 until result.LOCALDATA_031302.row.size) {
                        if (result.LOCALDATA_031302.row[i].BPLCNM.contains("CGV신촌")) {
                            LocationArrayList.add(
                                Location(
                                    result.LOCALDATA_031302.row[i].BPLCNM
                                )
                            )
                        }
                    }
                    cgvAdapter.notifyDataSetChanged()

                    Log.d("DataCheck2", LocationArrayList.toString())

                } else {
                    Log.d("CgvActicity", "getMovieData - onResponse : Error code")
                }
            }

            override fun onFailure(call: Call<CgvData>, t: Throwable) {
                Log.d("CgvActicity", t.message ?: "통신오류")
            }
        })
    }

    private fun getLocationY() {

        val movieInterface = RetrofitClient.cRetrofit.create(CgvInterface::class.java)

        movieInterface.getCgv().enqueue(object :
            Callback<CgvData> {

            override fun onResponse(
                call: Call<CgvData>,
                response: Response<CgvData>,
            ) {
                if (response.isSuccessful) {

                    val result = response.body() as CgvData
                    LocationArrayList.clear()

                    Log.d("dd", result.LOCALDATA_031302.row.size.toString())
                    for (i in 0 until result.LOCALDATA_031302.row.size) {
                        if (result.LOCALDATA_031302.row[i].BPLCNM.contains("CGV여의도")) {
                            LocationArrayList.add(
                                Location(
                                    result.LOCALDATA_031302.row[i].BPLCNM
                                )
                            )
                        }
                    }
                    cgvAdapter.notifyDataSetChanged()

                    Log.d("DataCheck2", LocationArrayList.toString())

                } else {
                    Log.d("CgvActicity", "getMovieData - onResponse : Error code")
                }
            }

            override fun onFailure(call: Call<CgvData>, t: Throwable) {
                Log.d("CgvActicity", t.message ?: "통신오류")
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
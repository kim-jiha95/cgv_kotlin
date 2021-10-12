package com.example.rp_week5

import com.example.rp_week5.cgv_models.CgvData
import retrofit2.Call
import retrofit2.http.GET

interface CgvInterface {


   // @GET("http://openapi.seoul.go.kr:8088/{api_key}/json/LOCALDATA_031302/{start_index}/{end_index}")
   // fun getCgv(
    //    val api_key= "455441597964696436397666544441"
     //   val start_index= "1"
      //  val end_index=="5"
        //    ) : Call<CgvData>


    @GET("455441597964696436397666544441/json/LOCALDATA_031302/1/100")
    fun getCgv() : Call<CgvData>


}
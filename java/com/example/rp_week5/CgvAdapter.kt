package com.example.rp_week5

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_week5.config.ApplicationClass
import com.example.rp_week5.databinding.TicketingItemBinding
import com.example.rp_week5.ticket.TicketActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient

class CgvAdapter(private val context: Context, private var LocationArrayList: ArrayList<Location>) :
    RecyclerView.Adapter<CgvAdapter.ViewHolder>() {


    var location_dataList: ArrayList<Location> = LocationArrayList

    class ViewHolder(val binding: TicketingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Location) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = TicketingItemBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val editor = ApplicationClass.sSharedPreferences.edit()
        holder.binding.cgvList.text = location_dataList[position].cgv_location
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                        //로그인 필요
                        holder.binding.cgvList.setOnClickListener {
                            var intent=Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                        }
                    } else {
                        //기타 에러
                    }
                } else {
                    //토큰 유효성 체크 성공(필요시 토큰 갱신됨)
                    holder.binding.cgvList.setOnClickListener {
                        var intent = Intent(context, TicketActivity::class.java)
                        intent.putExtra("location", holder.binding.cgvList.text.toString())
                        ApplicationClass.sSharedPreferences.getString("location", holder.binding.cgvList.text.toString())
                        editor.putString("location", holder.binding.cgvList.text.toString())
                        editor.apply()
                        context.startActivity(intent)
                    }

                }

            }
        } else {
            //로그인필요
            holder.binding.cgvList.setOnClickListener {
                var intent=Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
        }



    }

    override fun getItemCount(): Int {
        return location_dataList.size
    }

}
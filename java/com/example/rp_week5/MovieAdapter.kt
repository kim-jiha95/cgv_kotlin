package com.example.rp_week5

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.rp_week5.config.ApplicationClass
import com.example.rp_week5.databinding.MovieItemBinding
import com.example.rp_week5.databinding.TicketBinding
import com.example.rp_week5.ticket.TicketActivity


class MovieAdapter(private val context: Context, private var MoviesArrayList: ArrayList<Movies>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    var dataList: ArrayList<Movies> = MoviesArrayList

    //    var dataSet : ArrayList<Movies> = arrayListOf()
//    lateinit var binding: MovieItemBinding

    class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Movies) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val editor = ApplicationClass.sSharedPreferences.edit()
        holder.binding.mvName.text = dataList[position].name
        val url = "https://image.tmdb.org/t/p/w500" + dataList[position].img
        Glide.with(context)
            .load(url)
            .into(holder.binding.mvImg)

        holder.binding.eggTxt.text = dataList[position].egg_per
        holder.binding.ratio.text = dataList[position].ratio

        holder.binding.tcBtn.setOnClickListener {
            var intent  = Intent(context, CgvActivity::class.java)
            intent.putExtra("name",holder.binding.mvName.text.toString())
            ApplicationClass.sSharedPreferences.getString("name", holder.binding.mvName.text.toString())
            editor.putString("name", holder.binding.mvName.text.toString())
            editor.apply()
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}
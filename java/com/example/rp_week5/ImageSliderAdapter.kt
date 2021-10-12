package com.example.rp_week5

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rp_week5.databinding.ItemSliderBinding


class ImageSliderAdapter(
    private val context: Context,
    private val sliderImage: Array<String>,
) :
    RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {

    var handler = Handler(Looper.getMainLooper())

    inner class MyViewHolder(val binding: ItemSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSliderBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context).load(sliderImage[position]).into(holder.binding.imageSlider)
//        Thread(){
//            while (true){
//                for (position in 0..sliderImage.size){
//                    Thread.sleep(700)
//                    handler.post{
//                        Glide.with(context).load(sliderImage[position]).into(holder.binding.imageSlider)
//                    }
//                }
//            }
//        }.start()
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }
}
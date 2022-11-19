package com.example.vinilosgrupo3.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vinilosgrupo3.R
import com.example.vinilosgrupo3.databinding.MusicianDetailItemBinding
import com.example.vinilosgrupo3.models.Musician
import java.text.DateFormat
import java.util.*


class MusicianDetailAdapter : RecyclerView.Adapter<MusicianDetailAdapter.MusicianDetailViewHolder>(){

    var musiciandetail: Musician? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianDetailViewHolder {
        val withDataBinding: MusicianDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianDetailViewHolder.LAYOUT,
            parent,
            false)
        return MusicianDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicianDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.musiciandetail = musiciandetail
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    private fun formatDate(date: Date?): String {
        return DateFormat.getDateInstance(DateFormat.LONG).format(date).toString()
    }

    class MusicianDetailViewHolder(val viewDataBinding: MusicianDetailItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_item
            @JvmStatic @BindingAdapter("android:image")
            fun loadImage(view: ImageView, imageUrl: String?) {
                Glide.with(view.context)
                    .load(imageUrl).apply(RequestOptions())
                    .into(view)
            }
        }
    }
}


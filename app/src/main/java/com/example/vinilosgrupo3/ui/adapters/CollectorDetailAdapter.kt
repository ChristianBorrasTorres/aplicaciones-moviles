package com.example.vinilosgrupo3.ui.adapters
import android.annotation.SuppressLint
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
import com.example.vinilosgrupo3.databinding.CollectorDetailItemBinding
import com.example.vinilosgrupo3.models.Collector
import java.text.DateFormat
import java.util.*

class CollectorDetailAdapter : RecyclerView.Adapter<CollectorDetailAdapter.CollectorDetailViewHolder>(){

    var collectorDetail: Collector? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorDetailViewHolder {
        val withDataBinding: CollectorDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorDetailViewHolder.LAYOUT,
            parent,
            false)
        return CollectorDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collectorDetail = collectorDetail
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    private fun formatDate(date: Date?): String {
        return DateFormat.getDateInstance(DateFormat.LONG).format(date).toString()
    }

    class CollectorDetailViewHolder(val viewDataBinding: CollectorDetailItemBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_detail_item
            @JvmStatic @BindingAdapter("android:image")
            fun loadImage(view: ImageView, imageUrl: String?) {
                Glide.with(view.context)
                    .load(imageUrl).apply(RequestOptions())
                    .into(view)
            }
        }
    }
}


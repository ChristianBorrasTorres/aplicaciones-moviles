package com.example.vinilosgrupo3.ui.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosgrupo3.R
import com.example.vinilosgrupo3.databinding.DetailItemBinding
import com.example.vinilosgrupo3.models.Album
import java.text.DateFormat
import java.util.*

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>(){

    var detail: Album? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val withDataBinding: DetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DetailViewHolder.LAYOUT,
            parent,
            false)
        return DetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.detail = detail
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    private fun formatDate(date: Date?): String {
        return DateFormat.getDateInstance(DateFormat.LONG).format(date).toString()
    }

    class DetailViewHolder(val viewDataBinding: DetailItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.detail_item
        }
    }
}

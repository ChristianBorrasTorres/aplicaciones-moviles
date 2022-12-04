package com.example.vinilosgrupo3.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosgrupo3.R
import com.example.vinilosgrupo3.databinding.CollectorItemBinding
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.ui.CollectorFragmentDirections
import com.example.vinilosgrupo3.ui.HomeFragmentDirections

class CollectorAdapter : RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>(){

    var collectors :List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false)

        return CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collector = collectors[position]
        }
        holder.viewDataBinding.root.setOnClickListener {
            val action = CollectorFragmentDirections.actionCollectorFragmentToCollectorDetailFragment(collectors[position].id)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return collectors.size
    }


    class CollectorViewHolder(val viewDataBinding: CollectorItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }


}
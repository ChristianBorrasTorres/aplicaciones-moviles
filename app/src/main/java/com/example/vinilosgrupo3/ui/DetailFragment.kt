package com.example.vinilosgrupo3.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosgrupo3.R
import com.example.vinilosgrupo3.databinding.DetailFragmentBinding
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.ui.adapters.DetailAdapter
import com.example.vinilosgrupo3.viewmodels.DetailViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: DetailViewModel
    private var viewModelAdapter: DetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = DetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.detailRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.album_details)
        val args: DetailFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, DetailViewModel.Factory(activity.application, args.albumId)).get(DetailViewModel::class.java)
        viewModel.detail.observe(viewLifecycleOwner, Observer<Album> {
            it.apply {
                viewModelAdapter!!.detail = this
                Log.d("Args", this.cover)
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
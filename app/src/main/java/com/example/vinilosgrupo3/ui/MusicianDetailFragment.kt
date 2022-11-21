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
import com.example.vinilosgrupo3.databinding.MusicianDetailFragmentBinding
import com.example.vinilosgrupo3.models.Musician
import com.example.vinilosgrupo3.ui.adapters.MusicianDetailAdapter
import com.example.vinilosgrupo3.viewmodels.MusicianDetailViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MusicianDetailFragment : Fragment() {
    private var _binding: MusicianDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MusicianDetailViewModel
    private var viewModelAdapter: MusicianDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MusicianDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = MusicianDetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.musiciandetailRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.musician_details)
        val args: MusicianDetailFragmentArgs by navArgs()
        Log.d("Args", args.musicianId.toString())
        viewModel = ViewModelProvider(this, MusicianDetailViewModel.Factory(activity.application, args.musicianId)).get(MusicianDetailViewModel::class.java)
        viewModel.musiciandetail.observe(viewLifecycleOwner, Observer<Musician> {
            it.apply {
                viewModelAdapter!!.musiciandetail = this
                Log.d("Args", this.image)
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
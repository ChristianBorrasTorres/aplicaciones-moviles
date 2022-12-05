package com.example.vinilosgrupo3.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosgrupo3.R
import com.example.vinilosgrupo3.databinding.AddTrackFragmentBinding
import com.example.vinilosgrupo3.models.Track
import com.example.vinilosgrupo3.viewmodels.TrackViewModel
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AddTrackFragment : Fragment() {
    private var _binding: AddTrackFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TrackViewModel
    //private var viewModelAdapter: TracksAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddTrackFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        //viewModelAdapter = TracksAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var nameTrackTxt:TextInputEditText
        var durationTrackTxt:TextInputEditText

        val appContext = requireContext().applicationContext

        val buttonCancelAddTrack = view.findViewById<Button>(R.id.button_cancel_add_track)
        val buttonAddTrack = view.findViewById<Button>(R.id.button_add_track)

        buttonCancelAddTrack.setOnClickListener {
            val action = AddTrackFragmentDirections.actionAddTrackFragmentToAlbumFragment()
            findNavController().navigate(action)
        }

        buttonAddTrack.setOnClickListener {

            nameTrackTxt  = view.findViewById<TextInputEditText>(R.id.track_name_id)
            durationTrackTxt  = view.findViewById<TextInputEditText>(R.id.track_duration_id)

            val jsonObj = JSONObject()
            jsonObj.put("name", nameTrackTxt.text.toString());
            jsonObj.put("cover", durationTrackTxt.text.toString());

            Log.d("Args", jsonObj.toString())
            val track_id = viewModel.addTrackFromNetwork(jsonObj)
            Log.d("Args", "track_id=$track_id")
            Toast.makeText(activity, "Track agregado", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_add_track)
        val args: DetailFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, TrackViewModel.Factory(activity.application, args.albumId)).get(TrackViewModel::class.java)
        /*viewModel.tracks.observe(viewLifecycleOwner, Observer<List<Track>> {
            it.apply {
                viewModelAdapter!!.tracks = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })*/
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
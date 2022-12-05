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
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosgrupo3.R
import com.example.vinilosgrupo3.databinding.CreateAlbumFragmentBinding
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.ui.adapters.AlbumsAdapter
import com.example.vinilosgrupo3.viewmodels.AlbumViewModel
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateAlbumFragment : Fragment() {
    private var _binding: CreateAlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateAlbumFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var nameAlbumTxt:TextInputEditText
        var coverAlbumTxt:TextInputEditText
        var releaseDateAlbumTxt:TextInputEditText
        var descriptionAlbumTxt:TextInputEditText
        var genreAlbumTxt: String = ""
        var recordLabelAlbumTxt:String = ""

        val appContext = requireContext().applicationContext

        val genres = arrayOf("Classical", "Salsa", "Rock", "Folk")
        val recordLabels = arrayOf("Sony Music","EMI","Discos Fuentes","Elektra","Fania")

        val buttonCancelCreateAlbum = view.findViewById<Button>(R.id.button_cancel_create_album)
        val buttonCreateAlbum = view.findViewById<Button>(R.id.button_create_album)

        buttonCancelCreateAlbum.setOnClickListener {
            val action = CreateAlbumFragmentDirections.actionCreateAlbumFragmentToCollectorFragment()
            findNavController().navigate(action)
        }

        // access the spinner
        val spinnerGenre = view.findViewById<Spinner>(R.id.spinner_genre)
        if (spinnerGenre != null) {
            val adapter = ArrayAdapter(
                appContext,
                android.R.layout.simple_spinner_item, genres
            )
            spinnerGenre.adapter = adapter

            spinnerGenre.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(appContext,getString(R.string.selected_item) + " " + "" + genres[position],Toast.LENGTH_SHORT).show()
                    genreAlbumTxt  = genres[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        val spinnerRecordLabel = view.findViewById<Spinner>(R.id.spinner_record_label)
        if (spinnerRecordLabel != null) {
            val adapter = ArrayAdapter(
                appContext,
                android.R.layout.simple_spinner_item, recordLabels
            )
            spinnerRecordLabel.adapter = adapter

            spinnerRecordLabel.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(appContext,getString(R.string.selected_item) + " " + "" + recordLabels[position],Toast.LENGTH_SHORT).show()
                    recordLabelAlbumTxt  = recordLabels[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        buttonCreateAlbum.setOnClickListener {

            nameAlbumTxt  = view.findViewById<TextInputEditText>(R.id.album_name_id)
            coverAlbumTxt  = view.findViewById<TextInputEditText>(R.id.album_cover_id)
            releaseDateAlbumTxt  = view.findViewById<TextInputEditText>(R.id.album_releaseDate_id)
            descriptionAlbumTxt  = view.findViewById<TextInputEditText>(R.id.album_description_id)
            //val genreAlbumTxt  = view.findViewById<TextInputEditText>(R.id.album_genre_id)
            //recordLabelAlbumTxt  = view.findViewById<TextInputEditText>(R.id.album_record_label_id)

            val jsonObj = JSONObject()
            jsonObj.put("name", nameAlbumTxt.text.toString());
            jsonObj.put("cover", coverAlbumTxt.text.toString());
            jsonObj.put("releaseDate", releaseDateAlbumTxt.text.toString());
            jsonObj.put("description", descriptionAlbumTxt.text.toString());
            jsonObj.put("genre", genreAlbumTxt);
            jsonObj.put("recordLabel", recordLabelAlbumTxt);

            Log.d("Args", jsonObj.toString())
            val album_id = viewModel.createAlbumFromNetwork(jsonObj)
            Log.d("Args", "album_id=$album_id")
            Toast.makeText(activity, "Album creado", Toast.LENGTH_LONG).show()
            /*if (viewModel.createAlbumFromNetwork(jsonObj) != 0) {
                Toast.makeText(activity, "Album creado", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(activity, "Error creando Album", Toast.LENGTH_LONG).show()
            }*/
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_create_albums)
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application)).get(AlbumViewModel::class.java)
        viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                viewModelAdapter!!.albums = this
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
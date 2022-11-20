package com.example.vinilosgrupo3.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilosgrupo3.models.*
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://vinils-app-g3.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getDetail(albumId:Int, onComplete:(resp:Album)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums/$albumId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                var item = Album(albumId = resp.getInt("id"),name = resp.getString("name"), cover = resp.getString("cover"), recordLabel = resp.getString("recordLabel"), releaseDate = resp.getString("releaseDate"), genre = resp.getString("genre"), description = resp.getString("description"))
                Log.d("Args", item.toString())
                onComplete(item)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getMusicians(onComplete:(resp:List<Musician>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()
                val album = mutableListOf<Album>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Musician(musicianId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate"),album))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getMusicianDetail(musicianId:Int, onComplete:(resp:Musician)->Unit, onError: (error:VolleyError)->Unit){
        val album = mutableListOf<Album>()
        requestQueue.add(getRequest("musicians/$musicianId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                var item = Musician(musicianId = resp.getInt("id"),name = resp.getString("name"), image = resp.getString("image"), description = resp.getString("description"), birthDate = resp.getString("birthDate"),album)
                Log.d("Args", item.toString())
                onComplete(item)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                val list = mutableListOf<Collector>()
                val commentsList = mutableListOf<Comment>()
                val favoritePerformersList = mutableListOf<Musician>()
                val collectorAlbumsList = mutableListOf<CollectorAlbums>()

                val resp = JSONArray(response)

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)

                    val commentsJson = item.getJSONArray("comments")
                    for (j in 0 until commentsJson.length()) {
                        val commentItem = commentsJson.getJSONObject(j)
                        //Log.d("Args", commentItem.toString())
                        commentsList.add(j,Comment(commentItem.getInt("id"),commentItem.getString("description"),commentItem.getInt("rating")))
                    }

                    val favoritePerformersJson = item.getJSONArray("favoritePerformers")
                    for (j in 0 until favoritePerformersJson.length()) {
                        val favoritePerformersItem = favoritePerformersJson.getJSONObject(j)
                        //Log.d("Args", favoritePerformersItem.toString())
                        favoritePerformersList.add(j,
                            Musician(favoritePerformersItem.getInt("id"),
                                favoritePerformersItem.getString("name"),
                                favoritePerformersItem.getString("image"),
                                favoritePerformersItem.getString("description"),
                                "", //favoritePerformersItem.getString("creationDate"),
                                mutableListOf<Album>()))
                    }
                    val collectorAlbumsJson = item.getJSONArray("collectorAlbums")
                    for (j in 0 until collectorAlbumsJson.length()) {
                        val collectorAlbumsItem = collectorAlbumsJson.getJSONObject(j)
                        //Log.d("Args", collectorAlbumsItem.toString())
                        collectorAlbumsList.add(j,
                            CollectorAlbums(collectorAlbumsItem.getInt("id"),
                                collectorAlbumsItem.getInt("price"),
                                collectorAlbumsItem.getString("status"))
                        )
                    }
                    list.add(i, Collector(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        telephone = item.getString("telephone"),
                        email = item.getString("email"),
                        comments = commentsList,
                        favoritePerformers = favoritePerformersList,
                        collectorAlbums = collectorAlbumsList
                ))
                }
                Log.d("Args", list.toString())
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getCollectorsDetail(collectorId:Int, onComplete:(resp:Collector)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("collectors/$collectorId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)

                val commentsList = mutableListOf<Comment>()
                val favoritePerformersList = mutableListOf<Musician>()
                val collectorAlbumsList = mutableListOf<CollectorAlbums>()

                val commentsJson = resp.getJSONArray("comments")
                for (i in 0 until commentsJson.length()) {
                    val commentItem = commentsJson.getJSONObject(i)
                    commentsList.add(i,Comment(commentItem.getInt("id"),
                        commentItem.getString("description"),
                        commentItem.getInt("rating")))
                }

                val favoritePerformersJson = resp.getJSONArray("favoritePerformers")
                for (j in 0 until favoritePerformersJson.length()) {
                    val favoritePerformersItem = favoritePerformersJson.getJSONObject(j)
                    //Log.d("Args", favoritePerformersItem.toString())
                    favoritePerformersList.add(j,
                        Musician(favoritePerformersItem.getInt("id"),
                            favoritePerformersItem.getString("name"),
                            favoritePerformersItem.getString("image"),
                            favoritePerformersItem.getString("description"),
                            "",
                            mutableListOf<Album>()))
                }

                val collectorAlbumsJson = resp.getJSONArray("collectorAlbums")
                for (j in 0 until collectorAlbumsJson.length()) {
                    val collectorAlbumsItem = collectorAlbumsJson.getJSONObject(j)
                    //Log.d("Args", collectorAlbumsItem.toString())
                    collectorAlbumsList.add(j,
                        CollectorAlbums(collectorAlbumsItem.getInt("id"),
                            collectorAlbumsItem.getInt("price"),
                            collectorAlbumsItem.getString("status"))
                    )
                }
                var item = Collector(id = resp.getInt("id"),
                    name = resp.getString("name"),
                    telephone = resp.getString("telephone"),
                    email = resp.getString("email"),
                    comments = commentsList,
                    favoritePerformers = favoritePerformersList,
                    collectorAlbums = collectorAlbumsList)


                Log.d("Args", item.toString())
                onComplete(item)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}
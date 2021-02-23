package com.example.cr_noti

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cr_noti.Models.Article
import com.example.cr_noti.Models.Articulo
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    val TAG = "HomeActivity.kt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        peticionNoticias()
    }

    fun peticionNoticias(){

        val peticionesvolley = Volley.newRequestQueue(this)

        val peticionesjson = JsonObjectRequest(Request.Method.GET,url, null,
                    Response.Listener <JSONObject> {
                    response ->
                    try {
                        listaArticulos.removeAll(listaArticulos)
                        listaArticulos.addAll(actuarJSON(response))
                        for (i in listaArticulos) {
                            Log.d(TAG, i.toString())
                        }

                    }catch (e:JSONException){
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error -> Log.d(TAG, error.toString()) }
                )
        peticionesvolley.add(peticionesjson)
    }
    fun actuarJSON(json:JSONObject):ArrayList<Article> {
        val articles:JSONArray = json.getJSONArray("articles")
        val listArticles = ArrayList<Article>()
        for ( i in articles){
            val author = i.getString("author")
            val title = i.getString("title")
            val description = i.getString("description")
            val url = i.getString("url")
            val urlImg = i.getString("urlToImage")
            listArticles.add(Article(author, title, description, url, urlImg))
        }
        return listArticles
    }
}
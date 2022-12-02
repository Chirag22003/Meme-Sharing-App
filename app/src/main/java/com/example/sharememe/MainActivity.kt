package com.example.sharememe

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Video.Media
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.squareup.picasso.Picasso
import org.json.JSONObject
import kotlin.math.log

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val url="https://meme-api.com/gimme"
    private lateinit var memeImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        memeImage = findViewById(R.id.memeImage)


        // Enable JsonObjectRequest
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET,
//            "https://jsonplaceholder.typicode.com/posts/1",
//            null,
//            Response.Listener { response ->
//                // Handle response
//                Log.d("Response", response.toString())
//            },
//            Response.ErrorListener { error ->
//                // Handle error
//                Log.d("Error", error.toString())
//            }
//        )

        loading()
    }
    private fun loading(){
        //API
        //val url = "https://meme-api.herokuapp.com/gimme"
        //Request a random queue
//        val queue = Volley.newRequestQueue(this)
//        //create a JasonObjectRequest
//        val JsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET,url,null,
//            { response ->
                val queue = Volley.newRequestQueue(this)

                val request = JsonObjectRequest(Request.Method.GET, this.url, null,
                    { response ->
                        Log.d("result", response.toString())

                        Picasso.get().load(response.get("url").toString())
                            .placeholder(R.drawable.loading).into(memeImage)
                    },
                    {
                        Log.e("error", it.toString())
                        Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG)
                            .show()
                    }
                )
                queue.add(request)



//                Glide.with(this).load(url).into(memeImage)
//            },
//            {
//                //for API errors
//                Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()
//            }
//
//        )
        //set the Json Request to Queue
       // queue.add(JsonObjectRequest)

// Request a JsonOnjectRequest from the provided URL.


// Add the request to the RequestQueue.

    }



    fun nextMeme(view: View) {
     this.loading()
    }
    fun shareMeme(view: View) {

        val bitmapDrawable = memeImage.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val bitmapPath = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)

        val bitmapUri = Uri.parse(bitmapPath)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        startActivity(Intent.createChooser(intent, "Share To:"))
    }
}
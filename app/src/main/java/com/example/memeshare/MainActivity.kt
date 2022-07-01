@file:Suppress("NAME_SHADOWING")

package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    var currentImageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()


    }


        private fun loadmeme() {
            val progressBar=findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility= View.VISIBLE

// Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "https://meme-api.herokuapp.com/gimme"
            val memeimageview = findViewById<ImageView>(R.id.memeimageview)

// Request a string response from the provided URL.
            val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        currentImageUrl = response.getString("url")
                        Glide.with(this)
                                .load(currentImageUrl)
                            .listener(object:RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progressBar.visibility = View.GONE
                                    return false
                                    TODO("Not yet implemented")
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progressBar.visibility = View.GONE
                                    return false
                                    TODO("Not yet implemented")

                                }
                            })


                                .into(memeimageview)

                        // Display the first 500 characters of the response string.
                    }
            ) { }

// Add the request to the RequestQueue.
            queue.add(jsonObjectRequest)
        }



        fun Nextmeme(view: View) {
            loadmeme()

        }

        fun shareMeme(view: View) {
            val  intent= Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout this cool mee I got from Reddit $currentImageUrl")
            intent.type="text/plain"
            //chooser
            val chooser=Intent.createChooser(intent,"Share this meme using")
            startActivity(chooser)
        }

    }

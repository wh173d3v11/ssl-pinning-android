package com.codecrunchersx.glassyui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hoko.blur.HokoBlur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = findViewById<ImageView>(R.id.bgRoot)
        val bm = (imageView.drawable as BitmapDrawable).bitmap
        val outBitmap: Bitmap = HokoBlur.with(this).radius(12).blur(bm)
        imageView.setImageBitmap(outBitmap)

        findViewById<TextView>(R.id.tvGetStartedButton).setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                callApi()
            }
        }
    }

    private suspend fun callApi() {
        try {
            // Create a URL object for the target server
            val mURL = URL("https://dog.ceo/api/breeds/image/random")
            with(mURL.openConnection() as HttpsURLConnection) {
                requestMethod = "GET"
                // Add any necessary headers here
                println("URL: ${this.url}")
                println("Response Code: ${this.responseCode}")
                // Perform the actual connection and handle the response
                val responseCode = responseCode
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    // Hooray! Connection successful - time to celebrate! 🎉🎉🎉
                    // Now, let's process the response and show off our data handling skills! 🤓📊
                    showToast("Connection Success.")
                } else {
                    // Oops! Handle other response codes (e.g., error cases) gracefully 😅
                    // Every superhero faces challenges - it's how we handle them that matters!
                    showToast("Connection Failed.")
                }
            }
        } catch (e: Throwable) {
            // Uh-oh! Invalid ssl pinning or some other Network errors - but fear not, we're prepared! 💪🛡️
            // Time to troubleshoot and save the day with proper error handling!
            println(e)
            showToast("Connection Error.")
        }
    }

    private suspend fun showToast(s: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT)
                .show()
        }
    }
}
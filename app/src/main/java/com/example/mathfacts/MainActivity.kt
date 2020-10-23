package com.example.mathfacts

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val mathFactRetriever = MathFactRetriever()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isNetworkConnected()) {
            getMathFact()
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection!")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton(android.R.string.ok) {_, _ ->}
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        setContentView(R.layout.activity_main)
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun getMathFact() {
        val mainActivityJob = Job()

        val errorHandler =
            CoroutineExceptionHandler { _, exception ->
                AlertDialog.Builder(this).setTitle("Error!")
                    .setMessage(exception.message)
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }

        val coroutineScope =
            CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {

            val result = mathFactRetriever.getMathFact()
            txtMathFact.text = result.text
        }
    }
}
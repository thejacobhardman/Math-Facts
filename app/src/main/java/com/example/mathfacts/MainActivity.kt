package com.example.mathfacts

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isNetworkConnected()) {
            //getMathFact()
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection!")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton(android.R.string.ok) {_, _ ->}
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        setContentView(R.layout.activity_main)
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
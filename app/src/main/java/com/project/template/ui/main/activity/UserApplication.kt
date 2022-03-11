package com.project.template.ui.main.activity

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.core.content.ContextCompat
import com.project.template.network.NetworkListener

class UserApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val connectivityManager = ContextCompat.getSystemService(
            applicationContext,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), NetworkListener())
    }

}
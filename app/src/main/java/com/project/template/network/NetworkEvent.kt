package com.project.template.network

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData


object NetworkEvents : LiveData<NetworkListener.Event>() {
    internal fun notify(event: NetworkListener.Event) {
        postValue(event)
    }
}

class NetworkListener : ConnectivityManager.NetworkCallback() {

    var networkEvent: NetworkEvents = NetworkEvents

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        val connectionAvailable: Event = Event.ConnectivityAvailable
        networkEvent.notify(connectionAvailable)
        Log.i("----->", "onAvailable: $network")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        val connectivityLost: Event = Event.ConnectivityLost
        networkEvent.notify(connectivityLost)
        Log.i("----->", "onLost: $network")
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        val networkCapabilityChanged: Event = Event.NetworkCapabilityChanged(networkCapabilities)
        networkEvent.notify(networkCapabilityChanged)
        Log.i("----->", "onCapabilitiesChanged: $network")
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties)
        val linkPropertyChanged: Event = Event.LinkPropertyChanged(linkProperties)
        networkEvent.notify(linkPropertyChanged)
        Log.i("----->", "onLinkPropertiesChanged: $network")
    }

    sealed class Event {
        object ConnectivityLost : Event()
        object ConnectivityAvailable : Event()
        data class NetworkCapabilityChanged(val old: NetworkCapabilities?) : Event()
        data class LinkPropertyChanged(val old: LinkProperties?) : Event()
    }

}

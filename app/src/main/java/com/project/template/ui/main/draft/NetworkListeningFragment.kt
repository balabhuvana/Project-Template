package com.project.template.ui.main.draft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.project.template.R
import com.project.template.network.NetworkListener

class NetworkListeningFragment : Fragment() {

    private var isNetworkAvailable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_network_listening, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkListener().networkEvent.observe(
            viewLifecycleOwner
        ) { event ->
            when (event) {
                is NetworkListener.Event.ConnectivityAvailable -> {
                    isNetworkAvailable = true
                    showSnackBar(view, "Network is Available ")
                }
                is NetworkListener.Event.ConnectivityLost -> {
                    isNetworkAvailable = false
                    showSnackBar(view, "Network is not available ")
                }
                is NetworkListener.Event.NetworkCapabilityChanged -> {

                }
                is NetworkListener.Event.LinkPropertyChanged -> {

                }
            }
        }
    }

    private fun showSnackBar(view: View, displayText: String) {
        Snackbar.make(view, displayText, Snackbar.LENGTH_LONG).show()
    }
}
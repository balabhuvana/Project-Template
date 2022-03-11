package com.project.template.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.template.R
import com.project.template.network.NetworkListener
import com.project.template.utils.CommonUtils

open class BaseFragment : Fragment() {

    var isNetworkAvailable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkListener().networkEvent.observe(
            viewLifecycleOwner
        ) { event ->
            when (event) {
                is NetworkListener.Event.ConnectivityAvailable -> {
                    isNetworkAvailable = true
                    CommonUtils.showSnackBar(view, getString(R.string.network_available))
                }
                is NetworkListener.Event.ConnectivityLost -> {
                    isNetworkAvailable = false
                    CommonUtils.showSnackBar(view, getString(R.string.network_not_available))
                }
                is NetworkListener.Event.NetworkCapabilityChanged -> {

                }
                is NetworkListener.Event.LinkPropertyChanged -> {

                }
            }
        }
    }

}
package com.project.template.ui.main.draft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.template.databinding.FragmentNetworkListeningBinding
import com.project.template.ui.main.fragment.BaseFragment
import com.project.template.utils.CommonUtils

class NetworkListeningFragment : BaseFragment() {

    lateinit var binding: FragmentNetworkListeningBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNetworkListeningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCheckNetwork.setOnClickListener {
            CommonUtils.showSnackBar(view, isNetworkAvailable.toString())
        }
    }

}

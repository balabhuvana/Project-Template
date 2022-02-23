package com.project.template.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.project.template.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationToLoginFragment()
            it.findNavController().navigate(action)
        }

        binding.tvNewToApp.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationToLoginFragment()
            it.findNavController().navigate(action)
        }

        binding.registerButton.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationToUserFragment()
            it.findNavController().navigate(action)
        }
    }
}
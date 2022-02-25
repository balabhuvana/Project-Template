package com.project.template.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.project.template.databinding.FragmentLoginBinding
import com.project.template.model.LoginRequestModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            /*val action = LoginFragmentDirections.actionLoginToUserFragment()
            it.findNavController().navigate(action)*/
            loginViewModel.loginApiViewModel(buildLoginRequestObject("eve.holt@reqres.in", "cityslicka"))
        }

        binding.tvNewToApp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginToRegistrationFragment()
            it.findNavController().navigate(action)
        }
    }

    fun buildLoginRequestObject(email: String, password: String) = LoginRequestModel(email, password)

}

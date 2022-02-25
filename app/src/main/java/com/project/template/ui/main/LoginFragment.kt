package com.project.template.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.project.template.databinding.FragmentLoginBinding
import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginUiState
import com.project.template.network.RetrofitClient
import com.project.template.repo.LoginRDSViaFlow
import com.project.template.repo.LoginRepoViaFlow
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModelViaFlow: LoginViewModelViaFlow by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            /*val action = LoginFragmentDirections.actionLoginToUserFragment()
            it.findNavController().navigate(action)*/
            val loginWebService = RetrofitClient.instance?.getMyApi()
            val loginRDSViaFlow = LoginRDSViaFlow(loginWebService)
            val loginRepoViaFlow = LoginRepoViaFlow(loginRDSViaFlow)
            loginViewModelViaFlow.loginApiViewModel(loginRepoViaFlow)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    loginViewModelViaFlow.uiState.collect { uiState ->
                        // New value received
                        when (uiState) {
                            is LoginUiState.Success -> {
                                Log.i("------> ", "" + uiState.loginResponseModel?.token)
                            }
                            is LoginUiState.Error -> {
                                Log.i("------> ", "" + uiState.exception.message)
                            }
                        }
                    }
                }
            }
        }

        binding.tvNewToApp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginToRegistrationFragment()
            it.findNavController().navigate(action)
        }
    }

    fun buildLoginRequestObject(email: String, password: String) = LoginRequestModel(email, password)

}

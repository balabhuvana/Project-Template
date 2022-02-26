package com.project.template.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
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
            val loginWebService = RetrofitClient.instance?.getMyApi()
            val loginRDSViaFlow = LoginRDSViaFlow(loginWebService)
            val loginRepoViaFlow = LoginRepoViaFlow(loginRDSViaFlow)
            val loginRequestModel = LoginRequestModel("eve.holt@reqres.in", "cityslicka")
            loginViewModelViaFlow.loginApiViewModel(loginRequestModel, loginRepoViaFlow)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    loginViewModelViaFlow.uiState.collect { uiState ->
                        when (uiState) {
                            is LoginUiState.Success -> {
                                Snackbar.make(it, "Successfully login", Snackbar.LENGTH_LONG).show()
                                val action = LoginFragmentDirections.actionLoginToUserFragment()
                                it.findNavController().navigate(action)
                            }
                            is LoginUiState.Error -> {
                                Snackbar.make(it, "" + uiState.exception.message, Snackbar.LENGTH_LONG).show()
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

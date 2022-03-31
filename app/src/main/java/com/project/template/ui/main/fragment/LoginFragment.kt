package com.project.template.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.project.template.R
import com.project.template.databinding.FragmentLoginBinding
import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginUiState
import com.project.template.ui.main.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
            showOrHideProgressBar(View.VISIBLE)
            val loginRequestModel = buildLoginRequestObject(
                binding.etUsernameLogin.text.toString(), binding.etPasswordLogin.text.toString()
            )

            requestLoginApiCall(it, loginRequestModel)
        }

        binding.tvNewToApp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginToRegistrationFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun buildLoginRequestObject(email: String, password: String) = LoginRequestModel(email, password)

    private fun launchScreen(view: View, action: NavDirections) {
        view.findNavController().navigate(action)
    }

    private fun showSnackBar(view: View, displayText: String) {
        Snackbar.make(view, displayText, Snackbar.LENGTH_LONG).show()
    }

    private fun requestLoginApiCall(
        view: View,
        loginRequestModel: LoginRequestModel
    ) {
        loginViewModel.loginApiViewModel(loginRequestModel)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is LoginUiState.Success -> {
                            val token = uiState.loginResponseModel?.token
                            if (token?.isNotEmpty() == true) {
                                showSnackBar(view, getString(R.string.login_successfully))
                                launchScreen(view, LoginFragmentDirections.actionLoginToUserFragment())
                                showOrHideProgressBar(View.GONE)
                            } else {
                                showSnackBar(view, "token is empty")
                            }
                        }
                        is LoginUiState.Error -> {
                            showSnackBar(view, "" + uiState.exception.message)
                            showOrHideProgressBar(View.GONE)
                        }
                    }
                }
            }
        }
    }

    private fun showOrHideProgressBar(showOrHide: Int) {
        _binding?.loginProgressBar?.visibility = showOrHide
    }

}

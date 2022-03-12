package com.project.template.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.project.template.R
import com.project.template.databinding.FragmentLoginBinding
import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginUiState
import com.project.template.network.RetrofitClient
import com.project.template.repo.login.LoginRDSViaFlow
import com.project.template.repo.login.LoginRepoViaFlow
import com.project.template.ui.main.viewmodels.LoginViewModelViaFlow
import com.project.template.utils.CommonUtils
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment() {

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
            CommonUtils.showOrHideProgressBar(binding.loginProgressBar, View.VISIBLE)
            val loginWebService = RetrofitClient.instance?.getMyApi()
            val loginRDSViaFlow = LoginRDSViaFlow(loginWebService)
            val loginRepoViaFlow = LoginRepoViaFlow(loginRDSViaFlow)
            val loginRequestModel = buildLoginRequestObject(
                binding.etUsernameLogin.text.toString(), binding.etPasswordLogin.text.toString()
            )

            requestLoginApiCall(it, loginRequestModel, loginRepoViaFlow)
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

    private fun requestLoginApiCall(
        view: View,
        loginRequestModel: LoginRequestModel,
        loginRepoViaFlow: LoginRepoViaFlow
    ) {
        loginViewModelViaFlow.loginApiViewModel(loginRequestModel, loginRepoViaFlow)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModelViaFlow.uiState.collect { uiState ->
                    when (uiState) {
                        is LoginUiState.Success -> {
                            val token = uiState.loginResponseModel?.token
                            if (token?.isNotEmpty() == true) {
                                CommonUtils.showSnackBar(view, getString(R.string.login_successfully))
                                launchScreen(view, LoginFragmentDirections.actionLoginToUserFragment())
                                CommonUtils.showOrHideProgressBar(binding.loginProgressBar, View.GONE)
                            } else {
                                CommonUtils.showSnackBar(view, "token is empty")
                            }
                        }
                        is LoginUiState.Error -> {
                            CommonUtils.showSnackBar(view, "" + uiState.exception.message)
                            CommonUtils.showOrHideProgressBar(binding.loginProgressBar, View.GONE)
                        }
                    }
                }
            }
        }
    }

}

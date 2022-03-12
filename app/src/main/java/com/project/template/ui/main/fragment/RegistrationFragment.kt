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
import com.project.template.databinding.FragmentRegistrationBinding
import com.project.template.model.RegistrationRequestModel
import com.project.template.model.RegistrationUiState
import com.project.template.network.RetrofitClient
import com.project.template.repo.registration.RegistrationRdsViaFlow
import com.project.template.repo.registration.RegistrationRepoViaFlow
import com.project.template.ui.main.viewmodels.RegistrationViewModelViaFlow
import com.project.template.utils.CommonUtils
import kotlinx.coroutines.launch

class RegistrationFragment : BaseFragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val registrationViewModel: RegistrationViewModelViaFlow by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
            launchScreen(it, RegistrationFragmentDirections.actionRegistrationToLoginFragment())
        }

        binding.tvNewToApp.setOnClickListener {
            launchScreen(it, RegistrationFragmentDirections.actionRegistrationToLoginFragment())
        }

        binding.registerButton.setOnClickListener {
            CommonUtils.showOrHideProgressBar(binding.registrationProgressBar, View.VISIBLE)
            val apiWebService = RetrofitClient.instance?.getMyApi()
            val registrationRDSViaFlow = RegistrationRdsViaFlow(apiWebService)
            val registrationRepoViaFlow = RegistrationRepoViaFlow(registrationRDSViaFlow)
            val registrationRequestModel = buildRegisterRequestObject(
                binding.etEmail.text.toString(),
                binding.etPasswordRegistration.text.toString()
            )
            requestApiCall(view, registrationRequestModel, registrationRepoViaFlow)
        }
    }

    private fun buildRegisterRequestObject(email: String, password: String) = RegistrationRequestModel(email, password)

    private fun launchScreen(view: View, action: NavDirections) {
        view.findNavController().navigate(action)
    }

    private fun requestApiCall(
        view: View,
        registrationRequestModel: RegistrationRequestModel,
        registrationRepoViaFlow: RegistrationRepoViaFlow
    ) {
        registrationViewModel.registrationApiCallViewModel(registrationRequestModel, registrationRepoViaFlow)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registrationViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is RegistrationUiState.Success -> {
                            if (uiState.registrationResponseModel?.token?.isNotEmpty() == true) {
                                CommonUtils.showSnackBar(view, getString(R.string.register_successfully))
                                launchScreen(
                                    view,
                                    RegistrationFragmentDirections.actionRegistrationToUserListFragment()
                                )
                                CommonUtils.showOrHideProgressBar(binding.registrationProgressBar, View.GONE)
                            } else {
                                CommonUtils.showSnackBar(view, "token is empty")
                            }
                        }
                        is RegistrationUiState.Error -> {
                            CommonUtils.showSnackBar(view, "" + uiState.exception.message)
                            CommonUtils.showOrHideProgressBar(binding.registrationProgressBar, View.GONE)
                        }
                    }
                }
            }
        }
    }

}
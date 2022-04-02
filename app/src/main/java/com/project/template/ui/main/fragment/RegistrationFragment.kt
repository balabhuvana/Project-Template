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
import com.project.template.databinding.FragmentRegistrationBinding
import com.project.template.model.RegistrationRequestModel
import com.project.template.model.RegistrationUiState
import com.project.template.ui.main.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val registrationViewModel: RegistrationViewModel by activityViewModels()

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
            showOrHideProgressBar(View.VISIBLE)
            val registrationRequestModel = buildRegisterRequestObject(
                binding.etEmail.text.toString(), binding.etPasswordRegistration.text.toString()
            )
            requestApiCall(view, registrationRequestModel)
        }
    }

    private fun buildRegisterRequestObject(email: String, password: String) = RegistrationRequestModel(email, password)

    private fun launchScreen(view: View, action: NavDirections) {
        view.findNavController().navigate(action)
    }

    private fun showSnackBar(view: View, displayText: String) {
        Snackbar.make(view, displayText, Snackbar.LENGTH_LONG).show()
    }

    private fun requestApiCall(
        view: View, registrationRequestModel: RegistrationRequestModel
    ) {
        registrationViewModel.registrationApiCallViewModel(registrationRequestModel)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registrationViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is RegistrationUiState.Success -> {
                            if (uiState.registrationResponseModel?.token?.isNotEmpty() == true) {
                                showSnackBar(view, getString(R.string.register_successfully))
                                launchScreen(
                                    view, RegistrationFragmentDirections.actionRegistrationToUserListFragment()
                                )
                                showOrHideProgressBar(View.GONE)
                            } else {
                                showSnackBar(view, "token is empty")
                            }
                        }
                        is RegistrationUiState.Error -> {
                            showSnackBar(view, "" + uiState.exception.message)
                            showOrHideProgressBar(View.GONE)
                        }
                    }
                }
            }
        }
    }

    private fun showOrHideProgressBar(showOrHide: Int) {
        _binding?.registrationProgressBar?.visibility = showOrHide
    }
}
package com.project.template.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.RegistrationRequestModel
import com.project.template.model.RegistrationResponseModel
import com.project.template.model.RegistrationUiState
import com.project.template.repo.registration.RegistrationRepoViaFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RegistrationViewModelViaFlow : ViewModel() {

    private val _uiState =
        MutableStateFlow<RegistrationUiState>(RegistrationUiState.Success(RegistrationResponseModel(0, "")))
    val uiState: StateFlow<RegistrationUiState> = _uiState

    fun registrationApiCallViewModel(
        registrationRequestModel: RegistrationRequestModel, registrationRepoViaFlow: RegistrationRepoViaFlow
    ) {
        viewModelScope.launch {
            registrationRepoViaFlow.registrationApiCallRepo(registrationRequestModel)
                .catch { exception ->
                    _uiState.value = RegistrationUiState.Error(exception)
                }
                .collect {
                    _uiState.value = RegistrationUiState.Success(it)
                }
        }
    }

}
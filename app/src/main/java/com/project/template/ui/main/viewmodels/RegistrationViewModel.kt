package com.project.template.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.RegistrationRequestModel
import com.project.template.model.RegistrationResponseModel
import com.project.template.model.RegistrationUiState
import com.project.template.repo.registration.RegistrationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private var registrationRepo: RegistrationRepo) : ViewModel() {

    private val _uiState =
        MutableStateFlow<RegistrationUiState>(RegistrationUiState.Success(RegistrationResponseModel(0, "")))
    val uiState: StateFlow<RegistrationUiState> = _uiState

    fun registrationApiCallViewModel(registrationRequestModel: RegistrationRequestModel) {
        viewModelScope.launch {
            registrationRepo.registrationApiCallRepo(registrationRequestModel)
                .catch { exception ->
                    _uiState.value = RegistrationUiState.Error(exception)
                }
                .collect {
                    _uiState.value = RegistrationUiState.Success(it)
                }
        }
    }

}
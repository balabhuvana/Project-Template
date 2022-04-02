package com.project.template.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.UserDetailUIState
import com.project.template.repo.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(private var userRepo: UserRepo) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUIState>(UserDetailUIState.Success(null))
    val uiState: StateFlow<UserDetailUIState> = _uiState

    fun fetchUserDetailViaVM(userId: String) {
        viewModelScope.launch {
            userRepo.fetchUserDetailRepo(userId)
                .catch { exception ->
                    _uiState.value = UserDetailUIState.Failure(exception)
                }
                .collect {
                    val singleUser = it
                    _uiState.value = UserDetailUIState.Success(singleUser?.user)
                }
        }
    }

    fun fetchUserDetailFromRoomVM(userId: Int) {
        viewModelScope.launch {
            userRepo.fetchUserDetailFromRoomRepoCall(userId)
                .catch { exception ->
                    _uiState.value = UserDetailUIState.Failure(exception)
                }.collect {
                    _uiState.value = UserDetailUIState.Success(it)
                }
        }
    }

}
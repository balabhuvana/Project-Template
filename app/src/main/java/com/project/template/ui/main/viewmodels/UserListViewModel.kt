package com.project.template.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.UserUIState
import com.project.template.repo.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private var userRepo: UserRepo) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UserUIState>(UserUIState.Success(emptyList()))

    val uiState: StateFlow<UserUIState> = _uiState

    fun fetchUserList() {
        viewModelScope.launch {
            userRepo.fetchUserListViaRepo()
                .catch { exception ->
                    _uiState.value = UserUIState.Failure(exception)
                }.collect {
                    _uiState.value = UserUIState.Success(it)
                }
        }
    }

    fun fetchUserListFromRestAndStoreItInRoomViaVM() {
        viewModelScope.launch {
            userRepo.fetchUserListFromRestAndStoreItInRoomViaRepo()
        }
    }

    fun listenUserListOfflineSupportVM() {
        viewModelScope.launch {
            userRepo.fetchUserListRDSOfflineSupport()
                .catch { exception ->
                    _uiState.value = UserUIState.Failure(exception)
                }.collect {
                    _uiState.value = UserUIState.Success(it)
                }
        }
    }

}
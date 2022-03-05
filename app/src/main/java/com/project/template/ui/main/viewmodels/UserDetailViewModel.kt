package com.project.template.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.UserDetailUIState
import com.project.template.repo.user.UserRepoViaFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UserDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUIState>(UserDetailUIState.Success(null))
    val uiState: StateFlow<UserDetailUIState> = _uiState

    fun fetchUserDetailViaVM(userId: String, userRepoViaFlow: UserRepoViaFlow) {
        viewModelScope.launch {
            userRepoViaFlow.fetchUserDetailRepo(userId)
                .catch { exception ->
                    _uiState.value = UserDetailUIState.Failure(exception)
                }
                .collect {
                    val singleUser = it;
                    _uiState.value = UserDetailUIState.Success(singleUser)
                }
        }
    }

}
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.project.template.databinding.FragmentUserBinding
import com.project.template.model.User
import com.project.template.model.UserUIState
import com.project.template.network.RetrofitClient
import com.project.template.repo.user.UserRdsViaFlow
import com.project.template.repo.user.UserRepoViaFlow
import com.project.template.room.UserDatabase
import com.project.template.ui.main.adapter.UserListAdapter
import com.project.template.ui.main.viewmodels.UserListViewModel
import com.project.template.utils.CommonUtils
import kotlinx.coroutines.launch

class UserListFragment : BaseFragment() {

    private lateinit var fragmentUserBinding: FragmentUserBinding
    private val userListViewModel: UserListViewModel by activityViewModels()
    private lateinit var userListRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentUserBinding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return fragmentUserBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDatabase = Room.databaseBuilder(
            context!!,
            UserDatabase::class.java, "database-name"
        ).build()
        val userDao = userDatabase.userDao()

        val apiWebService = RetrofitClient.instance?.getMyApi()
        val userRdsViaFlow = UserRdsViaFlow(userDao, apiWebService)
        val userRepoViaFlow = UserRepoViaFlow(userRdsViaFlow)

        fetchUserListFetchUserListFromRestAndSaveItInRoomOfflineSupport(view, userRepoViaFlow)
    }

    private fun fetchUserListFetchUserListFromRestAndSaveItInRoomOfflineSupport(
        view: View,
        userRepoViaFlow: UserRepoViaFlow
    ) {
        userListViewModel.fetchUserListFromRestAndStoreItInRoomViaVM(userRepoViaFlow)
        userListViewModel.listenUserListOfflineSupportVM(userRepoViaFlow)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userListViewModel.uiState.collect {
                    when (it) {
                        is UserUIState.Success -> {
                            it.userList?.let { userList ->
                                updateUserListAdapter(userList)
                            }
                        }
                        is UserUIState.Failure -> {
                            CommonUtils.showSnackBar(view, it.exception.message.toString())
                        }
                    }

                }
            }
        }
    }

    private fun fetchUserList(view: View, userRepoViaFlow: UserRepoViaFlow) {
        userListViewModel.fetchUserList(userRepoViaFlow)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userListViewModel.uiState.collect { it ->
                    when (it) {
                        is UserUIState.Success -> {
                            it.userList?.let { userList ->
                                updateUserListAdapter(userList)
                            }
                        }
                        is UserUIState.Failure -> {
                            CommonUtils.showSnackBar(view, it.exception.message.toString())
                        }
                    }
                }
            }
        }
    }

    private fun updateUserListAdapter(userList: List<User>) {
        val userListAdapter = UserListAdapter(context, userList)
        userListRecyclerView = fragmentUserBinding.userRecyclerView
        userListRecyclerView.adapter = userListAdapter
        userListRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

}
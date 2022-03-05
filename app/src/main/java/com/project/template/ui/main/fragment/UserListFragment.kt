package com.project.template.ui.main.fragment

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.snackbar.Snackbar
import com.project.template.databinding.FragmentUserBinding
import com.project.template.model.UserUIState
import com.project.template.network.RetrofitClient
import com.project.template.repo.user.UserRdsViaFlow
import com.project.template.repo.user.UserRepoViaFlow
import com.project.template.ui.main.adapter.UserListAdapter
import com.project.template.ui.main.viewmodels.UserListViewModel
import kotlinx.coroutines.launch

class UserListFragment : Fragment() {

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

        fetchUserList(view)
    }

    private fun fetchUserList(view: View) {

        val apiWebService = RetrofitClient.instance?.getMyApi()
        val userRdsViaFlow = UserRdsViaFlow(apiWebService)
        val userRepoViaFlow = UserRepoViaFlow(userRdsViaFlow)
        userListViewModel.fetchUserList(userRepoViaFlow)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userListViewModel.uiState.collect { it ->
                    when (it) {
                        is UserUIState.Success -> {
                            Log.i("------>", "User list${it.userListRoot?.userModelList}")
                            it.userListRoot?.userModelList?.let {
                                val userListAdapter = UserListAdapter(it)
                                userListRecyclerView = fragmentUserBinding.userRecyclerView
                                userListRecyclerView.adapter = userListAdapter
                                userListRecyclerView.layoutManager = LinearLayoutManager(activity)
                            }
                        }
                        is UserUIState.Failure -> {
                            showSnackBar(view, it.exception.message.toString())
                        }
                    }
                }
            }
        }
    }

    private fun showSnackBar(view: View, displayText: String) {
        Snackbar.make(view, displayText, Snackbar.LENGTH_LONG).show()
    }

}
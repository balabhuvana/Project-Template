package com.project.template.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.project.template.R
import com.project.template.databinding.FragmentUserDetailBinding
import com.project.template.model.User
import com.project.template.model.UserDetailUIState
import com.project.template.network.RetrofitClient
import com.project.template.repo.user.UserRdsViaFlow
import com.project.template.repo.user.UserRepoViaFlow
import com.project.template.room.UserDatabase
import com.project.template.ui.main.viewmodels.UserDetailViewModel
import com.project.template.utils.CommonUtils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class UserDetailFragment : BaseFragment() {

    private val userDetailViewModel: UserDetailViewModel by activityViewModels()
    private lateinit var binding: FragmentUserDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
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

        val safeArgs: UserDetailFragmentArgs by navArgs()
        val userId = safeArgs.userIdArgs

        CommonUtils.showOrHideProgressBar(binding.detailProgressBar, View.VISIBLE)
        fetchUserDetailFromWithRestApiAndRoom(view, userId, userRepoViaFlow)
    }

    private fun fetchUserDetailFromWithRestApiAndRoom(view: View, userId: Int, userRepoViaFlow: UserRepoViaFlow) {
        userDetailViewModel.fetchUserDetailViaVM(userId.toString(), userRepoViaFlow)
        userDetailViewModel.fetchUserDetailFromRoomVM(userId, userRepoViaFlow)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailViewModel.uiState.collect() {
                    when (it) {
                        is UserDetailUIState.Success -> {
                            setUserData(it.user)
                        }
                        is UserDetailUIState.Failure -> {
                            CommonUtils.showSnackBar(view, it.exception.message.toString())
                            CommonUtils.showOrHideProgressBar(binding.detailProgressBar, View.GONE)
                        }
                    }
                }
            }
        }
    }

    private fun fetchUserDetail(view: View, userId: String, userRepoViaFlow: UserRepoViaFlow) {

        userDetailViewModel.fetchUserDetailViaVM(userId, userRepoViaFlow)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailViewModel.uiState.collect() {
                    when (it) {
                        is UserDetailUIState.Success -> {
                            setUserData(it.user)
                        }
                        is UserDetailUIState.Failure -> {
                            CommonUtils.showSnackBar(view, it.exception.message.toString())
                            CommonUtils.showOrHideProgressBar(binding.detailProgressBar, View.GONE)
                        }
                    }
                }
            }
        }

    }

    private fun setUserData(user: User?) {
        binding.tvFirstName.text = user?.userFirstName
        binding.tvLastName.text = user?.userLastName
        binding.tvEmail.text = user?.userEmail
        CommonUtils.showOrHideProgressBar(binding.detailProgressBar, View.GONE)

        Picasso.get()
            .load(user?.userAvatar).fit().centerCrop()
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(binding.ivUserImage)
    }
}
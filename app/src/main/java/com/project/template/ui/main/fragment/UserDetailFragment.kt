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
import com.project.template.R
import com.project.template.databinding.FragmentUserDetailBinding
import com.project.template.model.User
import com.project.template.model.UserDetailUIState
import com.project.template.network.RetrofitClient
import com.project.template.repo.user.UserRdsViaFlow
import com.project.template.repo.user.UserRepoViaFlow
import com.project.template.ui.main.viewmodels.UserDetailViewModel
import com.project.template.utils.CommonUtils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class UserDetailFragment : BaseFragment() {

    private val userDetailViewModel: UserDetailViewModel by activityViewModels()
    private lateinit var userDetailBinding: FragmentUserDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userDetailBinding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return userDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: UserDetailFragmentArgs by navArgs()
        val userId = safeArgs.userIdArgs

        CommonUtils.showOrHideProgressBar(userDetailBinding.detailProgressBar, View.VISIBLE)
        fetchUserDetail(view, userId.toString())
    }

    private fun fetchUserDetail(view: View, userId: String) {
        val apiWebService = RetrofitClient.instance?.getMyApi()
        val userRdsViaFlow = UserRdsViaFlow(apiWebService)
        val userRepoViaFlow = UserRepoViaFlow(userRdsViaFlow)
        userDetailViewModel.fetchUserDetailViaVM(userId, userRepoViaFlow)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailViewModel.uiState.collect {
                    when (it) {
                        is UserDetailUIState.Success -> {
                            if (it.singleUser?.user != null) {
                                setUserData(it.singleUser?.user)
                            }
                            CommonUtils.showOrHideProgressBar(userDetailBinding.detailProgressBar, View.GONE)
                        }
                        is UserDetailUIState.Failure -> {
                            CommonUtils.showSnackBar(view, it.exception.message.toString())
                            CommonUtils.showOrHideProgressBar(userDetailBinding.detailProgressBar, View.GONE)
                        }
                    }
                }
            }
        }
    }

    private fun setUserData(user: User?) {
        userDetailBinding.tvFirstName.text = user?.userFirstName
        userDetailBinding.tvLastName.text = user?.userLastName
        userDetailBinding.tvEmail.text = user?.userEmail

        Picasso.get()
            .load(user?.userAvatar).fit().centerCrop()
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(userDetailBinding.ivUserImage)
    }
}
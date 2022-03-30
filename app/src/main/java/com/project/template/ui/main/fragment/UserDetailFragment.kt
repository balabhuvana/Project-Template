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
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.project.template.R
import com.project.template.databinding.FragmentUserDetailBinding
import com.project.template.model.User
import com.project.template.model.UserDetailUIState
import com.project.template.network.RetrofitClient
import com.project.template.repo.user.UserRds
import com.project.template.repo.user.UserRepo
import com.project.template.room.UserDatabase
import com.project.template.ui.main.viewmodels.UserDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

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

        val userDatabase = Room.databaseBuilder(
            context!!,
            UserDatabase::class.java, "database-name"
        ).build()
        val userDao = userDatabase.userDao()


        val apiWebService = RetrofitClient.instance?.getMyApi()
        val userRds = UserRds(userDao, apiWebService)
        val userRepo = UserRepo(userRds)

        val safeArgs: UserDetailFragmentArgs by navArgs()
        val userId = safeArgs.userIdArgs

        showOrHideProgressBar(View.VISIBLE)
        fetchUserDetailFromWithRestApiAndRoom(view, userId, userRepo)
    }

    private fun fetchUserDetailFromWithRestApiAndRoom(view: View, userId: Int, userRepo: UserRepo) {
        userDetailViewModel.fetchUserDetailViaVM(userId.toString(), userRepo)
        userDetailViewModel.fetchUserDetailFromRoomVM(userId, userRepo)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailViewModel.uiState.collect() {
                    when (it) {
                        is UserDetailUIState.Success -> {
                            setUserData(it.user)
                        }
                        is UserDetailUIState.Failure -> {
                            showSnackBar(view, it.exception.message.toString())
                            showOrHideProgressBar(View.GONE)
                        }
                    }
                }
            }
        }
    }

    private fun fetchUserDetail(view: View, userId: String, userRepo: UserRepo) {

        userDetailViewModel.fetchUserDetailViaVM(userId, userRepo)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailViewModel.uiState.collect() {
                    when (it) {
                        is UserDetailUIState.Success -> {
                            setUserData(it.user)
                        }
                        is UserDetailUIState.Failure -> {
                            showSnackBar(view, it.exception.message.toString())
                            showOrHideProgressBar(View.GONE)
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
        showOrHideProgressBar(View.GONE)

        Picasso.get()
            .load(user?.userAvatar).fit().centerCrop()
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(userDetailBinding.ivUserImage)
    }

    private fun showSnackBar(view: View, displayText: String) {
        Snackbar.make(view, displayText, Snackbar.LENGTH_LONG).show()
    }

    private fun showOrHideProgressBar(showOrHide: Int) {
        userDetailBinding.detailProgressBar.visibility = showOrHide
    }
}
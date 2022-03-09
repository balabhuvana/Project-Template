package com.project.template.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.project.template.R
import com.project.template.databinding.UserListViewRowBinding
import com.project.template.model.User
import com.project.template.ui.main.fragment.UserListFragmentDirections
import com.squareup.picasso.Picasso

class UserListAdapter(private var userModelList: List<User>) :
    RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    inner class UserListViewHolder(val binding: UserListViewRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = UserListViewRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.binding.tvFirstName.text = userModelList[position].userFirstName
        holder.binding.tvLastName.text = userModelList[position].userLastName
        holder.binding.tvEmail.text = userModelList[position].userEmail

        Picasso.get()
            .load(userModelList[position].userAvatar).fit().centerCrop()
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(holder.binding.ivUserImage)

        holder.binding.root.setOnClickListener {
            val userIdArgs = userModelList[position].id
            val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(userIdArgs)
            launchScreen(it, action)
        }

    }

    override fun getItemCount(): Int = userModelList.size

    private fun launchScreen(view: View, action: NavDirections) {
        view.findNavController().navigate(action)
    }
}
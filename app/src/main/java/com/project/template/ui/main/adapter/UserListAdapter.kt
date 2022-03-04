package com.project.template.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.template.R
import com.project.template.model.User
import com.squareup.picasso.Picasso

class UserListAdapter(var userModelList: List<User>) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val ivImage: ImageView
        val tvFirstName: TextView
        val tvLastName: TextView
        val tvEmail: TextView

        init {
            ivImage = view.findViewById(R.id.ivUserImage)
            tvFirstName = view.findViewById(R.id.tvFirstName)
            tvLastName = view.findViewById(R.id.tvLastName)
            tvEmail = view.findViewById(R.id.tvEmail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_view_row, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.tvFirstName.text = userModelList[position].userFirstName
        holder.tvLastName.text = userModelList[position].userLastName
        holder.tvEmail.text = userModelList[position].userEmail
        Picasso.get()
            .load(userModelList[position].userAvatar).into(holder.ivImage)

        Picasso.get()
            .load(userModelList[position].userAvatar).fit().centerCrop()
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(holder.ivImage)

    }

    override fun getItemCount(): Int = userModelList.size

}
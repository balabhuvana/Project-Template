package com.project.template.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.project.template.databinding.FragmentLearnRoomBinding
import com.project.template.model.User
import com.project.template.room.UserDatabase
import kotlinx.coroutines.launch

class LearnRoomFragment : Fragment() {

    private lateinit var binding: FragmentLearnRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLearnRoomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDatabase = Room.databaseBuilder(
            context!!,
            UserDatabase::class.java, "database-name"
        ).build()
        val userDao = userDatabase.userDao()

        binding.btnInsert.setOnClickListener {
            val user = User(userEmail = "arun@gmail.com", userFirstName = "Arun", userLastName = "Kumar")
            lifecycleScope.launch {
                userDao.insertUser(user)
            }
        }

        binding.btnSelect.setOnClickListener {
            lifecycleScope.launch {
                val user = userDao.findUserByName(firstName = "Arun", lastName = "Kumar")
                Log.i("----> ", "User: $user")
            }
        }
    }
}
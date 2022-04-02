package com.project.template.ui.main.draft

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
import com.project.template.room.UserDao
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

        listenTableChanges(userDao)

        binding.btnInsert.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = "$firstName@gmail.com"
            val user = User(userEmail = email, userFirstName = firstName, userLastName = lastName)
            lifecycleScope.launch {
                userDao.insertUser(user)
            }
        }

        binding.btnGetUserById.setOnClickListener {
            lifecycleScope.launch {
                val userId = binding.etUserId.text.toString()
                if (userId.isNotEmpty()) {
                    userDao.findUserById(userId.toInt()).collect {
                        Log.i("----> ", "User: ${it.userEmail}")
                    }
                }
            }
        }

        binding.btnGetAllUser.setOnClickListener {
            lifecycleScope.launch {
                userDao.getAllUser().collect {
                    Log.i("----> ", "User: $it")
                }
            }
        }

        binding.btnDeleteUserById.setOnClickListener {
            lifecycleScope.launch {
                val userId = binding.etUserId.text.toString()
                if (userId.isNotEmpty()) {
                    userDao.deleteUserByID(userId.toInt())
                }
            }
        }

        binding.btnDeleteAllUser.setOnClickListener {
            lifecycleScope.launch {
                userDao.deleteAllUser()
            }
        }
    }

    private fun listenTableChanges(userDao: UserDao) {
        lifecycleScope.launch {
            userDao.getAllUser().collect {
                Log.i("----->", "Row inserted triggered list: $it")
            }
        }
    }
}
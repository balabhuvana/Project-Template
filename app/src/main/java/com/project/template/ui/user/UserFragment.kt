package com.project.template.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.project.template.R

class UserFragment : Fragment(R.layout.fragment_user) {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sampleButton = view.findViewById<Button>(R.id.btnSampleClick)

        sampleButton.setOnClickListener {
            userViewModel.fetchNameListRequest()
        }
    }
}


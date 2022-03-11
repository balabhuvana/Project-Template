package com.project.template.ui.main.draft

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.project.template.R

class DraftActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draft)

        supportFragmentManager.commit {
            replace<NetworkListeningFragment>(R.id.draftContainerView)
        }
    }
}
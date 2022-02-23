package com.project.template.ui.main


import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.project.template.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchLoginFragment()
    }

    private fun launchLoginFragment() {

        showOrHideProgressBar(View.VISIBLE)
        Handler().postDelayed({
            val navDirections: NavDirections =
                SplashFragmentDirections.actionSplashToLoginFragment()
            val navController: NavController = findNavController()
            navController.navigate(navDirections)
            showOrHideProgressBar(View.GONE)
        }, 2000)

    }

    private fun showOrHideProgressBar(showOrHide: Int) {
//        splashProgressBar.visibility = showOrHide
    }

}

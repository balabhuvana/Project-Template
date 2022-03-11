package com.project.template.ui.main.fragment


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.project.template.databinding.FragmentSplashBinding
import com.project.template.utils.CommonUtils

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchLoginFragment()
    }

    private fun launchLoginFragment() {

        CommonUtils.showOrHideProgressBar(binding.splashProgressBar, View.VISIBLE)
        Handler().postDelayed({
            val navDirections: NavDirections =
                SplashFragmentDirections.actionSplashToLoginFragment()
            val navController: NavController = findNavController()
            navController.navigate(navDirections)
            CommonUtils.showOrHideProgressBar(binding.splashProgressBar, View.GONE)
        }, 2000)

    }

}

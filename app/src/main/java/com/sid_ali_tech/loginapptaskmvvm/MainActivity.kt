package com.sid_ali_tech.loginapptaskmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sid_ali_tech.loginapptaskmvvm.databinding.ActivityMainBinding
import com.sid_ali_tech.loginapptaskmvvm.utils.makeGone
import com.sid_ali_tech.loginapptaskmvvm.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var _navController: NavController? = null
    private val navController get() = _navController!!
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        toolbar = binding.toolbar

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        _navController = navHostFragment.navController

        events()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (navController.currentDestination?.id) {
                    R.id.navLogin,R.id.navDashboard -> {
                        finish()
                    }
                    R.id.navRegister,R.id.navForgetPwd,->{
                        navController.navigateUp()
                    }
                    else -> {
                        navController.navigateUp()
                    }
                }
            }
        })
    }

    fun events(){
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navDashboard, -> {
                    binding.toolbar.makeVisible()
                }
                R.id.navSplash,R.id.navLogin,R.id.navRegister,R.id.navForgetPwd->{
                    binding.toolbar.makeGone()
                }
                else -> {
                    binding.toolbar.makeVisible()
                }
            }
        }


    }

}
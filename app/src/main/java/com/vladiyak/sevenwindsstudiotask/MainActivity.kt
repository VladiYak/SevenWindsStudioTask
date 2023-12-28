package com.vladiyak.sevenwindsstudiotask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.vladiyak.sevenwindsstudiotask.databinding.ActivityMainBinding
import com.vladiyak.sevenwindsstudiotask.utils.Constants
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MapKitFactory.setApiKey(Constants.api_key)
        MapKitFactory.initialize(this)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_main
        ) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

    }
}
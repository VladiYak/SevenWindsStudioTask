package com.vladiyak.sevenwindsstudiotask

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.vladiyak.sevenwindsstudiotask.databinding.ActivityMainBinding
import com.vladiyak.sevenwindsstudiotask.utils.Constants
import com.vladiyak.sevenwindsstudiotask.utils.TokenInstance
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val sharedPrefs by lazy { getSharedPreferences("main", Context.MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val token = sharedPrefs.getString("token", "0")
        TokenInstance.addToken(token ?: "0")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MapKitFactory.setApiKey(Constants.API_KEY)
        MapKitFactory.initialize(this)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_main
        ) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        val prefs = sharedPrefs.getString("token", "0")
        if (prefs == "0") {
            navController.navigate(R.id.signUpFragment)
        } else {
            navController.navigate(R.id.nearbyCoffeeShopsFragment)
        }
    }
}
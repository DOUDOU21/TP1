package com.ndoudou.tp1.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ndoudou.tp1.R
import com.ndoudou.tp1.databinding.ActivityMainBinding
import com.ndoudou.tp1.presentation.ui.fragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.main_fragment)
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

}
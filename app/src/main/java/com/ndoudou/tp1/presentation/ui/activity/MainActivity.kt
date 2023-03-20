package com.ndoudou.tp1.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndoudou.tp1.R
import com.ndoudou.tp1.data.repository.paging.MainLoadStateAdapter
import com.ndoudou.tp1.databinding.ActivityMainBinding
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.presentation.interfaces.OnItemClickListener
import com.ndoudou.tp1.presentation.ui.UserViewModel
import com.ndoudou.tp1.presentation.ui.adapter.UserAdapter
import com.ndoudou.tp1.presentation.ui.fragment.FormFragment
import com.ndoudou.tp1.presentation.ui.fragment.InfosFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val viewModel: UserViewModel by viewModels()
    private val userAdapter= UserAdapter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    fun init(){

        binding.userList.layoutManager = LinearLayoutManager(this)
        binding.userList.adapter = userAdapter.withLoadStateFooter(
            MainLoadStateAdapter()
        )


        lifecycleScope.launch {
            viewModel.data.collectLatest {
                userAdapter.submitData(it)
            }
        }

        binding.newUser.setOnClickListener{
            binding.newUser.hide();
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FormFragment())
                .commit()
        }
    }

    override fun onItemClick(item: User) {
        binding.newUser.hide();
        val infoFragment = InfosFragment()
        val data = Bundle().apply {
            putInt("id", item.id)
        }
        infoFragment.arguments = data
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, infoFragment)
            .commit()
    }

}
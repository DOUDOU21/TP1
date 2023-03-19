package com.ndoudou.tp1.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ndoudou.tp1.R
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.data.repository.paging.MainLoadStateAdapter
import com.ndoudou.tp1.databinding.ActivityMainBinding
import com.ndoudou.tp1.presentation.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private val userAdapter= UserAdapter()
    lateinit var recyclerView : RecyclerView
    lateinit var  newUser: FloatingActionButton

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    fun init(){
        newUser = findViewById(R.id.new_user)
        recyclerView = findViewById<RecyclerView>(R.id.user_list)
        recyclerView.layoutManager = LinearLayoutManager(this)


        binding.userList.adapter = userAdapter.withLoadStateFooter(
            MainLoadStateAdapter()
        )
        //Insertion

        //viewModel.insertUser(UserEntity(1,"ndoudou@gmail.com","Doudou","Nour-eddine","ville","pays","fonction","descriptin","tel","portable","null"))
        //viewModel.insertUser(UserEntity(2,"nsousou@gmail.com","Sousou","Sousou","ville","pays","fonction","descriptin","tel","portable","null"))
        //viewModel.insertUser(UserEntity(3,"nchocho@gmail.com","ChiChi","ChiChi","ville","pays","fonction","descriptin","tel","portable","null"))
        //viewModel.insertUser(UserEntity(4,"nmoumou@gmail.com","MouMou","MiMi","ville","pays","fonction","descriptin","tel","portable","null"))

//        viewModel.insertUser(UserEntity(5,"ntoutou@gmail.com","Toutou","Tawsi","ville","pays","fonction","descriptin","tel","portable","null"))
//        viewModel.insertUser(UserEntity(6,"ncoucou@gmail.com","Coucou","Couskoce","ville","pays","fonction","descriptin","tel","portable","null"))
//        viewModel.insertUser(UserEntity(7,"nboubou@gmail.com","Boubou","Bado","ville","pays","fonction","descriptin","tel","portable","null"))
//        viewModel.insertUser(UserEntity(8,"nloulou@gmail.com","Louou","Loubna","ville","pays","fonction","descriptin","tel","portable","null"))


        //Load Users

        //viewModel.getUsers()
        System.out.println("Result: ")
        //System.out.println("Result: ")

//        lifecycleScope.launch{
//            viewModel.users.collect{
//                userAdapter.submitList(it)
//                recyclerView.adapter = userAdapter
//            }
//        }


        lifecycleScope.launch {
            viewModel.data.collectLatest {
                userAdapter.submitData(it)
            }
        }




        newUser.setOnClickListener{
            newUser.hide();
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FormFragment())
                .commit()
        }
    }




//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, FormFragment())
//                .commit()
//        }



//    override fun passDataCom(user: User) {
//        val bundle = Bundle().apply {
//            putSerializable("user", user)
//        }
//        val transaction = this.supportFragmentManager.beginTransaction()
//        val infoFragment = InfosFragment()
//        infoFragment.arguments = bundle
//        transaction.replace(R.id.container, infoFragment)
//        transaction.commit()
//    }

}
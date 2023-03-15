package com.ndoudou.tp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ndoudou.tp1.model.User

class MainActivity : AppCompatActivity() , Communicator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FormulaireFragment())
                .commit()
        }
    }

    override fun passDataCom(user: User) {
        val bundle = Bundle().apply {
            putSerializable("user", user)
        }
        val transaction = this.supportFragmentManager.beginTransaction()
        val infoFragment = InfosFragment()
        infoFragment.arguments = bundle
        transaction.replace(R.id.container, infoFragment)
        transaction.commit()
    }
}
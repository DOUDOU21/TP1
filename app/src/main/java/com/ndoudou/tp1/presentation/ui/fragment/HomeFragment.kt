package com.ndoudou.tp1.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndoudou.tp1.R
import com.ndoudou.tp1.data.repository.paging.MainLoadStateAdapter
import com.ndoudou.tp1.databinding.FragmentHomeBinding
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.presentation.interfaces.OnItemClickListener
import com.ndoudou.tp1.presentation.ui.UserViewModel
import com.ndoudou.tp1.presentation.ui.adapter.UserAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



class HomeFragment : Fragment(), OnItemClickListener {
    private val viewModel: UserViewModel by activityViewModels()
    private val userAdapter= UserAdapter(this)
    private var _binding: FragmentHomeBinding? = null;
    private val binding get() = _binding!!;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false);
        val view = binding.root;
        init(view)
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun init(view: View) {

        binding.userList.layoutManager = LinearLayoutManager(view.context)
        binding.userList.adapter = userAdapter.withLoadStateFooter(
            MainLoadStateAdapter()
        )

        lifecycleScope.launch {
            viewModel.getUsers()
            viewModel.userState.collectLatest {
                userAdapter.submitData(it)
            }
        }

        binding.newUser.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formFragment)
        }

    }

    override fun onItemClick(item: User) {
        viewModel.getUserById(item.id)
        //val action = HomeFragmentDirections.actionHomeFragmentToInfosFragment()
        findNavController().navigate(R.id.action_homeFragment_to_infosFragment)
    }
}
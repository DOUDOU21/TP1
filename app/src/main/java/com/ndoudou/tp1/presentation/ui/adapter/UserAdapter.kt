package com.ndoudou.tp1.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.databinding.ItemContactBinding
import com.ndoudou.tp1.presentation.interfaces.OnItemClickListener

class UserAdapter(private val listener: OnItemClickListener) : PagingDataAdapter<User, UserAdapter.MainViewHolder>(UserDiffCallback()) {
    inner class MainViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = getItem(position)

        holder.binding.apply {
            textViewName.text = user?.lastName+" "+user?.firstName
            textViewEmail.text = user?.email
            textViewPhone.text = user?.phone
            Glide.with(holder.itemView.context).load(user?.avatar).into(imageContact)
        }

        holder.itemView.setOnClickListener {
            if (user != null) {
                listener.onItemClick(user)
            }
        }


    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem == newItem
        }
    }
}
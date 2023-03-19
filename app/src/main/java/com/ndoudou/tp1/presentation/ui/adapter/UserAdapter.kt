package com.ndoudou.tp1.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
            textViewName.text = user?.nom+" "+user?.prenom
            textViewEmail.text = user?.email
            textViewPhone.text = user?.tel
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



//class UserAdapter() : ListAdapter<User, UserAdapter.ViewHolder>(UserDiffCallback()) {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val user = getItem(position)
//        holder.text_view_name.text = user?.nom+" "+user?.prenom
//        holder.text_view_phone.text = user?.tel
//        holder.text_view_email.text = user?.email
//
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val container_item = itemView.findViewById<RelativeLayout>(R.id.container_item)
//        val image_contact = itemView.findViewById<CircleImageView>(R.id.image_contact)
//        val text_view_name = itemView.findViewById<TextView>(R.id.text_view_name)
//        val text_view_phone = itemView.findViewById<TextView>(R.id.text_view_phone)
//        val text_view_email = itemView.findViewById<TextView>(R.id.text_view_email)
//    }
}
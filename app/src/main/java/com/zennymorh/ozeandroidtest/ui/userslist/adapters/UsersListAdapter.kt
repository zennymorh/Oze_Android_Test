package com.zennymorh.ozeandroidtest.ui.userslist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zennymorh.ozeandroidtest.R
import com.zennymorh.ozeandroidtest.data.model.UsersDB

typealias UserClickListener = (UsersDB.UserDb) -> Unit

class UsersListAdapter (diffCallback: DataListDiffCallback, var listener: UserClickListener) :
    PagingDataAdapter<UsersDB.UserDb, UsersListAdapter.UsersListViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UsersListViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    inner class UsersListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.user_item, parent, false)
        ), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }
        private val name: TextView = itemView.findViewById(R.id.user_name_text)
        private val userPfp: ImageView = itemView.findViewById(R.id.user_pfp)

        fun bind(user: UsersDB.UserDb) {
            name.text = user.login
            if (user.avatarUrl != null) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(userPfp)
            } else {
                userPfp.setImageResource(R.drawable.default_user_ic)
            }

        }

        override fun onClick(p0: View?) {
            val user = getItem(adapterPosition)
            user.let {
                if (it != null) {
                    listener.invoke(it)
                }
            }
        }
    }
}

object DataListDiffCallback : DiffUtil.ItemCallback<UsersDB.UserDb>() {
    override fun areItemsTheSame(oldItem: UsersDB.UserDb, newItem: UsersDB.UserDb): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UsersDB.UserDb, newItem: UsersDB.UserDb): Boolean {
        return oldItem.login == newItem.login
    }
}
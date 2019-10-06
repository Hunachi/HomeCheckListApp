package io.github.hunachi.homechecklistapp.ui.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.data.CheckItem
import io.github.hunachi.homechecklistapp.ui.data.User


class UsersListAdapter : ListAdapter<User, UsersListAdapter.ViewHolder>(DIFFUtil) {

    // 選ばれているItemを通知するため。
    private val modifiableChooseItem: MutableLiveData<CheckItem> =
        MutableLiveData()
    val chooseItem: LiveData<CheckItem> = modifiableChooseItem

    // Viewを作って、ViewHolderのインスタンスを返している。
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder =
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_user, parent, false
        ).let { ViewHolder(it) }

    // Item1つ1つのViewを紐づける。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            view.findViewById<TextView>(R.id.text_name).apply {
                text = user.name
            }
        }
    }

    companion object {
        private val DIFFUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User, newItem: User
            ) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ) = oldItem == newItem
        }
    }
}
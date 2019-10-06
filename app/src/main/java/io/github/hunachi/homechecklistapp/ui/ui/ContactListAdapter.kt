package io.github.hunachi.homechecklistapp.ui.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.data.ContactItem


class ContactListAdapter : ListAdapter<ContactItem, ContactListAdapter.ViewHolder>(DIFFUtil) {

    // Viewを作って、ViewHolderのインスタンスを返している。
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder =
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_contact, parent, false
        ).let { ViewHolder(it) }

    // Item1つ1つのViewを紐づける。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(contactItem: ContactItem) {
            view.findViewById<TextView>(R.id.message).apply {
                text = contactItem.contactData.memo + "\n" + contactItem.checkItemName
            }
            view.findViewById<TextView>(R.id.user_name).apply {
                text = contactItem.contactData.userName
            }
        }
    }

    companion object {
        private val DIFFUtil = object : DiffUtil.ItemCallback<ContactItem>() {
            override fun areItemsTheSame(
                oldItem: ContactItem, newItem: ContactItem
            ) =
                oldItem.contactData.id == newItem.contactData.id

            override fun areContentsTheSame(
                oldItem: ContactItem,
                newItem: ContactItem
            ) = oldItem == newItem
        }
    }
}
package io.github.hunachi.homechecklistapp.ui.ui.checklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.data.CheckItem
import io.github.hunachi.homechecklistapp.ui.data.CheckListItem


class CheckListAdapter(val needCheckBox: Boolean) :
    ListAdapter<
            CheckListItem,
            CheckListAdapter.ViewHolder
            >(DIFFUtil) {

    // 選ばれているItemを通知するため。
    private val modifiableChooseItem: MutableLiveData<CheckItem> =
        MutableLiveData()
    val chooseItem: LiveData<CheckItem> = modifiableChooseItem

    // Viewを作って、ViewHolderのインスタンスを返している。
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder =
        if (needCheckBox) {
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_contact_task, parent, false
            ).let { CheckListViewHolder(it) }
        } else {
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_check, parent, false
            ).let { CheckListNoCheckBoxHolder(it) }
        }

    // Item1つ1つのViewを紐づける。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // 分類するため、基底クラスは同じにしたいから。
    abstract inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(checkItem: CheckListItem)
    }

    inner class CheckListViewHolder(val view: View) : ViewHolder(view) {
        override fun bind(checkItem: CheckListItem) {
            view.findViewById<RadioButton>(R.id.btn_radio).apply {
                text = checkItem.checkItem.content
                isChecked = checkItem.isChecked
                // Buttonが押されたら選ばれているItemを更新。
                setOnClickListener {
                    modifiableChooseItem.value = checkItem.checkItem
                }
            }
        }
    }

    inner class CheckListNoCheckBoxHolder(val view: View) : ViewHolder(view) {
        override fun bind(checkItem: CheckListItem) {
            view.findViewById<TextView>(R.id.text_name).apply {
                text = checkItem.checkItem.content
            }
        }
    }

    companion object {
        private val DIFFUtil = object : DiffUtil.ItemCallback<CheckListItem>() {
            override fun areItemsTheSame(
                oldItem: CheckListItem, newItem: CheckListItem
            ) =
                oldItem.checkItem.id == newItem.checkItem.id

            override fun areContentsTheSame(
                oldItem: CheckListItem,
                newItem: CheckListItem
            ) = oldItem == newItem
        }
    }
}
package io.github.hunachi.homechecklistapp.ui.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.data.ContactData
import io.github.hunachi.homechecklistapp.ui.data.ContactItem
import io.github.hunachi.homechecklistapp.ui.nonNullObserver
import io.github.hunachi.homechecklistapp.ui.toast
import io.github.hunachi.homechecklistapp.ui.ui.contact.ContactListAdapter

/*
*  昨日登録されたContactデータを表示するFragment。
* */
class ContactListFragment : Fragment(R.layout.fragment_contact_list) {

    private val adapter = ContactListAdapter()

    private lateinit var contactViewModel: ContactViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_contact_list, container, false).apply {
        findViewById<RecyclerView>(R.id.list_contact).let { list ->
            list.adapter = adapter
            list.layoutManager = LinearLayoutManager(context)
        }

        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.layout_swipe).also {
            it.setOnRefreshListener {
                contactViewModel.refreshContactList()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        contactViewModel = ViewModelProviders
            .of(activity!!).get(ContactViewModel::class.java)
        setupViewModel()
        contactViewModel.refreshContactList()
    }

    private fun setupViewModel() {
        adapter.submitList(listOf())
        contactViewModel.contactList.nonNullObserver(this) {
            adapter.submitList(it)
        }

        contactViewModel.error.nonNullObserver(this) {
            activity?.toast("エラーです！")
        }

        contactViewModel.spinner.nonNullObserver(this){
            swipeRefreshLayout.isRefreshing = it
        }
    }

    // 確認用に使ってね！
    private val dammyData = listOf(
        ContactItem(ContactData("a", "", 0, "Hunachi", "いつも通り学校があるのでいります。"), "お弁当"),
        ContactItem(ContactData("", "", 0, "お父さん", "会社のお弁当があるのでいりません。"), "お弁当")
    )
}

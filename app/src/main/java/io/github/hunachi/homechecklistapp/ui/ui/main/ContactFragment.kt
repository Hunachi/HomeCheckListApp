package io.github.hunachi.homechecklistapp.ui.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.data.ContactData
import io.github.hunachi.homechecklistapp.ui.data.ContactItem
import io.github.hunachi.homechecklistapp.ui.ui.ContactListAdapter

class ContactListFragment : Fragment(R.layout.fragment_contact_list) {

    private val adapter = ContactListAdapter()

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(dammyData)
    }

    private val dammyData = listOf(
        ContactItem(ContactData("", "", 0, "Hunachi", "いつも通り学校があるのでいります。"), "お弁当"),
        ContactItem(ContactData("", "", 0, "お父さん", "会社のお弁当があるのでいりません。"), "お弁当")
    )
}

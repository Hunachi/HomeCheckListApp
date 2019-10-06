package io.github.hunachi.homechecklistapp.ui.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.MyPreference
import io.github.hunachi.homechecklistapp.ui.data.CheckItem
import io.github.hunachi.homechecklistapp.ui.data.CheckListItem
import io.github.hunachi.homechecklistapp.ui.data.User
import io.github.hunachi.homechecklistapp.ui.ui.UsersListAdapter
import io.github.hunachi.homechecklistapp.ui.ui.checklist.CheckListAdapter
import org.koin.android.ext.android.inject

/**
 * Home screen fragment
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val preference: MyPreference by inject()

    private val checkListAdapter = CheckListAdapter(false)
    private val userListAdapter = UsersListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false).apply {
        findViewById<RecyclerView>(R.id.list_check).let { list ->
            list.adapter = checkListAdapter
            list.layoutManager = LinearLayoutManager(context)
        }
        findViewById<RecyclerView>(R.id.list_members).let {list ->
            list.adapter = userListAdapter
            list.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(preference.id().isNullOrEmpty()){
            findNavController().navigate(R.id.action_homeFragment_to_loginActivity)
        }

        // データを取ってくる処理を書きましょう。
        checkListAdapter.submitList(listOf(
            CheckListItem(CheckItem("", "お弁当")),CheckListItem(CheckItem("", "起きる時間"))))

        userListAdapter.submitList(listOf(User("", "Hunachi"), User("", "お母さん"), User("", "お父さん")))
    }
}

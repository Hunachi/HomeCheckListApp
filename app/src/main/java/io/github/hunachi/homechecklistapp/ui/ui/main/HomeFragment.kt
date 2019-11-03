package io.github.hunachi.homechecklistapp.ui.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.MyPreference
import io.github.hunachi.homechecklistapp.ui.nonNullObserver
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

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.layout_swipe).also {
            it.setOnRefreshListener {
                homeViewModel.updateChecklist()
                homeViewModel.updateUsersList()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeViewModel = ViewModelProviders
            .of(activity!!)[HomeViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(preference.id().isNullOrEmpty()){
            findNavController().navigate(R.id.action_homeFragment_to_loginActivity)
        }

        homeViewModel.checkList.nonNullObserver(this){
            checkListAdapter.submitList(it)
        }

        homeViewModel.userList.nonNullObserver(this){
            userListAdapter.submitList(it)
        }

        homeViewModel.spinner.nonNullObserver(this){
            swipeRefreshLayout.isRefreshing = it
        }

        homeViewModel.updateChecklist()
        homeViewModel.updateUsersList()
    }
}

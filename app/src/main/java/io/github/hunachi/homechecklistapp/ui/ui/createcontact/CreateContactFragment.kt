package io.github.hunachi.homechecklistapp.ui.ui.createcontact

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.MyPreference
import io.github.hunachi.homechecklistapp.ui.data.User
import io.github.hunachi.homechecklistapp.ui.nonNullObserver
import io.github.hunachi.homechecklistapp.ui.toast
import io.github.hunachi.homechecklistapp.ui.ui.checklist.CheckListAdapter

class CreateContactFragment : Fragment() {

    private lateinit var viewModel: CreateContactViewModel

    private val adapter = CheckListAdapter(true)

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_create_contact,
            container,
            false
        ).apply {
            val memo = findViewById<EditText>(R.id.memo)
            findViewById<RecyclerView>(R.id.list_check).let { list ->
                list.adapter = adapter
                list.layoutManager = LinearLayoutManager(context)
            }
            findViewById<Button>(R.id.btn_register).let {
                if (memo.text.isNullOrEmpty()) activity?.toast("入力してください。")
                else viewModel.sendContact(
                    user, memo.text.toString()
                )
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders
            .of(activity!!)[CreateContactViewModel::class.java]

        val preference = MyPreference(activity!!)
        user = preference.user()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.chooseItem.nonNullObserver(this) {
            viewModel.changeChooseItem(it)
        }
        viewModel.checkList.nonNullObserver(this) { list ->
            adapter.submitList(list)
        }
        viewModel.error.nonNullObserver(this) {
            activity?.toast("error")
        }
        viewModel.successSend.nonNullObserver(this) {
            activity?.toast("成功しました。")
            activity?.finish()
        }
        viewModel.refreshList()
    }
}
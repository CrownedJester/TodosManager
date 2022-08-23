package com.crownedjester.soft.todosmanager.presentation.fragment.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crownedjester.soft.todosmanager.R
import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.databinding.FragmentDashboardBinding
import com.crownedjester.soft.todosmanager.presenter.dashboard.DashboardContract
import com.crownedjester.soft.todosmanager.presenter.dashboard.DashboardPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(R.layout.fragment_dashboard), DashboardContract.View,
    DashboardAdapterCallback {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: DashboardContract.Presenter

    private val adapter = DashboardAdapter(this)

    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)

        binding.recyclerViewTodos.adapter = adapter
        binding.recyclerViewTodos.layoutManager = LinearLayoutManager(view.context)

        setPresenter(DashboardPresenter(this))

//        presenter.onViewCreated()

        adapter.differ.submitList(setupFakeData())

        binding.fabAddTodo.setOnClickListener {
            presenter.onNavigate()
        }

    }

    override fun displayTodosEntries(todosFlow: Flow<List<TodoEntry>>) {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                todosFlow.collectLatest {
                    /*todo display data*/
                }
            }
        }
    }

    override fun navigateToAddTodo() {
        navController.navigate(R.id.action_dashboardFragment_to_addTodoFragment)
    }

    override fun setPresenter(presenter: DashboardContract.Presenter) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        _binding = null
    }

    override fun onItemLongClicked(entry: TodoEntry) {
        presenter.onItemLongClicked(lifecycleScope, entry)
    }

    private fun setupFakeData() =
        listOf(
            TodoEntry(1, "abc", "cba", "", ""),
            TodoEntry(
                2,
                "abc",
                "cbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "",
                ""
            ),
            TodoEntry(
                3,
                "abc",
                "cbabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
                "",
                ""
            ),
            TodoEntry(4, "abc", "cba", "", ""),
            TodoEntry(5, "abc", "cba", "", ""),
            TodoEntry(6, "abc", "cba", "", ""),
            TodoEntry(7, "abc", "cba", "", ""),
            TodoEntry(8, "abc", "cba", "", ""),
            TodoEntry(9, "abc", "cba", "", ""),
        )

}
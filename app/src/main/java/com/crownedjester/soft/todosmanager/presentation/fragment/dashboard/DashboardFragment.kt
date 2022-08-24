package com.crownedjester.soft.todosmanager.presentation.fragment.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crownedjester.soft.todosmanager.R
import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.databinding.FragmentDashboardBinding
import com.crownedjester.soft.todosmanager.presentation.util.BundleUtil
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

        setPresenter(DashboardPresenter(this))

        binding.apply {
            recyclerViewTodos.adapter = adapter
            recyclerViewTodos.layoutManager = LinearLayoutManager(view.context)

            binding.fabAddTodo.setOnClickListener {
                presenter.onNavigate()
            }

        }

        presenter.onViewCreated()

    }

    override fun displayTodosEntries(todosFlow: Flow<List<TodoEntry>>) {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                todosFlow.collectLatest {
                    adapter.differ.submitList(it)
                }
            }
        }
    }

    override fun navigateToCreateTodo() {
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

    override fun onItemClick(entry: TodoEntry) {
        setFragmentResult(BundleUtil.SEND_TODO_REQUEST_KEY, bundleOf(BundleUtil.TODO_KEY to entry))
        navController.navigate(R.id.action_dashboardFragment_to_addTodoFragment)
    }

}
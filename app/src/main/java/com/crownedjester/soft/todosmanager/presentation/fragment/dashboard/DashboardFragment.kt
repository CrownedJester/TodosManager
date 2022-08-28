package com.crownedjester.soft.todosmanager.presentation.fragment.dashboard

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crownedjester.soft.todosmanager.R
import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.databinding.FragmentDashboardBinding
import com.crownedjester.soft.todosmanager.presentation.util.BundleUtil
import com.crownedjester.soft.todosmanager.presentation.util.DensityUtil
import com.crownedjester.soft.todosmanager.presenter.dashboard.DashboardContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.lang.ref.WeakReference

class DashboardFragment : Fragment(R.layout.fragment_dashboard), DashboardContract.View,
    DashboardAdapterCallback {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val presenter: DashboardContract.Presenter by inject { parametersOf(this) }

    private val adapter = DashboardAdapter(this)

    private val navController by lazy { findNavController() }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)

        binding.apply {
            recyclerViewTodos.adapter = adapter
            recyclerViewTodos.layoutManager = LinearLayoutManager(view.context)

            itemTouchHelper.attachToRecyclerView(binding.recyclerViewTodos)

            binding.fabAddTodo.setOnClickListener {
                presenter.onFabClick()
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

    override fun navigateToEditTodo(entry: TodoEntry) {
        setFragmentResult(
            BundleUtil.SEND_TODO_REQUEST_KEY, bundleOf(BundleUtil.TODO_KEY to entry)
        )
        navController.navigate(R.id.action_dashboardFragment_to_addTodoFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(entry: TodoEntry) {
        presenter.onItemClick(entry)
    }

    private val itemTouchHelper by lazy {

        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), START_COLOR, TARGET_COLOR)

        val callback = object : SimpleCallback(0, LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                presenter.onItemSwiped(
                    lifecycleScope,
                    adapter.differ.currentList[viewHolder.adapterPosition]
                )
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)


                colorAnimation.addUpdateListener {
                    viewHolder?.itemView?.backgroundTintList =
                        ColorStateList.valueOf(it.animatedValue as Int)
                }

                if (actionState == ACTION_STATE_SWIPE) {
                    viewHolder?.itemView?.elevation =
                        DensityUtil.convertDpToPx(WeakReference(requireContext()), 8f)
                    colorAnimation.duration = START_DURATION
                    colorAnimation.start()
                }

                if (actionState == ACTION_STATE_IDLE) {
                    lifecycleScope.launch {
                        delay(REVERSE_DURATION)
                        colorAnimation.removeAllUpdateListeners()
                    }
                    colorAnimation.duration = REVERSE_DURATION
                    colorAnimation.reverse()


                }
            }

        }

        ItemTouchHelper(callback)
    }

}

private const val START_DURATION = 500L
private const val REVERSE_DURATION = 250L
private const val START_COLOR = Color.WHITE
private const val TARGET_COLOR = Color.RED
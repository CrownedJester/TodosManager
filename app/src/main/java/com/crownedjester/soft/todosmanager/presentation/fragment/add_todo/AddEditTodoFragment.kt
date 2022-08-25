package com.crownedjester.soft.todosmanager.presentation.fragment.add_todo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.crownedjester.soft.todosmanager.R
import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.databinding.FragmentAddEditTodoBinding
import com.crownedjester.soft.todosmanager.presentation.util.BundleUtil
import com.crownedjester.soft.todosmanager.presenter.add_todo.AddEditTodoContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class AddEditTodoFragment : Fragment(R.layout.fragment_add_edit_todo), AddEditTodoContract.View {

    private var _binding: FragmentAddEditTodoBinding? = null
    private val binding get() = _binding!!

    private val presenter: AddEditTodoContract.Presenter by inject { parametersOf(this) }

    private var editingTodoId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddEditTodoBinding.bind(view)

        presenter.onViewCreated()

        binding.fabSaveTodo.setOnClickListener {
            presenter.onSaveTodo(lifecycleScope)
            findNavController().navigate(R.id.action_addTodoFragment_to_dashboardFragment)
        }

    }

    override fun postTodo(): TodoEntry {
        val title = binding.editTextTodoTitle.text.toString()
        val goal = binding.editTextTodoGoal.text.toString()

        val date = Calendar.getInstance().time

        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val publishDate = sdf.format(date)

        return if (editingTodoId != null)
            TodoEntry(
                id = editingTodoId!!,
                title = title,
                goal = goal,
                publishDate = publishDate,
            ) else
            TodoEntry(title = title, goal = goal, publishDate = publishDate)

    }

    override fun findNavArgs() {
        setFragmentResultListener(BundleUtil.SEND_TODO_REQUEST_KEY) { _, bundle ->

            val (id, title, goal, _) = bundle.getParcelable<TodoEntry>(BundleUtil.TODO_KEY)!!

            binding.apply {
                editTextTodoGoal.setText(goal)
                editTextTodoTitle.setText(title)
                editingTodoId = id
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        _binding = null
    }
}
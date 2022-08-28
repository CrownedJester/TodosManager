package com.crownedjester.soft.todosmanager.presenter.add_todo

import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import kotlinx.coroutines.CoroutineScope

interface AddEditTodoContract {

    interface Presenter {

        fun onViewCreated()

        fun onSaveTodo(scope: CoroutineScope)

    }

    interface View {

        fun postTodo(): TodoEntry

        fun findNavArgs()

    }
}
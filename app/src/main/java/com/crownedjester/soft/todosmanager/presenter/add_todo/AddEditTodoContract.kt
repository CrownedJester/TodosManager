package com.crownedjester.soft.todosmanager.presenter.add_todo

import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.presenter.base.BasePresenter
import kotlinx.coroutines.CoroutineScope

interface AddEditTodoContract {

    interface Presenter : BasePresenter {

        fun onViewCreated()

        fun onSaveTodo(scope: CoroutineScope)

    }

    interface View {

        fun postTodo(): TodoEntry

        fun findNavArgs()

    }
}
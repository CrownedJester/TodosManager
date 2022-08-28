package com.crownedjester.soft.todosmanager.presenter.dashboard

import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface DashboardContract {
    interface Presenter {

        fun onViewCreated()

        fun onFabClick()

        fun onItemSwiped(scope: CoroutineScope, entry: TodoEntry)

        fun onItemClick(entry: TodoEntry)

    }

    interface View {

        fun displayTodosEntries(todosFlow: Flow<List<TodoEntry>>)

        fun navigateToCreateTodo()

        fun navigateToEditTodo(entry: TodoEntry)

    }
}
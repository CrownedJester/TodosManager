package com.crownedjester.soft.todosmanager.presenter.dashboard

import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.presenter.base.BasePresenter
import com.crownedjester.soft.todosmanager.presenter.base.BaseView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface DashboardContract {
    interface Presenter : BasePresenter {

        fun onViewCreated()

        fun onFabClick()

        fun onItemLongClicked(scope: CoroutineScope, entry: TodoEntry)

        fun onItemClick(entry: TodoEntry)

    }

    interface View : BaseView<Presenter> {

        fun displayTodosEntries(todosFlow: Flow<List<TodoEntry>>)

        fun navigateToCreateTodo()

        fun navigateToEditTodo(entry: TodoEntry)

    }
}
package com.crownedjester.soft.todosmanager.presenter.dashboard

import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.domain.use_cases.delete_todo.DeleteTodo
import com.crownedjester.soft.todosmanager.domain.use_cases.get_todos.GetTodos
import kotlinx.coroutines.CoroutineScope
import org.koin.java.KoinJavaComponent.inject

class DashboardPresenter(
    view: DashboardContract.View
) : DashboardContract.Presenter {

    private val deleteTodo by inject<DeleteTodo>(DeleteTodo::class.java)
    private val getTodos by inject<GetTodos>(GetTodos::class.java)
    private var view: DashboardContract.View? = view

    override fun onViewCreated() {
        view?.displayTodosEntries(getTodos())
    }

    override fun onNavigate() {
        view?.navigateToCreateTodo()
    }

    override fun onItemLongClicked(scope: CoroutineScope, entry: TodoEntry) {
        deleteTodo(scope, entry)
    }

    override fun onDestroy() {
        this.view = null
    }

}
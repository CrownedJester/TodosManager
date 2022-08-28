package com.crownedjester.soft.todosmanager.presenter.add_todo

import com.crownedjester.soft.todosmanager.domain.use_cases.post_todo.PostTodo
import kotlinx.coroutines.CoroutineScope
import org.koin.java.KoinJavaComponent.inject

class AddEditTodoPresenter(private val view: AddEditTodoContract.View) :
    AddEditTodoContract.Presenter {

    private val postTodo by inject<PostTodo>(PostTodo::class.java)

    override fun onViewCreated() {
        view.findNavArgs()
    }

    override fun onSaveTodo(scope: CoroutineScope) {
        postTodo(scope, view.postTodo())
    }

}
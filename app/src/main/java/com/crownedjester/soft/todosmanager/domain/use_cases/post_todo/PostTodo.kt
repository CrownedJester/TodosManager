package com.crownedjester.soft.todosmanager.domain.use_cases.post_todo

import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.domain.repository.TodosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PostTodo(private val repository: TodosRepository) {

    operator fun invoke(scope: CoroutineScope, entry: TodoEntry) {
        scope.launch {
            repository.postTodo(entry)
        }
    }

}
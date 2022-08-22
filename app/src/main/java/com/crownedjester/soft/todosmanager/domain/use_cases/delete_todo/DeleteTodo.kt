package com.crownedjester.soft.todosmanager.domain.use_cases.delete_todo

import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.domain.repository.TodosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteTodo(private val repository: TodosRepository) {

    operator fun invoke(scope: CoroutineScope, entry: TodoEntry) {
        scope.launch {
            repository.deleteTodo(entry)
        }
    }

}
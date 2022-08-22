package com.crownedjester.soft.todosmanager.domain.use_cases.get_todos

import com.crownedjester.soft.todosmanager.domain.repository.TodosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTodos(private val repository: TodosRepository) {

    suspend operator fun invoke(scope: CoroutineScope) =
        withContext(scope.coroutineContext + Dispatchers.IO) {

            repository.getTodos()
        }

}
package com.crownedjester.soft.todosmanager.domain.use_cases.get_todos

import com.crownedjester.soft.todosmanager.domain.repository.TodosRepository

class GetTodos(private val repository: TodosRepository) {

    operator fun invoke() =
            repository.getTodos()


}
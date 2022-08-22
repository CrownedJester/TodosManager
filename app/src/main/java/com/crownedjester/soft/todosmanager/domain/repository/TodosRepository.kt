package com.crownedjester.soft.todosmanager.domain.repository

import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import kotlinx.coroutines.flow.Flow

interface TodosRepository {

    fun getTodos(): Flow<List<TodoEntry>>

    suspend fun postTodo(entry: TodoEntry)

    suspend fun deleteTodo(entry: TodoEntry)

}
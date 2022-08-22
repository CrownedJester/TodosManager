package com.crownedjester.soft.todosmanager.domain.repository

import com.crownedjester.soft.todosmanager.data.data_source.TodoDao
import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import kotlinx.coroutines.flow.Flow

class TodosManager(private val todosDao: TodoDao) : TodosRepository {

    override fun getTodos(): Flow<List<TodoEntry>> =
        todosDao.getTodos()

    override suspend fun postTodo(entry: TodoEntry) =
        todosDao.postTodo(entry)

    override suspend fun deleteTodo(entry: TodoEntry) =
        todosDao.deleteTodo(entry)

}
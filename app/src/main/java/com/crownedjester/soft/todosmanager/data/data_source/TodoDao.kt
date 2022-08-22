package com.crownedjester.soft.todosmanager.data.data_source

import androidx.room.*
import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("select * from todo_table")
    fun getTodos(): Flow<List<TodoEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postTodo(entry: TodoEntry)

    @Delete
    suspend fun deleteTodo(entry: TodoEntry)
}
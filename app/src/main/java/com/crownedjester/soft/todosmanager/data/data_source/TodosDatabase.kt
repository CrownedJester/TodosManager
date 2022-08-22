package com.crownedjester.soft.todosmanager.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crownedjester.soft.todosmanager.data.model.TodoEntry

@Database(
    version = 1,
    entities = [TodoEntry::class]
)
abstract class TodosDatabase : RoomDatabase() {

    abstract val dao: TodoDao

    companion object {
        const val DATABASE_NAME = "todos_database"
    }
}
package com.crownedjester.soft.todosmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val goal: String,
    val publishDate: String,
    val publishTime: String
)
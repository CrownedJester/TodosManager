package com.crownedjester.soft.todosmanager.presentation.fragment.dashboard

import com.crownedjester.soft.todosmanager.data.model.TodoEntry

interface DashboardAdapterCallback {

    fun onItemLongClicked(entry: TodoEntry)

}

package com.crownedjester.soft.todosmanager.di

import androidx.room.Room
import com.crownedjester.soft.todosmanager.data.data_source.TodosDatabase
import com.crownedjester.soft.todosmanager.domain.repository.TodosManager
import com.crownedjester.soft.todosmanager.domain.repository.TodosRepository
import com.crownedjester.soft.todosmanager.domain.use_cases.delete_todo.DeleteTodo
import com.crownedjester.soft.todosmanager.domain.use_cases.get_todos.GetTodos
import com.crownedjester.soft.todosmanager.domain.use_cases.post_todo.PostTodo
import com.crownedjester.soft.todosmanager.presenter.add_todo.AddEditTodoContract
import com.crownedjester.soft.todosmanager.presenter.add_todo.AddEditTodoPresenter
import com.crownedjester.soft.todosmanager.presenter.dashboard.DashboardContract
import com.crownedjester.soft.todosmanager.presenter.dashboard.DashboardPresenter
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val module = module {

    //Todos Repository
    single<TodosRepository> {
        TodosManager(get<TodosDatabase>(TodosDatabase::class).dao)
    }

    //Todos Database
    single {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            TodosDatabase::class.java,
            TodosDatabase.DATABASE_NAME
        ).build()
    }

}

val useCasesModule = module {

    single { PostTodo(get()) }

    single { DeleteTodo(get()) }

    single { GetTodos(get()) }

}

val presenterModule = module {

    factory<DashboardContract.Presenter> { (view: DashboardContract.View) ->
        DashboardPresenter(view)
    }

    factory<AddEditTodoContract.Presenter> { (view: AddEditTodoContract.View) ->
        AddEditTodoPresenter(view)
    }
}
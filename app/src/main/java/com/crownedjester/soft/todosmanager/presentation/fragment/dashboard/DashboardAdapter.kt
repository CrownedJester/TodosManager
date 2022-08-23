package com.crownedjester.soft.todosmanager.presentation.fragment.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.crownedjester.soft.todosmanager.data.model.TodoEntry
import com.crownedjester.soft.todosmanager.databinding.ItemTodoBinding

class DashboardAdapter(private val dashboardAdapterCallback: DashboardAdapterCallback) :
    RecyclerView.Adapter<DashboardAdapter.TodoItemViewHolder>() {

    private val callback = object : ItemCallback<TodoEntry>() {

        override fun areItemsTheSame(oldItem: TodoEntry, newItem: TodoEntry): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TodoEntry, newItem: TodoEntry): Boolean =
            oldItem.id == newItem.id

    }

    val differ = AsyncListDiffer(this, callback)


    inner class TodoItemViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: TodoEntry) {
            binding.apply {

                textViewTodoTitle.text = entry.title

                textViewTodoGoal.text = entry.goal

                textViewTodoDate.text = entry.publishDate

                itemView.setOnLongClickListener {
                    dashboardAdapterCallback.onItemLongClicked(entry)
                    false
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val view = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TodoItemViewHolder(view)
    }

    override fun getItemCount(): Int =
        differ.currentList.size


    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todo = differ.currentList[position]

        holder.bind(todo)
    }

}
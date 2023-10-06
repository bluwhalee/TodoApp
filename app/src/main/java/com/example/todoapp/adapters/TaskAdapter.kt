package com.example.kotlintodopractice.utils.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.interfaces.TaskAdapterInterface
import com.example.todoapp.models.ToDoData
import com.example.todoapp.viewholders.TaskViewHolder

class TaskAdapter(private val list: MutableList<ToDoData>) : RecyclerView.Adapter<TaskViewHolder>() {


    private var listener:TaskAdapterInterface? = null
    fun setListener(listener: TaskAdapterInterface){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.fromParent(parent)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position], position, listener)
    }
    override fun getItemCount(): Int {
        return list.size
    }


}

package com.example.todoapp.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemtodoBinding
import com.example.todoapp.interfaces.TaskAdapterInterface
import com.example.todoapp.models.ToDoData

class TaskViewHolder(val binding: ItemtodoBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(todo : ToDoData, position: Int, listener: TaskAdapterInterface?){
        binding.todoTask.text = todo.task

        binding.editTask.setOnClickListener {
            listener?.onEditItemClicked(todo , position)
        }

        binding.deleteTask.setOnClickListener {
            listener?.onDeleteItemClicked(todo , position)
        }

    }
    companion object{
        fun fromParent(parent: ViewGroup,):TaskViewHolder{
            return TaskViewHolder(ItemtodoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }
}
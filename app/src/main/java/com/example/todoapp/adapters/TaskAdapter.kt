package com.example.kotlintodopractice.utils.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemtodoBinding
import com.example.todoapp.interfaces.TaskAdapterInterface
import com.example.todoapp.models.ToDoData
import com.example.todoapp.viewholders.TAskViewHolder

class TaskAdapter(private val list: MutableList<ToDoData>) : RecyclerView.Adapter<TAskViewHolder>() {

    private  val TAG = "TaskAdapter"
    private var listener:TaskAdapterInterface? = null
    fun setListener(listener: TaskAdapterInterface){
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TAskViewHolder {

        return TAskViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: TAskViewHolder, position: Int) {
        holder.bind(list[position], position, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}

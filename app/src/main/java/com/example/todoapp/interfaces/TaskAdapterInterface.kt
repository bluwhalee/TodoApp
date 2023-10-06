package com.example.todoapp.interfaces

import com.example.todoapp.models.ToDoData

interface TaskAdapterInterface{
    fun onDeleteItemClicked(toDoData: ToDoData , position : Int)
    fun onEditItemClicked(toDoData: ToDoData , position: Int)
}
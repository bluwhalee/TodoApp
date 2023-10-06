package com.example.todoapp.interfaces

import com.example.todoapp.models.ToDoData
import com.google.android.material.textfield.TextInputEditText

interface DialogAddButtton  {
    fun onSaveTask(todo: String, todoEt : TextInputEditText)
    fun onUpdateTask(toDoData: ToDoData, todoEt : TextInputEditText)
}
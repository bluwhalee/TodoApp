package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTodoPopupBinding
import com.example.todoapp.interfaces.DialogAddButtton
import com.example.todoapp.models.ToDoData


class AddTodoPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogAddButtton
    private var toDoData: ToDoData? = null

    companion object{
        const val TAG = "AddTodoPopupFragment"
        @JvmStatic
        fun newInstance(taskId:String, task:String) = AddTodoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }
    fun setListener(listener : DialogAddButtton)
    {
        this.listener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoPopupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null){
            toDoData = ToDoData(arguments?.getString("taskId").toString(), arguments?.getString("task").toString())
            binding.todoEt.setText(toDoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val todo = binding.todoEt.text.toString()
            if(todo.isNotEmpty())
            {
                if(toDoData == null){

                    listener.onSaveTask(todo, binding.todoEt)
                }
                else{
                    toDoData?.task = todo
                    listener.onUpdateTask(toDoData!!, binding.todoEt )
                }
            }
            else
            {
                Toast.makeText(context, "Empty fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener{
            dismiss()
        }
    }


}
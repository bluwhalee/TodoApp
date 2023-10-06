package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlintodopractice.utils.adapter.TaskAdapter
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.databinding.FragmentSignInBinding
import com.example.todoapp.interfaces.DialogAddButtton
import com.example.todoapp.interfaces.TaskAdapterInterface
import com.example.todoapp.models.ToDoData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(), DialogAddButtton, TaskAdapterInterface {

    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseRef: DatabaseReference
    private var popupFragment: AddTodoPopupFragment? = null
    private lateinit var adapter : TaskAdapter
    private lateinit var mList: MutableList<ToDoData >
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())

        registerEvents()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            mList = mutableListOf()
        }
        adapter = TaskAdapter(mList)
        adapter.setListener(this)
        binding.recyclerView.adapter = adapter

        getDataFromFirebase()

    }

    private fun getDataFromFirebase() {
        databaseRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for(taskSnapshot in snapshot.children)
                {
                    val todoTask = taskSnapshot.key?.let {
                        ToDoData(it, taskSnapshot.value.toString())
                    }
                    if(todoTask != null) mList.add(todoTask)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerEvents() {
        binding.btnAdd.setOnClickListener{
            if(popupFragment != null)
                childFragmentManager.beginTransaction().remove(popupFragment!!).commit()
            popupFragment = AddTodoPopupFragment()
            popupFragment!!.setListener(this)
            popupFragment!!.show(
                childFragmentManager,
                AddTodoPopupFragment.TAG
            )
        }
    }

    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
        databaseRef.push().setValue(todo).addOnCompleteListener{
            if(it.isSuccessful)
            {
                Toast.makeText(context, "Todo saved", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            todoEt.text = null
            popupFragment?.dismiss()
        }
    }

    override fun onDeleteItemClicked(toDoData: ToDoData, position: Int) {
        databaseRef.child(toDoData.taskId).removeValue().addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(context, "Todo removed", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditItemClicked(toDoData: ToDoData, position: Int) {
        if(popupFragment != null)
            childFragmentManager.beginTransaction().remove(popupFragment!!).commit()
        popupFragment = AddTodoPopupFragment.newInstance(toDoData.taskId, toDoData.task)
        popupFragment!!.setListener(this)
        popupFragment!!.show(childFragmentManager, AddTodoPopupFragment.TAG)
    }

    override fun onUpdateTask(toDoData: ToDoData, todoEt: TextInputEditText) {
        val map = HashMap<String, Any>()
        map[toDoData.taskId] = toDoData.task
        databaseRef.updateChildren(map).addOnCompleteListener{
            if(it.isSuccessful)
            {
                Toast.makeText(context, "Todo Updated", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            todoEt.text = null
            popupFragment!!.dismiss()
        }
    }
}
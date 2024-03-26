package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db:TasksDatabaseHelper
    private lateinit var tasksAdapter:TasksAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize database helper
        db=TasksDatabaseHelper(this)
        //Initialize the adapter
        tasksAdapter= TasksAdapter(db.getAllTasks(),this)

        //Set layout manager and adapter for the recyclerview
        binding.tasksRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.tasksRecyclerView.adapter=tasksAdapter

        //Set onClickListener for Add button
        binding.addButton.setOnClickListener {
            // Start AddTodoListActivity when the button is clicked
            val intent=Intent(this,AddTodoListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data in the adapter when the activity resumes
        tasksAdapter.refreshData(db.getAllTasks())
    }
}
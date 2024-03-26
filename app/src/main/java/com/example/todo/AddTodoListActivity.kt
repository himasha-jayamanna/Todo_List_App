package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todo.databinding.ActivityAddTodoListBinding
import com.example.todo.databinding.ActivityMainBinding

class AddTodoListActivity : AppCompatActivity() {

    //binding instance for activity layout
    private lateinit var binding: ActivityAddTodoListBinding
    //database helper instance
    private lateinit var db:TasksDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inflate layout using ViewBinding
        binding= ActivityAddTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize database helper
        db= TasksDatabaseHelper(this)

        //set onClickListener for Save button
        binding.saveButton.setOnClickListener {
            //retrieve title and content from EditText fields
            val title=binding.titleEditText.text.toString()
            val content=binding.contentEditText.text.toString()

            //create new task object with retrieved data
            val task=Task(0,title,content)
            //insert task into db
            db.insertTask(task)
            finish()
            //display toast message
            Toast.makeText(this,"Task Saved",Toast.LENGTH_SHORT).show()

        }
    }
}
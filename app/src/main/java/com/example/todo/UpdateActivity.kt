package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todo.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db:TasksDatabaseHelper
    private var taskId:Int=-1    /**task id variable represent value is empty**/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=TasksDatabaseHelper(this)

        //retrieve task ID from intent extras
        taskId=intent.getIntExtra("task_id",-1)
        //if task ID is not valid,finish the activity
        if(taskId==-1){
            finish()
            return
        }

        //retrieve task details from db using task ID
        val task=db.getTaskByID(taskId)
        //set EditText fields with task details
        binding.updateTitleEditText.setText(task.title)
        binding.updateContentEditText.setText(task.content)

        binding.updateSaveButton.setOnClickListener {
            // Retrieve new title and content from EditText fields
            val newTitle=binding.updateTitleEditText.text.toString()
            val newContent=binding.updateContentEditText.text.toString()
            //create new task object with updated data
            val updatedTask=Task(taskId,newTitle,newContent)
            db.updateTask(updatedTask)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()

        }
    }
}
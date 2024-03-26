package com.example.todo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter (private var tasks:List<Task>,context:Context):
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val db:TasksDatabaseHelper=TasksDatabaseHelper(context)

    //viewHolder class for the task item view
    class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleTextView:TextView=itemView.findViewById(R.id.titleTextView)
        val contentTextView:TextView=itemView.findViewById(R.id.contentTextView)
        val updateButton:ImageView=itemView.findViewById(R.id.updateButton)
        val deleteButton:ImageView=itemView.findViewById(R.id.deleteButton)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int=tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task=tasks[position]
        // set title and content for task item view
        holder.titleTextView.text=task.title
        holder.contentTextView.text=task.content

        //set onClickListener for update button
        holder.updateButton.setOnClickListener {
            // Start UpdateActivity and pass the task ID as an extra
            val intent=Intent(holder.itemView.context,UpdateActivity::class.java).apply {
                putExtra("task_id",task.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Set onClickListener for the Delete button
        holder.deleteButton.setOnClickListener {
            // Delete task from database
            db.deleteTask(task.id)
            // Refresh the adapter data to reflect changes
            refreshData(db.getAllTasks())
            // Display a toast message
            Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
        }
    }

    // refresh adapter data with new list of tasks
    fun refreshData(newTasks:List<Task>){
        tasks=newTasks
        notifyDataSetChanged()
    }
}
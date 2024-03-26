package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Helper class for managing tasks database
class TasksDatabaseHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    //create constants
    companion object{
        private const val DATABASE_NAME="todoapp.db"
        private const val DATABASE_VERSION=1
        private const val TABLE_NAME="allTasks"
        private const val COLUMN_ID="id"
        private const val COLUMN_TITLE="title"
        private const val COLUMN_CONTENT="content"






    }
    // call when database created for first time
    override fun onCreate(db: SQLiteDatabase?) {
        //create the table
        val createTableQuery="CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    //call when database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //drop existing table and recreate it
        val dropTableQuery="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    //insert new task into the database
    fun insertTask(task: Task){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,task.title)
            put(COLUMN_CONTENT,task.content)
        }

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    //retrieve all tasks from the database
    fun getAllTasks():List<Task>{
        val tasksList= mutableListOf<Task>()
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(query,null)

        while(cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val task = Task(id, title, content)
            tasksList.add(task)
        }
        cursor.close()
        db.close()
        return tasksList

    }

    //update existing task
    fun updateTask(task: Task){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,task.title)
            put(COLUMN_CONTENT,task.content)
        }
        val whereClause="$COLUMN_ID=?"
        val whereArgs= arrayOf(task.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    //retrieve task by its id
    fun getTaskByID(taskId:Int):Task{
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$taskId"
        val cursor=db.rawQuery(query,null)
        cursor.moveToFirst()

        val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Task(id,title,content)
    }

    //delete task from db by its id
    fun deleteTask(taskId: Int){
        val db=writableDatabase
        val whereClause="$COLUMN_ID =?"
        val whereArgs= arrayOf(taskId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}
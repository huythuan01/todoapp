package com.example.mytodoapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mytodoapp.model.Task

class DbHelper(context: Context)
    : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private val DATABASE_VERSION = 1  // Database Version
        private val DATABASE_NAME = "tasks_db" // Database Name
        // Database Table Name
        private val TABLE_NAME = "tasks"
        // Attributes for Tables
        private val COLUMN_ID = "id"
        private val COLUMN_TITLE = "title"
        private val COLUMN_DESCRIPTION = "description"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = (
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_TITLE + " TEXT,"
                        + COLUMN_DESCRIPTION + " TEXT"
                        + ")")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addTask(task : Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_DESCRIPTION, task.description)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTasks(): List<Task> {
        val tasksList = mutableListOf<Task>()
        val db = readableDatabase
        val query = "SELECT * fROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val task = Task(id, title, description)
            tasksList.add(task)
        }
        cursor.close()
        db.close()
        return tasksList
    }

    fun updateTask(task: Task){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_DESCRIPTION, task.description)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getTaskByID(taskID: Int): Task {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

        cursor.close()
        db.close()
        return Task(id, title, description)
    }

    fun deleteTask(taskID: Int?){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(taskID.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

}
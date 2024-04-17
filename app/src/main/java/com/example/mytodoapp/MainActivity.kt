package com.example.mytodoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodoapp.activities.AddActivity
import com.example.mytodoapp.adapters.TaskAdapter
import com.example.mytodoapp.databinding.ActivityMainBinding
import com.example.mytodoapp.db.DbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DbHelper
    private lateinit var taskAdapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)
        taskAdapter = TaskAdapter(db.getAllTasks(), this)

        binding.recview.layoutManager = LinearLayoutManager(this)
        binding.recview.adapter = taskAdapter

        binding.btnAdd.setOnClickListener {
            val i = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(i)
        }

        binding.btnExit.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        taskAdapter.refreshData(db.getAllTasks())
    }
}
package com.example.mytodoapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.ActivityUpdateBinding
import com.example.mytodoapp.db.DbHelper
import com.example.mytodoapp.model.Task

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: DbHelper
    private var taskID : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        taskID = intent.getIntExtra("task_id", -1)
        if(taskID == -1){
            finish()
            return
        }

        val task = db.getTaskByID(taskID)
        binding.txtUpdateTitle.setText(task.title)
        binding.txtUpdateDecs.setText(task.description)

        binding.btnUpdateSave.setOnClickListener {
            val newTitle = binding.txtUpdateTitle.text.toString()
            val newDesc = binding.txtUpdateDecs.text.toString()
            val updateTask = Task(taskID, newTitle, newDesc)
            if (!newTitle.equals("")) {
                db.updateTask(updateTask)
                finish()
                Toast.makeText(this, "Đã lưu thay đổi", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Nhập nội dung", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
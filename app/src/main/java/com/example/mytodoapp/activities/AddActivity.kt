package com.example.mytodoapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mytodoapp.databinding.ActivityAddBinding
import com.example.mytodoapp.db.DbHelper
import com.example.mytodoapp.model.Task

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var db: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        binding.btnSave.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val description = binding.txtDecs.text.toString()
            val task = Task(0, title, description)

            if (!title.equals("")) {
                db.addTask(task)
                finish()
                Toast.makeText(this, "Đã lưu thành công", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Nhập nội dung", Toast.LENGTH_SHORT).show()
            }

        }

    }
}
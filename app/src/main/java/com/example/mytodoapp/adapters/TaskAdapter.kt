package com.example.mytodoapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.R
import com.example.mytodoapp.model.Task
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import com.example.mytodoapp.activities.UpdateActivity
import com.example.mytodoapp.db.DbHelper

class TaskAdapter(private var tasks: List<Task>, context: Context) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val db: DbHelper = DbHelper(context)
    private lateinit var mcontext : Context
    class  TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvDesc : TextView = itemView.findViewById(R.id.tvDesc)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        mcontext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvTitle.text = task.title
        holder.tvDesc.text = task.description

        holder.btnEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("task_id", task.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener {
            var dialog = AlertDialog.Builder(mcontext)
            dialog.setTitle("Cảnh báo")
            dialog.setMessage("Bạn có chắc chắn muốn xóa không ?")
            dialog.setPositiveButton("Có") { _: DialogInterface, _: Int ->
                db.deleteTask(task.id)
                refreshData(db.getAllTasks())
                Toast.makeText(holder.itemView.context, "Đã xóa", Toast.LENGTH_SHORT).show()
            }
            dialog.setNegativeButton("Không") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }

    }

    fun refreshData(newTasks: List<Task>){
        tasks = newTasks
        notifyDataSetChanged()
    }

}
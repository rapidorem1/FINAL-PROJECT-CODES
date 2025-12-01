FINISHEDTASKADAPTER.KT
package com.example.pawminder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FinishedTaskAdapter(
    private val items: List<Task>
) : RecyclerView.Adapter<FinishedTaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTaskTitle)
        val tvDateTime: TextView = view.findViewById(R.id.tvTaskDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_finished_tasks, parent, false)
        return TaskViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val t = items[position]
        holder.tvTitle.text = "${t.petName} – ${t.taskType}"
        holder.tvDateTime.text = t.dateTimeText
    }
}

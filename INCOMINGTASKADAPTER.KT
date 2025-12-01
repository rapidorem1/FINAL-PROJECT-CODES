INCOMINGTASKADAPTER.KT
package com.example.pawminder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IncomingTaskAdapter(
    private val items: MutableList<Task>,
    private val onFinishedClicked: (Task) -> Unit
) : RecyclerView.Adapter<IncomingTaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTaskTitle)
        val tvDateTime: TextView = view.findViewById(R.id.tvTaskDateTime)
        val btnFinished: Button = view.findViewById(R.id.btnMarkFinished)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_incoming_task, parent, false)
        return TaskViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val t = items[position]
        holder.tvTitle.text = "${t.petName} – ${t.taskType}"
        holder.tvDateTime.text = t.dateTimeText
        holder.btnFinished.setOnClickListener { onFinishedClicked(t) }
    }

    fun updateData(newItems: List<Task>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

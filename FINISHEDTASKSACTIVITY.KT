FINISHEDTASKSACTIVITY.KT
package com.example.pawminder

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FinishedTasksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_tasks)

        val rvFinished: RecyclerView = findViewById(R.id.rvFinishedTasks)
        val tvBack: TextView = findViewById(R.id.tvBackToMain)

        val allTasks = TaskStorage.loadTasks(this)
        val finishedTasks = allTasks.filter { it.finished }

        rvFinished.layoutManager = LinearLayoutManager(this)
        rvFinished.adapter = FinishedTaskAdapter(finishedTasks)

        tvBack.setOnClickListener { finish() } // ← return to main screen
    }
}

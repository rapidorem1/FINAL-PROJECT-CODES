MAINACTIVITY.KT
package com.example.pawminder
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var etPetName: EditText
    private lateinit var spinnerTask: Spinner
    private lateinit var etDateTime: EditText
    private lateinit var btnAddTask: Button
    private lateinit var rvIncoming: RecyclerView
    private lateinit var tvViewFinished: TextView

    private lateinit var incomingAdapter: IncomingTaskAdapter

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy, h:mm a", Locale.getDefault())

    // List that holds ALL tasks (incoming + finished)
    private lateinit var allTasks: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load saved tasks from SharedPreferences using Gson
        allTasks = TaskStorage.loadTasks(this)

        // Connect views
        etPetName = findViewById(R.id.etPetName)
        spinnerTask = findViewById(R.id.spinnerTask)
        etDateTime = findViewById(R.id.etDateTime)
        btnAddTask = findViewById(R.id.btnAddTask)
        rvIncoming = findViewById(R.id.rvIncomingTasks)
        tvViewFinished = findViewById(R.id.tvViewFinished)

        // Setup task dropdown
        val taskAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.task_types)
        )
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTask.adapter = taskAdapter

        // Open date & time picker when field is clicked
        etDateTime.setOnClickListener { openDateTimePicker() }

        // Setup RecyclerView for Incoming Tasks
        incomingAdapter = IncomingTaskAdapter(getIncomingTasks().toMutableList()) { task ->
            // When "Mark as Finished" is clicked
            task.finished = true
            TaskStorage.saveTasks(this, allTasks)
            refreshLists()
        }
        rvIncoming.layoutManager = LinearLayoutManager(this)
        rvIncoming.adapter = incomingAdapter

        // Add Task button
        btnAddTask.setOnClickListener { addTask() }

        // View Finished Tasks link
        tvViewFinished.setOnClickListener {
            val intent = Intent(this, FinishedTasksActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh list when coming back from FinishedTasksActivity
        refreshLists()
    }

    private fun openDateTimePicker() {
        val c = calendar

        // First: date picker
        DatePickerDialog(
            this,
            { _, year, month, day ->
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, month)
                c.set(Calendar.DAY_OF_MONTH, day)

                // Second: time picker
                TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        c.set(Calendar.MINUTE, minute)
                        c.set(Calendar.SECOND, 0)

                        etDateTime.setText(dateFormat.format(c.time))
                    },
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    false
                ).show()
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun addTask() {
        val name = etPetName.text.toString().trim()
        val dateTimeText = etDateTime.text.toString().trim()
        val taskType = spinnerTask.selectedItem.toString()

        if (name.isEmpty() || dateTimeText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create new Task
        val task = Task(
            id = System.currentTimeMillis(),
            petName = name,
            taskType = taskType,
            dateTimeText = dateTimeText,
            finished = false
        )

        // Add to list and save
        allTasks.add(task)
        TaskStorage.saveTasks(this, allTasks)

        // Update RecyclerView
        refreshLists()

        // Clear inputs
        etPetName.text.clear()
        etDateTime.text.clear()
        spinnerTask.setSelection(0)
    }

    // Filter only not-finished tasks (incoming)
    private fun getIncomingTasks(): List<Task> =
        allTasks.filter { !it.finished }

    // Refresh adapter data
    private fun refreshLists() {
        incomingAdapter.updateData(getIncomingTasks().toMutableList())
    }
}

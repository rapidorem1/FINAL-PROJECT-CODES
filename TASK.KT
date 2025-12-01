TASK.KT
package com.example.pawminder

data class Task(
    val id: Long,              // Unique identifier for the task
    val petName: String,       // Pet name (e.g. Choco)
    val taskType: String,      // Feeding, Walking, Grooming, etc.
    val dateTimeText: String,  // Schedule date/time in text
    var finished: Boolean = false  // Changes to true when completed
)

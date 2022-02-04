package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position:Int){
                //1. Remove the item from the list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }
        loadItems()

        //Look up the recyclerView in the activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate the items
        recyclerView.adapter = adapter
        // Set the layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button and input field so that the user can enter a task and add to the list

        val inputField = findViewById<EditText>(R.id.addTaskField)
        //Get a reference to the button

        //Set an onclick listener
        findViewById<Button>(R.id.button).setOnClickListener{
            //1. Grab the text that the user has inputted into the text field
            val userInputtedTask = inputField.text.toString()
            //2. Add the text to the list of tasks
            listOfTasks.add(userInputtedTask)

            //Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //3. Clear out the text field
            inputField.setText("")

            saveItems()
        }

    }

    // Save the data that the user has inputted

    //Save data by reading and writing from a file

    // Create a method to get the data that we need
    fun getDataFile(): File {

        //Every line represents a specific task in a list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }
    }
    // Save items by writing them in the data file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
}
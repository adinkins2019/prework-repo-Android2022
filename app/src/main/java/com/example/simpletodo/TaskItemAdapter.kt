package com.example.simpletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/* A bridge that tells the recycler View how to display the data we give it */
class TaskItemAdapter(private val listOfItems: List<String>,
                     val longClickListener: OnLongClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position:Int){

        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //Stores references to elements in our layoutView
        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }

    }

    //Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //Inflate the custom Layout
        val taskView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        //Return a new holder instance
        return ViewHolder(taskView)
    }

    //Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Get the data model based on position
        val task = listOfItems.get(position)
        // Set the item views based on your views and data model
        holder.textView.text = task

    }

    override fun getItemCount(): Int {

        return listOfItems.size
    }
}
package ru.napoleonit.smartmorozov.kotlinapp1

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import android.widget.ImageView


class VitaminAdapter (private val vitamins: MutableList<Vitamin>) : RecyclerView.Adapter<VitaminAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cv: CardView = itemView.findViewById(R.id.vitamin_cv) as CardView
        val vitaminName: TextView = itemView.findViewById(R.id.vitamin_name)
        val vitaminDescription: TextView = itemView.findViewById(R.id.vitamin_description)
        val vitaminIv: ImageView = itemView.findViewById(R.id.vitamin_iv)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VitaminAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_vitamin, parent, false)
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.vitaminName.text = vitamins[position].name
        holder.vitaminDescription.text = vitamins[position].description
        holder.vitaminIv.setImageResource(vitamins[position].avatar)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return vitamins.size
    }
}

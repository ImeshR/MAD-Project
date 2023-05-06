package com.example.madproject.Activity.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R

class Myadapter(private val newslist : ArrayList<News>) : RecyclerView.Adapter<Myadapter.MyViewholder>() {

    inner class MyViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.title_image)
        val deleteicon: ImageView = itemView.findViewById(R.id.deleteicon)
        val tvheading : TextView = itemView.findViewById(R.id.tvheading)
        val price: TextView = itemView.findViewById(R.id.price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myadapter.MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return MyViewholder(view)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val news = newslist[position]
        holder.imageView.setImageResource(news.imageResId)
        holder.tvheading.text = news.heading
        holder.price.text = news.price.toString()

        // Set click listener for delete icon
        holder.deleteicon.setOnClickListener {
            // Remove item from list
            newslist.removeAt(position)
            // Notify adapter about item removal
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return newslist.size
    }
}

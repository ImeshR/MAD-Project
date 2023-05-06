package com.example.madproject.Activity.Activity

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R

class BookDeleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.book_title)
    val imageView: ImageView = itemView.findViewById(R.id.book_image)
    val deleteButton: ImageButton = itemView.findViewById(R.id.updatebtnEsh)

}



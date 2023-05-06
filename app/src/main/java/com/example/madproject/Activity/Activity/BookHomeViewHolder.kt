package com.example.madproject.Activity.Activity

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R

class BookHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.book_title)
    val imageView: ImageView = itemView.findViewById(R.id.book_image)
    val viewbtn : LinearLayout = itemView.findViewById(R.id.BookData)

}
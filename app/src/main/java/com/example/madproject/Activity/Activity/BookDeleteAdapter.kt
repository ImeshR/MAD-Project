package com.example.madproject.Activity.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madproject.R

class BookDeleteAdapter(private val books: MutableList<BookD>) : RecyclerView.Adapter<BookDeleteViewHolder>()  {

    var deleteButtonClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookDeleteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_delete_book_item, parent, false)
        return BookDeleteViewHolder(view)
    }


    override fun onBindViewHolder(holder: BookDeleteViewHolder, position: Int) {
        val book = books[position]

        holder.titleTextView.text = book.title

        Glide.with(holder.itemView)
            .load(book.imageUrl)
            .into(holder.imageView)

        holder.deleteButton.setOnClickListener {
            deleteButtonClickListener?.invoke()
        }
    }

    override fun getItemCount() = books.size

}
package com.example.madproject.Activity.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madproject.R

class BookUpdateAdapter(private val books: MutableList<Book>) :
    RecyclerView.Adapter<BookUpdateViewHolder>() {

    var UpdateButtonClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookUpdateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_update_book_item, parent, false)
        return BookUpdateViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookUpdateViewHolder, position: Int) {
        val book = books[position]

        holder.titleTextView.text = book.title

        Glide.with(holder.itemView)
            .load(book.imageUrl)
            .into(holder.imageView)

        holder.updateButton.setOnClickListener {
            UpdateButtonClickListener?.invoke()


        }

    }
    override fun getItemCount() = books.size
}


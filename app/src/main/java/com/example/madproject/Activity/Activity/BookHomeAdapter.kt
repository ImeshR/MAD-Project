package com.example.madproject.Activity.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madproject.R

class BookHomeAdapter(private val books: List<Book>) : RecyclerView.Adapter<BookHomeViewHolder>() {



    var DataButtonClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHomeViewHolder {
        println("Say Hi!")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homebookitem, parent, false)
        return BookHomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookHomeViewHolder, position: Int) {
        val book = books[position]

        holder.titleView?.text = book.title

        if (!book.imageUrl.isNullOrEmpty()) {
            Glide.with(holder.imageView)
                .load(book.imageUrl)
                .into(holder.imageView)


            holder.viewbtn.setOnClickListener {
                DataButtonClickListener?.invoke()
            }

        } else {
//            holder.imageView.setImageResource(R.drawable.default_book_cover) // or any other default image
        }
    }



    override fun getItemCount() = books.size
}
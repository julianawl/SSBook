package com.julianawl.ssbook.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.julianawl.ssbook.LibraryQuery
import com.julianawl.ssbook.R

class LibraryAdapter : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    var onItemClickListener: (book: LibraryQuery.AllBook) -> Unit = {}
    private val books: ArrayList<LibraryQuery.AllBook> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size

    fun update(books: List<LibraryQuery.AllBook>) {
        this.books.clear()
        this.books.addAll(books)
        notifyDataSetChanged()
    }

    inner class LibraryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var book: LibraryQuery.AllBook
        private val item = view.findViewById<ConstraintLayout>(R.id.item_library)
        private val bookTitle =
            view.findViewById<MaterialTextView>(R.id.item_library_book_title_tv)
        private val bookAuthor =
            view.findViewById<MaterialTextView>(R.id.item_library_book_author_tv)
        private val bookCover =
            view.findViewById<AppCompatImageView>(R.id.item_library_book_cover_iv)

        fun bind(book: LibraryQuery.AllBook) {
            this.book = book
            bookTitle.text = book.name
            bookAuthor.text = book.author.name

            loadImage(book.cover)
            initActions(book)
        }

        private fun loadImage(url: String) {
            Glide.with(itemView)
                .load(url)
                .into(bookCover)
        }

        private fun initActions(book: LibraryQuery.AllBook) {
            item.setOnClickListener {
                onItemClickListener(book)
            }
        }
    }
}
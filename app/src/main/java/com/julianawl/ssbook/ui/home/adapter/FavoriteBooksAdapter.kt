package com.julianawl.ssbook.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.julianawl.ssbook.FavoriteBooksQuery
import com.julianawl.ssbook.R

class FavoriteBooksAdapter : RecyclerView.Adapter<FavoriteBooksAdapter.FavoriteBooksViewHolder>() {

    var onItemClickListener: (book: FavoriteBooksQuery.FavoriteBook) -> Unit = {}
    private val favoriteBooks: ArrayList<FavoriteBooksQuery.FavoriteBook> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBooksViewHolder {
        return FavoriteBooksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_books, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteBooksViewHolder, position: Int) {
        holder.bind(favoriteBooks[position])
    }

    override fun getItemCount(): Int = favoriteBooks.size

    fun update(favoriteBooks: List<FavoriteBooksQuery.FavoriteBook>) {
        this.favoriteBooks.clear()
        this.favoriteBooks.addAll(favoriteBooks)
        notifyDataSetChanged()
    }

    inner class FavoriteBooksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var book: FavoriteBooksQuery.FavoriteBook
        private val item = view.findViewById<ConstraintLayout>(R.id.item_fav_book)
        private val bookTitle =
            view.findViewById<MaterialTextView>(R.id.item_favorite_book_title_tv)
        private val bookAuthor =
            view.findViewById<MaterialTextView>(R.id.item_favorite_book_author_tv)
        private val bookCover =
            view.findViewById<AppCompatImageView>(R.id.item_favorite_book_cover_iv)

        fun bind(book: FavoriteBooksQuery.FavoriteBook) {
            this.book = book
            bookTitle.text = book.name
            bookAuthor.text = book.author.name
            loadImage(book.cover)

            initActions(book)
        }

        private fun initActions(book: FavoriteBooksQuery.FavoriteBook) {
            item.setOnClickListener {
                onItemClickListener(book)
            }
        }

        private fun loadImage(url: String) {
            Glide.with(itemView)
                .load(url)
                .into(bookCover)
        }
    }
}
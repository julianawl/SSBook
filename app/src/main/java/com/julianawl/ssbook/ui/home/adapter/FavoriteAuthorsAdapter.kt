package com.julianawl.ssbook.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.julianawl.ssbook.FavoriteAuthorsQuery
import com.julianawl.ssbook.R

class FavoriteAuthorsAdapter :
    RecyclerView.Adapter<FavoriteAuthorsAdapter.FavoriteAuthorsViewHolder>() {

    private val favoriteAuthors: ArrayList<FavoriteAuthorsQuery.FavoriteAuthor> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAuthorsViewHolder {
        return FavoriteAuthorsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite_author, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteAuthorsViewHolder, position: Int) {
        holder.bind(favoriteAuthors[position])
    }

    override fun getItemCount(): Int = favoriteAuthors.size

    fun update(authors: List<FavoriteAuthorsQuery.FavoriteAuthor>) {
        favoriteAuthors.clear()
        favoriteAuthors.addAll(authors)
        notifyDataSetChanged()
    }

    inner class FavoriteAuthorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var author: FavoriteAuthorsQuery.FavoriteAuthor
        private val authorName =
            view.findViewById<MaterialTextView>(R.id.item_favorite_author_name_tv)
        private val authorProfileImage =
            view.findViewById<AppCompatImageView>(R.id.item_favorite_author_iv)
        private val authorBooksCount =
            view.findViewById<MaterialTextView>(R.id.item_favorite_author_books_count_tv)

        fun bind(author: FavoriteAuthorsQuery.FavoriteAuthor) {
            this.author = author
            authorName.text = author.name
            if(author.booksCount == 1){
                authorBooksCount.text = "${author.booksCount} livro"
            } else {
                authorBooksCount.text = "${author.booksCount} livros"
            }

            loadImage(author.picture)
        }

        private fun loadImage(url: String) {
            Glide.with(itemView)
                .load(url)
                .into(authorProfileImage)
        }
    }
}
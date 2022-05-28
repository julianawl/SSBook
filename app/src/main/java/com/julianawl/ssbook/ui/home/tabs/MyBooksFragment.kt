package com.julianawl.ssbook.ui.home.tabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.exception.ApolloException
import com.julianawl.ssbook.*
import com.julianawl.ssbook.apollo.apolloClient
import com.julianawl.ssbook.databinding.FragmentMyBooksBinding
import com.julianawl.ssbook.model.FilterItem
import com.julianawl.ssbook.model.toChip
import com.julianawl.ssbook.ui.home.HomeFragmentDirections
import com.julianawl.ssbook.ui.home.adapter.FavoriteAuthorsAdapter
import com.julianawl.ssbook.ui.home.adapter.FavoriteBooksAdapter
import com.julianawl.ssbook.ui.home.adapter.LibraryAdapter
import java.util.*

class MyBooksFragment : Fragment() {

    private val controller by lazy { findNavController() }
    private var _binding: FragmentMyBooksBinding? = null
    private val binding get() = _binding!!

    private val favoriteBooksAdapter by lazy { FavoriteBooksAdapter() }
    private val favoriteAuthorsAdapter by lazy { FavoriteAuthorsAdapter() }
    private val libraryAdapter by lazy { LibraryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFavoriteBooksList()
        setFavoriteAuthorsList()
        setLibraryList()
        setFilters()
    }

    private fun setFilters() {
        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(GenreFiltersQuery()).execute()
            } catch (e: ApolloException) {
                Log.e(APOLLO_ERROR_TAG, e.message!!)
                null
            }

            val filtersQuery = response?.data?.__type?.enumValues
            if (filtersQuery != null && !response.hasErrors()) {
                val filters: MutableList<FilterItem> = mutableListOf()
                filters.add(FilterItem(getString(R.string.filter_all)))
                for (filter in filtersQuery) {
                    filters.add(
                        FilterItem(filter.name.lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                    )
                }
                binding.let {
                    filters.forEach { filter ->
                        it.myBooksLibraryGenreCg.addView(filter.toChip(requireContext()))
                    }
                }
            }
        }
    }

    private fun setFavoriteBooksList() {
        binding.myBooksFavoriteRv.adapter = favoriteBooksAdapter

        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(FavoriteBooksQuery()).execute()
            } catch (e: ApolloException) {
                Log.e(APOLLO_ERROR_TAG, e.message!!)
                null
            }

            val books = response?.data?.favoriteBooks
            if (books != null && !response.hasErrors()) {
                favoriteBooksAdapter.update(books)
            }
        }

        favoriteBooksAdapter.onItemClickListener = {
            controller.navigate(
                HomeFragmentDirections.actionHomeFragmentToBookFragment(it.id)
            )
        }
    }

    private fun setFavoriteAuthorsList() {
        binding.myBooksFavoriteAuthorsRv.adapter = favoriteAuthorsAdapter

        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(FavoriteAuthorsQuery()).execute()
            } catch (e: ApolloException) {
                Log.e(APOLLO_ERROR_TAG, e.message!!)
                null
            }

            val authors = response?.data?.favoriteAuthors
            if (authors != null && !response.hasErrors()) {
                favoriteAuthorsAdapter.update(authors)
            }
        }
    }

    private fun setLibraryList() {
        binding.myBooksLibraryRv.adapter = libraryAdapter

        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(LibraryQuery()).execute()
            } catch (e: ApolloException) {
                Log.e(APOLLO_ERROR_TAG, e.message!!)
                null
            }

            val books = response?.data?.allBooks
            if (books != null && !response.hasErrors()) {
                libraryAdapter.update(books)
            }
        }

        libraryAdapter.onItemClickListener = {
            controller.navigate(
                HomeFragmentDirections.actionHomeFragmentToBookFragment(it.id)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val APOLLO_ERROR_TAG = "Apollo Error"
    }
}
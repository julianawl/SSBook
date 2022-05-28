package com.julianawl.ssbook.ui.book

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo3.exception.ApolloException
import com.bumptech.glide.Glide
import com.julianawl.ssbook.BookQuery
import com.julianawl.ssbook.R
import com.julianawl.ssbook.apollo.apolloClient
import com.julianawl.ssbook.databinding.FragmentBookBinding
import com.julianawl.ssbook.ui.home.tabs.MyBooksFragment

class BookFragment: Fragment() {
    private val controller by lazy { findNavController() }
    private val argument by navArgs<BookFragmentArgs>()
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = Color.TRANSPARENT
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBookInformation()
        setBackBtn()
        resources
    }

    private fun setBackBtn() {
        binding.bookBackBtn.setOnClickListener {
            controller.navigate(R.id.homeFragment)
        }
    }

    private fun setBookInformation() {
        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(BookQuery(argument.bookId)).execute()
            } catch (e: ApolloException) {
                Log.e(MyBooksFragment.APOLLO_ERROR_TAG, e.message!!)
                null
            }

            val book = response?.data?.book
            if (book != null && !response.hasErrors()) {
                binding.bookTitleTv.text = book.name
                binding.bookAuthorTv.text = book.author.name
                binding.bookSynopsisTv.text = book.description
                Glide.with(requireView()).load(book.cover).into(binding.bookCoverIv)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
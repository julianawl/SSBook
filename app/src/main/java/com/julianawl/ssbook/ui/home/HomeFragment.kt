package com.julianawl.ssbook.ui.home

import android.os.Bundle
import android.text.Html.fromHtml
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.bumptech.glide.Glide
import com.julianawl.ssbook.R
import com.julianawl.ssbook.UserPictureQuery
import com.julianawl.ssbook.apollo.apolloClient
import com.julianawl.ssbook.databinding.FragmentHomeBinding
import com.julianawl.ssbook.ui.home.adapter.ViewPagerAdapter
import com.julianawl.ssbook.ui.home.tabs.MyBooksFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        setProfilePicture()
        setupTabs()
    }

    private fun setTitle() {
        val title = "<font color=#555555>SS</font><font color=#A076F2>BOOK</font>"
        binding.homeLogoTv.text = fromHtml(title)
    }

    private fun setupTabs() {
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(MyBooksFragment(), getString(R.string.tab_my_books_title))
        adapter.addFragment(Fragment(), getString(R.string.tab_borrowed_title))

        binding.homeViewpager.adapter = adapter
        binding.homeTabLayout.setupWithViewPager(binding.homeViewpager)
    }

    private fun setProfilePicture() {
        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(UserPictureQuery()).execute()
            } catch (e: ApolloException) {
                Log.e(APOLLO_ERROR_TAG, e.message!!)
                null
            }
            val picture = response?.data?.userPicture
            Glide.with(requireView()).load(picture).into(binding.homeProfileIv)
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
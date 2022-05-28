package com.julianawl.ssbook.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

@Suppress("DEPRECATION")
class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragTitleList = ArrayList<String>()

    override fun getCount(): Int = mFragTitleList.size

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getPageTitle(position: Int): CharSequence = mFragTitleList[position]

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragTitleList.add(title)
    }
}
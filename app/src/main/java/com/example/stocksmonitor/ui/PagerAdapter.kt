package com.example.stocksmonitor.ui

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.stocksmonitor.ui.favourite.FavouriteFragment
import com.example.stocksmonitor.ui.stocks.StocksFragment

class PagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    companion object {
        const val POSITION_STOCKS = 0
        const val POSITION_FAVOURITE = 1
    }

    private var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun setTabs() {
        fragments.add(POSITION_STOCKS, StocksFragment())
        fragments.add(POSITION_FAVOURITE, FavouriteFragment())
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val currentFragment = super.instantiateItem(container, position) as Fragment
        fragments[position] = currentFragment
        return currentFragment
    }



}

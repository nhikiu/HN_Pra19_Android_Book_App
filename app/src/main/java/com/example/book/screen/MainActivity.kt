package com.example.book.screen

import com.example.book.R
import com.example.book.screen.favorite.FavoriteFragment
import com.example.book.utils.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val fragment = FavoriteFragment.newInstance()

        // Lấy FragmentManager và bắt đầu một giao dịch Fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}

package com.example.junjie.youkyarchitecture

import android.content.res.ColorStateList
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.helper.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.app_main_activity.*

class OriginalActivity : BaseActivity() {

//    lateinit var pageFragment: HomeFragment
//    lateinit var knowledgeFragment: MineFragment

    override fun attachLayoutRes(): Int {
        return R.layout.app_main_activity
    }

    override fun initView() {
        main_activity_navigation.inflateMenu(R.menu.main_navigation_menu)

        val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))

        val colors =
            intArrayOf(resources.getColor(R.color.home_icon_unchecked), resources.getColor(R.color.home_icon_checked))
        val csl = ColorStateList(states, colors)
        main_activity_navigation.itemTextColor = csl
        main_activity_navigation.itemIconTintList = csl
        BottomNavigationViewHelper.disableShiftMode(main_activity_navigation)
    }

    override fun initData() {

    }

    override fun start() {

    }

    override fun initListener() {

    }


}

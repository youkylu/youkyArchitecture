package com.example.junjie.youkyarchitecture

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.base.BaseFragment
import com.example.commonlib.constant.ARouterUrlConstant
import com.example.commonlib.util.ARouterUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.app_main_activity.*
import java.util.*
import kotlin.collections.HashMap

class OriginalActivity : BaseActivity() {

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_MAIN = 0x02
    private val FRAGMENT_MINE = 0x03

    private val BOTTOM_INDEX: String = "bottom_index"

    private var mIndex = FRAGMENT_HOME

    //home模块Fragment
    private var mHomeFragment: BaseFragment? = null
    //main模块Fragment
    private var mMainFragment: BaseFragment? = null
    //main模块Fragment
    private var mMineFragment: BaseFragment? = null

    /**
     * 存放切换Fragment
     */
    private val mFragmentList = ArrayList<Fragment>()

    override fun attachLayoutRes(): Int {
        return R.layout.app_main_activity
    }

    override fun initView(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            mIndex = savedInstanceState?.getInt(BOTTOM_INDEX)
        }
        val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))

        val colors =
            intArrayOf(resources.getColor(R.color.home_icon_unchecked), resources.getColor(R.color.home_icon_checked))
        val csl = ColorStateList(states, colors)
        main_activity_navigation.run {
            inflateMenu(R.menu.main_navigation_menu)
            itemTextColor = csl
            itemIconTintList = csl

            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
        showFragment(mIndex)
    }


    override fun initData() {

    }

    override fun start() {

    }

    override fun initListener() {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BOTTOM_INDEX, mIndex)
    }


    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when (index) {
            FRAGMENT_HOME // 首页
            -> {
                if (mHomeFragment == null) {
                  var map =  HashMap<String, Any>()
                    map["content"] = "测试home fragment"
                    map["times"] = 1231231
                    mHomeFragment =
                            ARouterUtils
                                .getFragment(ARouterUrlConstant.HOME_FRAGMENT, map)
                    transaction.add(R.id.main_activity_container, mHomeFragment!!, ARouterUrlConstant.HOME_FRAGMENT)
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_MAIN       //主页
            -> {
                if (mMainFragment == null) {
                    var mainMap =  HashMap<String, Any>()
                    mainMap["contentMain"] = "测试main fragment"

                    mMainFragment = ARouterUtils.getFragment(ARouterUrlConstant.MAIN_FRAGMENT,mainMap)
                    transaction.add(R.id.main_activity_container, mMainFragment!!, ARouterUrlConstant.MAIN_FRAGMENT)
                } else {
                    transaction.show(mMainFragment!!)
                }
            }
            FRAGMENT_MINE   //我的
            -> {
                if (mMineFragment == null) {
                    var mineMap =  HashMap<String, Any>()
                    mineMap["mineContent"] = "测试mine fragment"
                    mMineFragment = ARouterUtils.getFragment(ARouterUrlConstant.MINE_FRAGMENT,mineMap )
                    transaction.add(R.id.main_activity_container, mMineFragment!!, ARouterUrlConstant.MINE_FRAGMENT)
                } else {
                    transaction.show(mMineFragment!!)
                }
            }


        }
        transaction.commit()
    }

    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mMainFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }


    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.navigation_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.navigation_main -> {
                    showFragment(FRAGMENT_MAIN)
                    true
                }
                R.id.navigation_mine -> {
                    showFragment(FRAGMENT_MINE)
                    true
                }
                else -> {
                    false
                }

            }
        }

}

package com.example.main

import android.os.Bundle
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.base.BaseFragment
import com.example.commonlib.constant.ARouterUrlConstant
import com.example.commonlib.util.ARouterUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.main_main_activity.*

class MainActivity : BaseActivity() {
    private val transaction = supportFragmentManager.beginTransaction()

    private var mMainFragment: BaseFragment? = null

    override fun attachLayoutRes(): Int {
        return   R.layout.main_main_activity

    }

    override fun initData() {
        if (mMainFragment == null) {
            var map =  HashMap<String, Any>()
            map["contentMain"] = "单独测试main fragment"
            mMainFragment = ARouterUtils.getFragment(ARouterUrlConstant.MAIN_FRAGMENT)
            transaction.add(R.id.main_main_activity_container, mMainFragment!!, ARouterUrlConstant.MAIN_FRAGMENT)
        }
        transaction.show(mMainFragment!!).commit()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun start() {

    }

    override fun initListener() {

    }


}

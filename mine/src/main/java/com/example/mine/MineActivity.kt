package com.example.mine


import android.os.Bundle

import com.example.commonlib.base.BaseActivity
import com.example.commonlib.base.BaseFragment
import com.example.commonlib.constant.ARouterUrlConstant
import com.example.commonlib.util.ARouterUtils
import kotlinx.android.synthetic.main.mine_main_activity.*

class MineActivity : BaseActivity() {
    private val transaction = supportFragmentManager.beginTransaction()

    private var mMineFragment: BaseFragment? = null

    override fun attachLayoutRes(): Int {
        return R.layout.mine_main_activity
    }

    override fun initData() {
        if (mMineFragment == null) {
            var mineMap = HashMap<String,Any>()
            mineMap["mineContent"] = "单独测试mine Fragment"
            mMineFragment = ARouterUtils.getFragment(ARouterUrlConstant.MINE_FRAGMENT,mineMap)
            transaction.add(R.id.mine_main_activity_container, mMineFragment!!, ARouterUrlConstant.MINE_FRAGMENT)
        }
        transaction.show(mMineFragment!!).commit()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }



    override fun start() {

    }

    override fun initListener() {

    }

}

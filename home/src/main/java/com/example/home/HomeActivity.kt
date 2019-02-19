package com.example.home


import android.os.Bundle
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.base.BaseFragment
import com.example.commonlib.constant.ARouterUrlConstant
import com.example.commonlib.util.ARouterUtils
import kotlinx.android.synthetic.main.home_main_activity.*

class HomeActivity : BaseActivity() {

    private val transaction = supportFragmentManager.beginTransaction()

    private var mHomeFragment: BaseFragment? = null

    override fun attachLayoutRes(): Int {
        return R.layout.home_main_activity
    }

    override fun initView(savedInstanceState: Bundle?) {

        if (mHomeFragment == null) {
            var map =  HashMap<String, Any>()
            map["content"] = "单独测试home fragment"

            mHomeFragment = ARouterUtils.getFragment(ARouterUrlConstant.HOME_FRAGMENT,map)
            transaction.add(R.id.home_main_activity_container, mHomeFragment!!, ARouterUrlConstant.HOME_FRAGMENT)
        }
        transaction.show(mHomeFragment!!).commit()

    }

    override fun initData() {

    }

    override fun start() {

    }

    override fun initListener() {

    }


}

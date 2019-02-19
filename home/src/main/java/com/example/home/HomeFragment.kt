package com.example.home

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.base.BaseFragment
import com.example.commonlib.constant.ARouterUrlConstant

import com.example.home.R
import kotlinx.android.synthetic.main.home_home_fragment.*

/**
 * <p>Class: com.example.home.HomeFragment</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/18/15:07
 */
@Route(path = ARouterUrlConstant.HOME_FRAGMENT)
class HomeFragment : BaseFragment() {

    @JvmField
    @Autowired
    var content: String? = null

    @JvmField
    @Autowired()
    var times: Int = 0

    override fun attachLayoutRes(): Int {
        return R.layout.home_home_fragment
    }

    override fun initView(view: View) {
        home_home_fragment_content_tv.text = content + times
    }

    override fun lazyLoad() {

    }

}
package com.example.main

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.base.BaseFragment
import com.example.commonlib.constant.ARouterUrlConstant

import kotlinx.android.synthetic.main.main_main_fragment.*

/**
 * <p>Class: com.example.main.MainFragment</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/19/10:21
 */
@Route(path = ARouterUrlConstant.MAIN_FRAGMENT)
class MainFragment : BaseFragment() {

    @JvmField
    @Autowired
    var contentMain: String? = null

    override fun attachLayoutRes(): Int {
        return R.layout.main_main_fragment
    }

    override fun initView(view: View) {
        main_main_fragment_content_tv.text = contentMain
    }

    override fun lazyLoad() {
    }

}
package com.example.mine

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.base.BaseFragment
import com.example.commonlib.constant.ARouterUrlConstant

import kotlinx.android.synthetic.main.mine_main_fragment.*

/**
 * <p>Class: com.example.mine.MineFragment</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/19/10:54
 */
@Route(path = ARouterUrlConstant.MINE_FRAGMENT)
class MineFragment : BaseFragment() {

    @JvmField
    @Autowired
    var mineContent: String? = null


    override fun attachLayoutRes(): Int {
        return R.layout.mine_main_fragment
    }

    override fun initView(view: View) {

        mineContent?.let {  mine_main_fragment_content_tv.text =it }
    }

    override fun lazyLoad() {

    }

}


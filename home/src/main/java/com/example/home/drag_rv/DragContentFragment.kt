package com.example.home.drag_rv

import android.view.View
import com.example.commonlib.base.BaseFragment
import com.example.home.R

import kotlinx.android.synthetic.main.home_drag_content_fragment.*

/**
 *
 * Class: com.example.home.drag_rv.DragContentFragment
 *
 * Description:
 * <pre>
 *
</pre> *
 *
 * @author lujunjie
 * @date 2019/3/1/11:51
 */

class DragContentFragment:BaseFragment() {

    override fun attachLayoutRes(): Int {
      return R.layout.home_drag_content_fragment
    }

    override fun initView(view: View) {
        home_drag_content_fragment_tv.text = arguments!!.get("name") as String
    }

    override fun lazyLoad() {

    }
}

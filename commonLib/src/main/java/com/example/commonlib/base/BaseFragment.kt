package com.example.commonlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.example.commonlib.constant.Constant
import com.example.commonlib.event.NetworkChangeEvent
import com.example.commonlib.util.Preference
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *
 * Class: com.example.commonlib.base.BaseFragment
 *
 * Description:
 * <pre>
 *
</pre> *
 *
 * @author lujunjie
 * @date 2019/2/12/17:14
 */

abstract class BaseFragment  : Fragment(){
    /**
     * check login
     */
    protected var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    /**
     * 缓存上一次的网络状态
     */
    protected var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false
    /**
     * 多种状态的 View 的切换
     */
//    protected var mLayoutStatusView: MultipleStatusView? = null

    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun attachLayoutRes(): Int

    /**
     * 初始化 View
     */
    abstract fun initView(view: View)

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoad()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ARouter.getInstance().inject(this)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        return inflater?.inflate(attachLayoutRes(), null)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
//        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        if (event.isConnected) {
            doReConnected()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}

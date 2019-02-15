package com.example.commonlib.base

import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.commonlib.R
import com.example.commonlib.constant.Constant
import com.example.commonlib.event.NetworkChangeEvent
import com.example.commonlib.receiver.NetworkChangeReceiver
import com.example.commonlib.util.CommonUtil
import com.example.commonlib.util.KeyBoardUtil
import com.example.commonlib.util.Preference
import com.example.commonlib.util.SettingUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *
 * Class: com.example.commonlib.base.BaseActivity
 *
 * Description:
 * <pre>
 *
</pre> *
 *
 * @author lujunjie
 * @date 2019/2/12/13:50
 */

abstract class BaseActivity : AppCompatActivity() {
    /**
     * check login
     */
    protected var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    /**
     * 缓存上一次的网络状态
     */
    protected var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    /**
     * 网络状态变化的广播
     */
    protected var mNetworkChangeReceiver: NetworkChangeReceiver? = null

    /**
     * theme color
     */
    protected var mThemeColor: Int = SettingUtil.getColor()

    /**
     * 多种状态的 View 的切换
     */
//    protected var mLayoutStatusView: MultipleStatusView? = null

    /**
     * 提示View
     */
    protected lateinit var mTipView: View
    protected lateinit var mWindowManager: WindowManager
    protected lateinit var mLayoutParams: WindowManager.LayoutParams

    /**
     * 布局文件id
     */
    protected abstract fun attachLayoutRes(): Int

    /**
     * 初始化 View
     */
    abstract fun initView()
    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 开始请求
     */
    abstract fun start()

    /**
     *注册监听
     */
    abstract fun initListener()

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 是否需要显示 TipView
     */
    open fun enableNetworkTip(): Boolean = true

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // AutoDensityUtil.setCustomDensity(this, App.instance)
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initView()
        initData()
        initTipView()

        start()
        initListener()
    }

    override fun onResume() {
        // 动态注册网络变化广播
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangeReceiver = NetworkChangeReceiver(this)
        registerReceiver(mNetworkChangeReceiver, filter)

        super.onResume()

        // 在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查

        // 1.第一次进入界面会导致 start() 方法走两次
        // 2.后台切换到前台时，会调用 start() 方法执行相应的操作
        // 此处不应该调用，删掉，修改 #13
        // checkNetwork(hasNetwork)

    }


    /**
     * 初始化 TipView
     */
    private fun initTipView() {
        mTipView = layoutInflater.inflate(R.layout.layout_network_tip, null)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT)
        mLayoutParams.gravity = Gravity.TOP
        mLayoutParams.x = 0
        mLayoutParams.y = 0
        mLayoutParams.windowAnimations = R.style.anim_float_view // add animations
    }



    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    protected fun initToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean, title: String) {
        toolbar?.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }

    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetwork = event.isConnected
        checkNetwork(event.isConnected)
    }

    private fun checkNetwork(isConnected: Boolean) {
        if (enableNetworkTip()) {
            if (isConnected) {
                doReConnected()
                if (mTipView != null && mTipView.parent != null) {
                    mWindowManager.removeView(mTipView)
                }
            } else {
                if (mTipView.parent == null) {
                    mWindowManager.addView(mTipView, mLayoutParams)
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            // 如果不是落在EditText区域，则需要关闭输入法
            if (KeyBoardUtil.isHideKeyboard(v, ev)) {
                KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        // Fragment 逐个出栈
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onPause() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        CommonUtil.fixInputMethodManagerLeak(this)

    }

    override fun finish() {
        super.finish()
        if (mTipView != null && mTipView.parent != null) {
            mWindowManager.removeView(mTipView)
        }
    }
}

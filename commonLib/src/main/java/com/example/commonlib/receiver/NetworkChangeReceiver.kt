package com.example.commonlib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.constant.Constant
import com.example.commonlib.event.NetworkChangeEvent
import com.example.commonlib.util.NetWorkUtil
import com.example.commonlib.util.Preference
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference

/**
 * <p>Class: com.example.commonlib.receiver.NetworkChangeReceiver</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/12/14:52
 */

class NetworkChangeReceiver(baseCompatActivity: BaseActivity) : BroadcastReceiver(){

    private  var weakReference: WeakReference<BaseActivity> = WeakReference<BaseActivity>(baseCompatActivity)

    /**
     * 缓存上一次的网络状态
     */
    private var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(weakReference.get()!!)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }

}
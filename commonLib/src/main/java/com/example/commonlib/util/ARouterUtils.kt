package com.example.commonlib.util

import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.base.BaseBean
import com.example.commonlib.base.BaseFragment
import java.util.ArrayList
import java.util.HashMap

/**
 * <p>Class: com.example.commonlib.util.ARouterUtils</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/18/11:39
 */

object ARouterUtils {


    /**
     * 根据path返回Fragment
     *
     * @param path path
     * @return fragment
     */
    fun getFragment(path: String): BaseFragment {
        return ARouter.getInstance()
            .build(path)
            .navigation() as BaseFragment
    }

    fun getFragment(path: String, params: Map<String, Any>): BaseFragment {

        val postcard = ARouter.getInstance()
            .build(path)
        if (params != null) {
           for(( key,value ) in params){
                when (value) {
                    is String -> postcard.withString(key, value)
                    is Boolean -> postcard.withBoolean(key, value)
                    is Int -> postcard.withInt(key, value)
                    is Float -> postcard.withFloat(key, value)
                    is Double -> postcard.withDouble(key, value)
                    is Long -> postcard.withLong(key, value)
                    is Short -> postcard.withShort(key, value)
                    is BaseBean -> postcard.withSerializable(key, value )
                    is ArrayList<*> -> postcard.withSerializable(key, value)
                    is HashMap<*, *> -> postcard.withSerializable(key, value)
                }
           }
        }
        return postcard.navigation() as BaseFragment
    }


    /**
     * 根据path返回Activity
     *
     * @param path path
     * @return Activity
     */
    fun getActivity(path: String): BaseActivity {
        return ARouter.getInstance()
            .build(path)
            .navigation() as BaseActivity
    }

    fun getActivity(path: String, params: Map<String, Any>): BaseActivity{
        val postcard = ARouter.getInstance()
            .build(path)
        if (params != null) {
            for(( key,value ) in params){
                when (value) {
                    is String -> postcard.withString(key, value)
                    is Boolean -> postcard.withBoolean(key, value)
                    is Int -> postcard.withInt(key, value)
                    is Float -> postcard.withFloat(key, value)
                    is Double -> postcard.withDouble(key, value)
                    is Long -> postcard.withLong(key, value)
                    is Short -> postcard.withShort(key, value)
                    is BaseBean -> postcard.withSerializable(key, value )
                    is ArrayList<*> -> postcard.withSerializable(key, value)
                    is HashMap<*, *> -> postcard.withSerializable(key, value)
                }
            }
        }
        return postcard.navigation() as BaseActivity
    }
}
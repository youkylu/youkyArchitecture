package com.example.home

import android.os.Bundle
import android.util.Log
import cn.jzvd.JZUserAction
import cn.jzvd.JZUserActionStd
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.bumptech.glide.Glide
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.video.MyJzvdStd

import kotlinx.android.synthetic.main.home_video_activity.*

/**
 * <p>Class: com.example.home.VideoActivity</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/26/10:29
 */

class VideoActivity : BaseActivity(){
     private lateinit var myJzvdStd: MyJzvdStd

    override fun attachLayoutRes(): Int {
      return  R.layout.home_video_activity
    }

    override fun initData() {



    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun start() {
        myJzvdStd = jz_video
        myJzvdStd.setUp(
            "http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4",
            "饺子快长大",
            JzvdStd.SCREEN_WINDOW_NORMAL
        )
        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
            .into(myJzvdStd.thumbImageView)
        Jzvd.setJzUserAction(MyUserActionStd())
    }

    override fun initListener() {

    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }


    /**
     * 这只是给埋点统计用户数据用的，不能写和播放相关的逻辑，监听事件请参考MyJzvdStd，复写函数取得相应事件
     */
    internal inner class MyUserActionStd : JZUserActionStd {

        override fun onEvent(type: Int, url: Any, screen: Int, vararg objects: Any) {
            when (type) {
                JZUserAction.ON_CLICK_START_ICON -> Log.i(
                    "USER_EVENT",
                    "ON_CLICK_START_ICON" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_CLICK_START_ERROR -> Log.i(
                    "USER_EVENT",
                    "ON_CLICK_START_ERROR" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_CLICK_START_AUTO_COMPLETE -> Log.i(
                    "USER_EVENT",
                    "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_CLICK_PAUSE -> Log.i(
                    "USER_EVENT",
                    "ON_CLICK_PAUSE" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_CLICK_RESUME -> Log.i(
                    "USER_EVENT",
                    "ON_CLICK_RESUME" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_SEEK_POSITION -> Log.i(
                    "USER_EVENT",
                    "ON_SEEK_POSITION" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_AUTO_COMPLETE -> Log.i(
                    "USER_EVENT",
                    "ON_AUTO_COMPLETE" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_ENTER_FULLSCREEN -> Log.i(
                    "USER_EVENT",
                    "ON_ENTER_FULLSCREEN" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_QUIT_FULLSCREEN -> Log.i(
                    "USER_EVENT",
                    "ON_QUIT_FULLSCREEN" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_ENTER_TINYSCREEN -> Log.i(
                    "USER_EVENT",
                    "ON_ENTER_TINYSCREEN" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_QUIT_TINYSCREEN -> Log.i(
                    "USER_EVENT",
                    "ON_QUIT_TINYSCREEN" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME -> Log.i(
                    "USER_EVENT",
                    "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION -> Log.i(
                    "USER_EVENT",
                    "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )

                JZUserActionStd.ON_CLICK_START_THUMB -> Log.i(
                    "USER_EVENT",
                    "ON_CLICK_START_THUMB" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                JZUserActionStd.ON_CLICK_BLANK -> Log.i(
                    "USER_EVENT",
                    "ON_CLICK_BLANK" + " title is : " + (if (objects.size == 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen
                )
                else -> Log.i("USER_EVENT", "unknow")
            }
        }
    }
}
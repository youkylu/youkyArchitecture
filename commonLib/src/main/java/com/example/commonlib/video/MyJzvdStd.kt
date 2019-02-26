package com.example.commonlib.video

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import cn.jzvd.JZUserActionStd
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.example.commonlib.R
import com.example.commonlib.dialog.VideoNetWorkDialog

/**
 * 这里可以监听到视频播放的生命周期和播放状态
 * 所有关于视频的逻辑都应该写在这里
 * Created by Nathen on 2017/7/2.
 */
class MyJzvdStd : JzvdStd {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun init(context: Context) {
        super.init(context)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        val i = v.id
        if (i == cn.jzvd.R.id.fullscreen) {
            if (currentScreen == Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                //click quit fullscreen
            } else {
                //click goto fullscreen
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.jz_layout_std
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return super.onTouch(v, event)
    }

    override fun startVideo() {
        super.startVideo()
    }

    //onState 代表了播放器引擎的回调，播放视频各个过程的状态的回调
    override fun onStateNormal() {
        super.onStateNormal()
    }

    override fun onStatePreparing() {
        super.onStatePreparing()
    }

    override fun onStatePlaying() {
        super.onStatePlaying()
    }

    override fun onStatePause() {
        super.onStatePause()
    }

    override fun onStateError() {
        super.onStateError()
    }

    override fun onStateAutoComplete() {
        super.onStateAutoComplete()
    }

    //changeUiTo 真能能修改ui的方法
    override fun changeUiToNormal() {
        super.changeUiToNormal()
    }

    override fun changeUiToPreparing() {
        super.changeUiToPreparing()
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
    }

    override fun changeUiToPlayingClear() {
        super.changeUiToPlayingClear()
    }

    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
    }

    override fun changeUiToPauseClear() {
        super.changeUiToPauseClear()
    }

    override fun changeUiToComplete() {
        super.changeUiToComplete()
    }

    override fun changeUiToError() {
        super.changeUiToError()
    }

    override fun onInfo(what: Int, extra: Int) {
        super.onInfo(what, extra)
    }

    override fun onError(what: Int, extra: Int) {
        super.onError(what, extra)
    }

    override fun startWindowFullscreen() {
        super.startWindowFullscreen()
    }

    override fun startWindowTiny() {
        super.startWindowTiny()
    }

    override fun showWifiDialog() {
//        val builder = AlertDialog.Builder(context)
//        builder.setMessage(resources.getString(cn.jzvd.R.string.tips_not_wifi))
//        builder.setPositiveButton(resources.getString(cn.jzvd.R.string.tips_not_wifi_confirm)) { dialog, which ->
//            dialog.dismiss()
//            onEvent(JZUserActionStd.ON_CLICK_START_WIFIDIALOG)
//            startVideo()
//            Jzvd.WIFI_TIP_DIALOG_SHOWED = true
//        }
//        builder.setNegativeButton(resources.getString(cn.jzvd.R.string.tips_not_wifi_cancel)) { dialog, which ->
//            dialog.dismiss()
//            clearFloatScreen()
//        }
//        builder.setOnCancelListener { it.dismiss() }
//        builder.create().show()

        var wifiDialog: VideoNetWorkDialog =
            VideoNetWorkDialog.Builder(context).setLayout(R.layout.video_network_dialog_layout)
                .setConfirmListener(DialogInterface.OnClickListener { dialog, _ ->
                    run {
                        dialog.dismiss()
                        onEvent(JZUserActionStd.ON_CLICK_START_WIFIDIALOG)
                        startVideo()
                        Jzvd.WIFI_TIP_DIALOG_SHOWED = true
                    }
                })
                .setCancelListener(DialogInterface.OnClickListener { dialog, _ ->
                    run {
                        dialog.dismiss()
                        clearFloatScreen()
                    }
                })
                .setCancelable(false)
                .create()

        wifiDialog.show()


    }


}

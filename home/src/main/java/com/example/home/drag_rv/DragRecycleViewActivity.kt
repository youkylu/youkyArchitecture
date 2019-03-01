package com.example.home.drag_rv

import android.app.ActionBar
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioGroup
import android.widget.TextView
import com.example.commonlib.base.BaseActivity
import com.example.home.R

import kotlinx.android.synthetic.main.home_drag_recycle_view_activity.*

/**
 * <p>Class: com.example.home.drag_rv.DragRecycleViewActivity</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/28/15:53
 */

class DragRecycleViewActivity : BaseActivity(), View.OnClickListener{

    private  var popupWindow:PopupWindow?=null

    override fun attachLayoutRes(): Int {
        return  R.layout.home_drag_recycle_view_activity
    }

    override fun initData() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun start() {

    }

    override fun initListener() {
        home_drag_recycle_view_filter_bt.setOnClickListener(this)

    }


    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.home_drag_recycle_view_filter_bt->{ showPopWindow()}
        }
    }

    private fun showPopWindow(){
        if (popupWindow == null) {
            val popupView = LayoutInflater.from(this).inflate(R.layout.home_drag_rv_activity_filter_popup, null)


//            val location = IntArray(2)
//            bottomLine.getLocationOnScreen(location)
//            val x = location[0]
//            val y = location[1]
//            val dm = DisplayMetrics()
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm)
            popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,   ViewGroup.LayoutParams.MATCH_PARENT
            )
            popupWindow!!.setBackgroundDrawable(ColorDrawable(0x00000000))
            popupWindow!!.isOutsideTouchable = true
            popupWindow!!.isClippingEnabled = false
            popupWindow!!.isFocusable = true
            //设置PopupWindow消失监听
            popupWindow!!.setOnDismissListener(PopupWindow.OnDismissListener {
                //                    fdm.setChecked(false);
            })
        }
        popupWindow!!.showAsDropDown(home_drag_recycle_view_filter_bt, 0, 0,Gravity.BOTTOM)
    }
}
package com.example.commonlib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.commonlib.R

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import kotlinx.android.synthetic.main.file_path_bg_view_child.*

/**
 * <p>Class: com.example.commonlib.widget.FilePathBackgroundView</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/28/9:22
 */

class FilePathBackgroundView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {
    private var textContent: String ?= ""
    private var textColor: Int
    private var icon: Int
    private lateinit var pathText: TextView
    private lateinit var layerIv: ImageView
    private lateinit var   itemLl : LinearLayout

    private var mOnItemClickListener: OnClickListener? = null

    init {
        initView()
        var ta = context.obtainStyledAttributes(attrs, R.styleable.FilePathBackgroundView)
        textContent = ta.getString(R.styleable.FilePathBackgroundView_pathString)
        textColor =
            ta.getColor(R.styleable.FilePathBackgroundView_pathStringColor, context.resources.getColor(R.color.oil))
        icon = ta.getResourceId(R.styleable.FilePathBackgroundView_layerIcon, R.drawable.layer_arrow)

        ta.recycle()
        setText(textContent)
        setTextColor(textColor)
        setIvSrc(icon)
        itemLl.setOnClickListener { v->
            run {
                mOnItemClickListener!!.itemClick(v)
            }
        }
    }

    private fun initView() {
        val child = View.inflate(context, R.layout.file_path_bg_view_child, null)
        pathText = child.findViewById(R.id.file_path_bg_view_child_tv)
        layerIv = child.findViewById(R.id.file_path_bg_view_child_iv)
        itemLl   =  child.findViewById(R.id.file_path_bg_view_child_ll)
        this.addView(child)
    }

    fun setText(textString: String?) {
        if(textString!=null){
            pathText.text = textString
        }

    }


    fun setTextColor(titleTextColor: Int) {
        pathText.setTextColor(titleTextColor)
    }

    fun setIvSrc(layerIcon: Int) {
        layerIv.setImageDrawable(context.resources.getDrawable(R.drawable.layer_arrow))
    }

    fun setItemClickListener(onClickListener :OnClickListener){
        mOnItemClickListener = onClickListener
    }


    interface OnClickListener{
        fun itemClick(v:View)
    }


}
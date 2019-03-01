package com.example.home

import android.content.Intent
import android.os.Environment
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.base.BaseFragment
import com.example.commonlib.bean.FileEntity
import com.example.commonlib.constant.ARouterUrlConstant
import com.example.commonlib.util.ToastUtil
import com.example.home.drag_rv.DragRecycleViewActivity
import com.example.home.my_file_manager.MyFileManagerActivity
import kotlinx.android.synthetic.main.home_home_fragment.*


/**
 * <p>Class: com.example.home.HomeFragment</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/18/15:07
 */
@Route(path = ARouterUrlConstant.HOME_FRAGMENT)
class HomeFragment : BaseFragment(), View.OnClickListener {


    @JvmField
    @Autowired
    var content: String? = null

    @JvmField
    @Autowired
    var times: Int = 0

    override fun attachLayoutRes(): Int {
        return R.layout.home_home_fragment
    }

    override fun initView(view: View) {
        home_home_fragment_content_tv.text = content + times
    }

    override fun lazyLoad() {
        home_home_fragment_video_tv.setOnClickListener(this)
        home_home_fragment_system_file_manage_tv.setOnClickListener(this)
        home_home_fragment_my_file_manage_tv.setOnClickListener(this)
        home_home_fragment_drag_rv_tv.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.home_home_fragment_video_tv -> startActivity(Intent(activity, VideoActivity::class.java))
            R.id.home_home_fragment_system_file_manage_tv -> openSystemFileManage()
            R.id.home_home_fragment_my_file_manage_tv -> startActivityForResult(
                Intent(
                    activity,
                    MyFileManagerActivity::class.java
                ), 1005
            )
            R.id.home_home_fragment_drag_rv_tv->startActivity(Intent(activity, DragRecycleViewActivity::class.java))
            else -> {

            }
        }
    }

    private fun openSystemFileManage() {
//        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
//            ToastUtil.showShort(context!!.applicationContext, R.string.msg_storage_nosdcard)
//            return
//        }
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 1)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1005) {
            if (resultCode == 55) {
                var bundle = data!!.extras
                var fileEntity = bundle.getSerializable("fileEntity") as FileEntity

                ToastUtil.showShort(activity!!.applicationContext, fileEntity.filePath)
            }
        }
    }

}
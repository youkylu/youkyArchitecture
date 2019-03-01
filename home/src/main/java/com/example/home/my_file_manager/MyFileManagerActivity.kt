package com.example.home.my_file_manager

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.bean.FileEntity
import com.example.commonlib.util.ToastUtil
import com.example.commonlib.widget.FilePathBackgroundView
import com.example.home.R

import kotlinx.android.synthetic.main.home_my_file_manager_activity.*


import java.io.File

import java.util.*


/**
 * <p>Class: com.example.home.my_file_manager.MyFileManagerActivity</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/27/10:52
 */

class MyFileManagerActivity : BaseActivity() {

    private var fileEntityList: ArrayList<FileEntity> = ArrayList()
    private var sdRootPath = ""
    var fileManagerAdapter: FileManagerAdapter = FileManagerAdapter(this, fileEntityList)
//    private lateinit var currentFile: File

    var pathStack = Stack<String>()

    override fun attachLayoutRes(): Int {
        return R.layout.home_my_file_manager_activity
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {

        home_my_file_manager_activity_rv.layoutManager = LinearLayoutManager(this)
        home_my_file_manager_activity_rv.adapter = fileManagerAdapter
        fileManagerAdapter.setOnItemClickListener(object : FileManagerAdapter.OnItemClickListener {

            override fun onFolderItemClick(view: View, position: Int) {
                pathStack.add("/" + fileEntityList[position].fileName)
                findAllFiles(getShowPath(pathStack))
            }

            override fun onFileItemClick(view: View, position: Int) {
                ToastUtil.showShort(this@MyFileManagerActivity, R.string.app_name)

                var selectIntent = Intent()
                var bundle = Bundle()
                bundle.putSerializable("fileEntity", fileEntityList[position])
                selectIntent.putExtras(bundle)
                setResult(55, selectIntent)
                finish()
            }
        })

        sdRootPath = Environment.getExternalStorageDirectory().absolutePath
        if (pathStack.empty()) {
            pathStack.add(Environment.getExternalStorageDirectory().absolutePath)
        }

        findAllFiles(getShowPath(pathStack))
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun start() {

    }

    override fun initListener() {

    }


    /**
     * 查找path地址下所有文件
     * @param path
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun findAllFiles(path: String?) {
        fileEntityList.clear()

        if (path == null || path == "") {
            return
        }
        val fatherFile = File(path)
        val files = fatherFile.listFiles()
        if (files != null && files.isNotEmpty()) {
            for (i in files!!.indices) {
                val entity = FileEntity()
                val isDirectory = files!![i].isDirectory
                if (isDirectory) {
                    entity.fileType = FileEntity.Type.FOLDER
                    //					entity.setFileName(files[i].getPath());
                } else {
                    entity.fileType = FileEntity.Type.FILE
                }
                entity.fileName = files!![i].name.toString()
                entity.filePath = files!![i].absolutePath
                entity.fileSize = files!![i].length().toString()
                fileEntityList.add(entity)
            }
        }
        fileManagerAdapter.notifyDataSetChanged()


        addPathChildView()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        if (TextUtils.equals(getShowPath(pathStack), Environment.getExternalStorageDirectory().absolutePath)) {
            finish()
        } else {
            pathStack.pop()

            findAllFiles(getShowPath(pathStack))
        }
    }

    private fun getShowPath(stringStack: Stack<String>): String {
        var path = ""
        for (str in stringStack) {
            path += str
        }
        return path
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addPathChildView() {
        home_my_file_manager_activity_path_ll.removeAllViews()
        for ((count, str) in pathStack.withIndex()) {
            var filePathBgView = FilePathBackgroundView(this)
            filePathBgView.setText(str)
            filePathBgView.setTextColor(R.color.oil)

            filePathBgView.setItemClickListener(object : FilePathBackgroundView.OnClickListener {
                override fun itemClick(v: View) {
                    doPopAndUpdate(count)
                    findAllFiles(getShowPath(pathStack))
                }
            })
            home_my_file_manager_activity_path_ll.addView(filePathBgView)
        }


    }

    fun doPopAndUpdate(count: Int) {
        if (pathStack.size - 1 != count) {
            pathStack.pop()
            doPopAndUpdate(count)
        }
    }
}

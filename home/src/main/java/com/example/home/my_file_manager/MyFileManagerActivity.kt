package com.example.home.my_file_manager

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.bean.FileEntity
import com.example.commonlib.util.ToastUtil
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
    private lateinit var currentFile: File

    var s = Stack<String>()

    override fun attachLayoutRes(): Int {
        return R.layout.home_my_file_manager_activity
    }

    override fun initData() {
        home_my_file_manager_activity_path_tv.text = "未知"

        home_my_file_manager_activity_rv.layoutManager = LinearLayoutManager(this)
        home_my_file_manager_activity_rv.adapter = fileManagerAdapter
        fileManagerAdapter.setOnItemClickListener(object : FileManagerAdapter.OnItemClickListener {
            override fun onFolderItemClick(view: View, position: Int) {
                sdRootPath = fileEntityList[position].filePath
                currentFile = File(sdRootPath)
                findAllFiles(sdRootPath)
            }

            override fun onFileItemClick(view: View, position: Int) {
                ToastUtil.showShort(this@MyFileManagerActivity, R.string.app_name)

                var selectIntent  = Intent()
                var bundle = Bundle()
                bundle.putSerializable("fileEntity", fileEntityList[position])
                selectIntent.putExtras(bundle )
                setResult( 55, selectIntent)
                finish()
            }
        })



        sdRootPath = Environment.getExternalStorageDirectory().absolutePath
        currentFile = File(sdRootPath)
        findAllFiles(sdRootPath)
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
        home_my_file_manager_activity_path_tv.text = sdRootPath
    }


    override fun onBackPressed() {
        if (TextUtils.equals(sdRootPath, Environment.getExternalStorageDirectory().absolutePath)) {
            finish()
        }else{
            val parentPath = currentFile.parent
            currentFile = File(parentPath)
            sdRootPath = currentFile.absolutePath
            findAllFiles(parentPath)
        }

    }


}

package com.example.home.my_file_manager

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.commonlib.bean.FileEntity
import com.example.home.R


/**
 * <p>Class: com.example.home.my_file_manager.FileManagerAdapter</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/27/11:15
 */

class FileManagerAdapter(
    private var context: Context,
    private var fileEntityList: List<FileEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_DIRECTORY = 1
    private val TYPE_FILE = 2
    private var mOnItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView: View
        return when (viewType) {
            TYPE_DIRECTORY -> {
                rootView =
                    LayoutInflater.from(context)
                        .inflate(R.layout.home_file_manager_adapter_directory_layout, parent, false)
                DirectoryViewHolder(rootView)
            }
            TYPE_FILE -> {
                rootView =
                    LayoutInflater.from(context).inflate(R.layout.home_file_manager_adapter_file_layout, parent, false)
                FileViewHolder(rootView)
            }
            else -> {
                rootView =
                    LayoutInflater.from(context)
                        .inflate(R.layout.home_file_manager_adapter_directory_layout, parent, false)
                DirectoryViewHolder(rootView)
            }
        }
    }

    override fun getItemCount(): Int {
        return fileEntityList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DirectoryViewHolder) {
            holder.folderIconTv.text = fileEntityList[position].fileName

            holder.folderItemConl.setOnClickListener { v ->
                run {
                    mOnItemClickListener!!.onFolderItemClick(v, position)
                }
            }
        } else if (holder is FileViewHolder) {
            holder.fileIconTv.text = fileEntityList[position].fileName

            holder.fileItemConl.setOnClickListener { v->
                kotlin.run {  mOnItemClickListener!!.onFileItemClick(v, position) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (TextUtils.equals(fileEntityList[position].fileType.toString(), FileEntity.Type.FOLDER.toString())) {
            TYPE_DIRECTORY
        } else {
            TYPE_FILE
        }

    }

    inner class DirectoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var folderIconIv: ImageView = itemView.findViewById(R.id.home_file_manager_adapter_directory_layout_iv)
        var folderIconTv: TextView = itemView.findViewById(R.id.home_file_manager_adapter_directory_layout_name_tv)
        var folderItemConl: ConstraintLayout =
            itemView.findViewById(R.id.home_file_manager_adapter_directory_layout_conL)
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var fileIconIv: ImageView = itemView.findViewById(R.id.home_file_manager_adapter_file_layout_iv)
        var fileIconTv: TextView = itemView.findViewById(R.id.home_file_manager_adapter_file_layout_name_tv)
        var fileItemConl: ConstraintLayout = itemView.findViewById(R.id.home_file_manager_adapter_file_layout_conL)
    }


    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }


    interface OnItemClickListener {
        fun onFolderItemClick(view: View, position: Int)
        fun onFileItemClick(view: View, position: Int)
    }


}
package com.example.home.drag_rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.commonlib.bean.SubjectBean
import com.example.home.R

/**
 * <p>Class: com.example.home.drag_rv.DragRvAdapter</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/3/1/10:15
 */

class DragRvAdapter ( private var context:Context, private  var subjectList:ArrayList<SubjectBean>): RecyclerView.Adapter<DragRvAdapter.DragRvViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DragRvViewHolder {
        var rootView: View = LayoutInflater.from(context)
            .inflate(R.layout.home_drag_rv_item_layout, parent, false)

        return  DragRvViewHolder(rootView)
    }

    override fun getItemCount(): Int {
       return subjectList.size
    }

    override fun onBindViewHolder(holder: DragRvViewHolder, position: Int) {
        holder.contentTv.text = subjectList[position].name
    }

    class DragRvViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var contentTv: TextView = itemView.findViewById(R.id.home_drag_rv_item_layout_tv)
    }
}
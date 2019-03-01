package com.example.home.drag_rv

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.commonlib.base.BaseActivity
import com.example.commonlib.bean.SubjectBean
import com.example.commonlib.util.CommonUtil
import com.example.home.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.home_drag_recycle_view_activity.*
import java.util.*
import kotlin.collections.ArrayList

import androidx.viewpager.widget.ViewPager





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

class DragRecycleViewActivity : BaseActivity(), View.OnClickListener {

    private var popupWindow: PopupWindow? = null
    private var subjectList: ArrayList<SubjectBean> = ArrayList()
    private lateinit var myLayoutManager: RecyclerView.LayoutManager
    private lateinit var myRv: RecyclerView
    private lateinit var myDragRvAdapter: DragRvAdapter
    private lateinit var mItemTouchHelper: ItemTouchHelper
    private lateinit var myFragmentManager: FragmentManager
    private var fragmentList = ArrayList<Fragment>()
    private var mTabTitle = ArrayList<String>()
    private  var mySavedInstanceState: Bundle? = null
    private lateinit var  myPagerAdapter :MyFragmentPagerAdapter

    override fun attachLayoutRes(): Int {
        return R.layout.home_drag_recycle_view_activity
    }

    override fun initData() {
        getSubjectData()
    }

    override fun initView(savedInstanceState: Bundle?) {
        myFragmentManager = supportFragmentManager
        mySavedInstanceState = savedInstanceState
        initChildTabLayout(savedInstanceState)
    }

    override fun start() {

    }

    override fun initListener() {
        home_drag_recycle_view_filter_bt.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.home_drag_recycle_view_filter_bt -> {
                showPopWindow()
            }
        }
    }

    private fun showPopWindow() {
        if (popupWindow == null) {
            val popupView = LayoutInflater.from(this).inflate(R.layout.home_drag_rv_activity_filter_popup, null)
            myRv = popupView.findViewById<RecyclerView>(R.id.home_drag_rv_activity_filter_rv)


            val location = IntArray(2)
            home_drag_recycle_view_filter_bt.getLocationOnScreen(location)
            val x = location[0]
            val y = location[1]
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, dm.heightPixels - y - CommonUtil.dp2px(this, 45F)
            )
            myLayoutManager = GridLayoutManager(this, 3)
            myRv.layoutManager = myLayoutManager
            myDragRvAdapter = DragRvAdapter(this, subjectList)
            myRv.adapter = myDragRvAdapter
//            getSubjectData()
            myDragRvAdapter.notifyDataSetChanged()

            mItemTouchHelper = ItemTouchHelper(MyItemTouchHelper())
            mItemTouchHelper.attachToRecyclerView(myRv)

            popupWindow!!.setBackgroundDrawable(ColorDrawable(0x00000000))
            popupWindow!!.isOutsideTouchable = true
            popupWindow!!.isClippingEnabled = false
            popupWindow!!.isFocusable = true
            //设置PopupWindow消失监听
            popupWindow!!.setOnDismissListener(PopupWindow.OnDismissListener {
                updateTabLayout(mySavedInstanceState)
            })
        }
        popupWindow!!.showAsDropDown(home_drag_recycle_view_filter_bt, 0, 0, Gravity.BOTTOM)
    }

    fun getSubjectData() {
        subjectList.clear()
        subjectList.add(SubjectBean("学科"))
        subjectList.add(SubjectBean("疾病"))
        subjectList.add(SubjectBean("热门"))
        subjectList.add(SubjectBean("最新"))
        subjectList.add(SubjectBean("视频"))

    }

    inner class MyItemTouchHelper : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return ItemTouchHelper.Callback.makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                0
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            //得到当拖拽的viewHolder的Position
            val fromPosition = viewHolder.adapterPosition
            //拿到当前拖拽到的item的viewHolder
            val toPosition = target.adapterPosition
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap(subjectList, i, i + 1)
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(subjectList, i, i - 1)
                }
            }
            myDragRvAdapter.notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }

    fun initChildTabLayout(savedInstanceState: Bundle?) {
        fragmentList.clear()
        mTabTitle.clear()
        home_drag_recycle_view_tabL.tabMode = TabLayout.MODE_SCROLLABLE
        home_drag_recycle_view_tabL.removeAllTabs()
        for (i in 0 until subjectList.size) {
            mTabTitle.add(subjectList[i].name)

            if (savedInstanceState != null) {
                if (myFragmentManager.getFragment(savedInstanceState, subjectList[i].name) != null) {
                    fragmentList.add(myFragmentManager.getFragment(savedInstanceState, subjectList[i].name)!!)
                } else {
                    var dragContentFragment = DragContentFragment()
                    var bundle = Bundle()
                    bundle.putString("name", subjectList[i].name)
                    dragContentFragment.arguments = bundle
                    fragmentList.add(dragContentFragment)
                }
            }else {
                var dragContentFragment = DragContentFragment()
                var bundle = Bundle()
                bundle.putString("name", subjectList[i].name)
                dragContentFragment.arguments = bundle
                fragmentList.add(dragContentFragment)
            }
            home_drag_recycle_view_tabL.addTab(home_drag_recycle_view_tabL.newTab())
        }

         myPagerAdapter = MyFragmentPagerAdapter(myFragmentManager, mTabTitle, fragmentList)
        home_drag_recycle_view_viewpager.adapter = myPagerAdapter
        home_drag_recycle_view_tabL.setupWithViewPager(home_drag_recycle_view_viewpager)

//        home_drag_recycle_view_viewpager.offscreenPageLimit = 10
    }

    fun updateTabLayout(savedInstanceState: Bundle?){
        fragmentList.clear()
        mTabTitle.clear()
        for (i in 0 until subjectList.size) {
            mTabTitle.add(subjectList[i].name)

            if (savedInstanceState != null) {
                if (myFragmentManager.getFragment(savedInstanceState, subjectList[i].name) != null) {
                    fragmentList.add(myFragmentManager.getFragment(savedInstanceState, subjectList[i].name)!!)
                } else {
                    var dragContentFragment = DragContentFragment()
                    var bundle = Bundle()
                    bundle.putString("name", subjectList[i].name)
                    dragContentFragment.arguments = bundle
                    fragmentList.add(dragContentFragment)
                }
            }else {
                var dragContentFragment = DragContentFragment()
                var bundle = Bundle()
                bundle.putString("name", subjectList[i].name)
                dragContentFragment.arguments = bundle
                fragmentList.add(dragContentFragment)
            }
//            home_drag_recycle_view_tabL.addTab(home_drag_recycle_view_tabL.newTab())
        }

        myPagerAdapter.notifyDataSetChanged()
    }


    inner class MyFragmentPagerAdapter(
        var fm: FragmentManager,
        var pageTitleList: ArrayList<String>,
        var fragments: ArrayList<Fragment>
    ) :
        FragmentStatePagerAdapter(fm), ViewPager.OnPageChangeListener {

        private var mCurrentPageIndex = 0 // 当前page索引（切换之前）

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {

        }



        override fun getItem(position: Int): Fragment {
         return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return pageTitleList[position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
       for(i in fragmentList.indices ){
           if(fragmentList[i]!=null && fragmentList[i].isAdded){
               myFragmentManager.putFragment( outState, fragmentList[i].arguments!!.get("name") as String,  fragmentList[i])
           }
       }
    }


}
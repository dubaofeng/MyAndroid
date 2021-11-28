package com.dbf.studyandtest.testrefreshlistview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dbf.common.myutils.MyLog
import com.dbf.common.refreshlistview.MyStudyRefreshListView
import com.dbf.common.refreshlistview.OnRefreshListener
import com.dbf.studyandtest.Constant

import com.dbf.studyandtest.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyStudyRefreshListViewFragment : Fragment(), OnRefreshListener {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var refreshListView: MyStudyRefreshListView
    private var arr = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        MyLog.d(Constant.TAG, log = "MyStudyRefreshListViewFragment.onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view: View =
            inflater.inflate(R.layout.fragment_mystudy_refresh_list_view, container, false)
        refreshListView = view.findViewById<MyStudyRefreshListView>(R.id.mystudy_refreshListView)
        for (index in 0..20) {
            arr.add("asdasdasd${index}")
        }
        var adapter =
            activity?.let { //let扩展函数  判断activity是否为null 如果为null则不执行{}，如果不为null则执行{} 其中得it就是指activity
                ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, arr)
            }
        refreshListView?.adapter = adapter
        MyLog.i(TAG, "adapter?.count=${adapter?.count}")
        MyLog.d(Constant.TAG, log = "MyStudyRefreshListViewFragment.onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyLog.d(Constant.TAG, log = "MyStudyRefreshListViewFragment.onViewCreated")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        MyLog.d(Constant.TAG, log = "MyStudyRefreshListViewFragment.onHiddenChanged")
    }

    override fun onStart() {
        super.onStart()
        MyLog.d(Constant.TAG, log = "MyStudyRefreshListViewFragment.onStart")
    }

    override fun onResume() {
        super.onResume()
        MyLog.d(Constant.TAG, log = "MyStudyRefreshListViewFragment.onResume")
    }

    override fun onPause() {
        super.onPause()
        MyLog.d(Constant.TAG, log = "MyStudyRefreshListViewFragment.onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyLog.d(Constant.TAG, log = "MyStudyRefreshListViewFragment.onDestroy")
    }

    companion object {
        val TAG = "MyStudyRefreshListViewFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RefreshListViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = MyStudyRefreshListViewFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    override fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable { kotlin.run { } }, 1000)
    }

    override fun onLoadMoring() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable { kotlin.run { } }, 1000)
    }
}

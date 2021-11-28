package com.dbf.studyandtest.testrefreshlistview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.dbf.common.myutils.MyLog
import com.dbf.common.refreshlistview.OnRefreshListener
import com.dbf.common.refreshlistview.SearchContactsListView
import com.dbf.studyandtest.Constant
import com.dbf.studyandtest.R
import kotlinx.android.synthetic.main.fragment_example_refresh_list_view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StartRefreshListViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExampleRefreshListViewFragment : Fragment(), OnRefreshListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var refreshListView: SearchContactsListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        MyLog.d(Constant.TAG, log = "ExampleRefreshListViewFragment.onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view: View =
            inflater.inflate(R.layout.fragment_example_refresh_list_view, container, false)
        refreshListView = view.findViewById<SearchContactsListView>(R.id.example_refreshListView)
        var adapter =
            activity?.let { //let扩展函数  判断activity是否为null 如果为null则不执行{}，如果不为null则执行{} 其中得it就是指activity
                ArrayAdapter<String>(it,
                    android.R.layout.simple_list_item_1,
                    arrayOf("etwesdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd",
                        "asdasdasd"))
            }
        refreshListView.adapter = adapter
        refreshListView.setOnRefreshListener(this)
        MyLog.d(Constant.TAG, log = "ExampleRefreshListViewFragment.onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyLog.d(Constant.TAG, log = "ExampleRefreshListViewFragment.onViewCreated")
    }
    override fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable { kotlin.run { refreshListView.onRefreshFinish(true, 1) } },
            1000)
    }

    override fun onLoadMoring() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable { kotlin.run { refreshListView.onRefreshFinish(true, 1) } },
            1000)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        MyLog.d(Constant.TAG, log = "ExampleRefreshListViewFragment.onHiddenChanged")
    }

    override fun onStart() {
        super.onStart()
        MyLog.d(Constant.TAG, log = "ExampleRefreshListViewFragment.onStart")
    }

    override fun onResume() {
        super.onResume()
        MyLog.d(Constant.TAG, log = "ExampleRefreshListViewFragment.onResume")
    }

    override fun onPause() {
        super.onPause()
        MyLog.d(Constant.TAG, log = "ExampleRefreshListViewFragment.onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyLog.d(Constant.TAG, log = "ExampleRefreshListViewFragment.onDestroy")
    }

    companion object {
        val TAG = "ExampleRefreshListViewFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StartRefreshListViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ExampleRefreshListViewFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}

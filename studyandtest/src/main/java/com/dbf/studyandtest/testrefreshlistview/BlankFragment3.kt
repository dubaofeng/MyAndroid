package com.dbf.studyandtest.testrefreshlistview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dbf.common.myutils.MyLog
import com.dbf.studyandtest.Constant

import com.dbf.studyandtest.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment3 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        MyLog.d(Constant.TAG, log = "BlankFragment3.onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        MyLog.d(Constant.TAG, log = "BlankFragment3.onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyLog.d(Constant.TAG, log = "BlankFragment3.onViewCreated")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = BlankFragment3().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        MyLog.d(Constant.TAG, log = "BlankFragment3.onHiddenChanged")
    }

    override fun onStart() {
        super.onStart()
        MyLog.d(Constant.TAG, log = "BlankFragment3.onStart")
    }

    override fun onResume() {
        super.onResume()
        MyLog.d(Constant.TAG, log = "BlankFragment3.onResume")
    }

    override fun onPause() {
        super.onPause()
        MyLog.d(Constant.TAG, log = "BlankFragment3.onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyLog.d(Constant.TAG, log = "BlankFragment3.onDestroy")
    }
}

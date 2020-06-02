package com.dbf.studyandtest.testrefreshlistview

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dbf.studyandtest.R
import kotlinx.android.synthetic.main.activity_test_refresh_list_view.*

class TestRefreshListViewActivity : AppCompatActivity() {


    private lateinit var exampleFragment: ExampleRefreshListViewFragment
    private lateinit var mystudyFragment: MyStudyRefreshListViewFragment
    private lateinit var viewPgerAdapter: MyViewpager2Adapter
    private var fragments = arrayListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_refresh_list_view)
        exampleFragment = ExampleRefreshListViewFragment.newInstance("", "")
        mystudyFragment = MyStudyRefreshListViewFragment.newInstance("", "")
        fragments.add(exampleFragment)
        fragments.add(mystudyFragment)
        fragments.add(BlankFragment1.newInstance("", ""))
        fragments.add(BlankFragment2.newInstance("", ""))
        fragments.add(BlankFragment3.newInstance("", ""))
        viewPgerAdapter = MyViewpager2Adapter(this)
        viewPgerAdapter.setFragments(fragments)
        viewpager2.adapter = viewPgerAdapter
    }

}

class MyViewpager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private var fragemnts = arrayListOf<Fragment>()
    fun setFragments(arrayList: ArrayList<Fragment>) {
        this.fragemnts = arrayList
    }

    override fun getItemCount(): Int {
        return fragemnts.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragemnts.get(position)
    }
}
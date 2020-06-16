package com.dbf.studyandtest.myglide

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dbf.studyandtest.R
import com.dbf.studyandtest.databinding.ActivityTestGlideBinding
import kotlinx.android.synthetic.main.activity_test_glide.*

class TestGlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_glide)
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        glideRv.layoutManager = linearLayoutManager
        var adapter = MyAdapter(this)
        var paths = listOf("https://p2.ssl.qhimgs1.com/bdr/326__/t014c514ad3594d06ec.jpg",
            "https://p5.ssl.qhimgs1.com/bdr/326__/t0183085951624797df.jpg",
            "https://p0.ssl.qhimgs1.com/bdr/326__/t014e00ed23f8929746.jpg",
            "https://p0.ssl.qhimgs1.com/bdr/326__/t014e00ed23f8929746.jpg",
            "https://p0.ssl.qhimgs1.com/bdr/326__/t014e00ed23f8929746.jpg",
            "https://p0.ssl.qhimgs1.com/bdr/326__/t014e00ed23f8929746.jpg",
            "https://p0.ssl.qhimgs1.com/bdr/326__/t014e00ed23f8929746.jpg",
            "https://p0.ssl.qhimgs1.com/bdr/326__/t014e00ed23f8929746.jpg")
        adapter.setPaths(paths)
        glideRv.adapter = adapter
    }


}




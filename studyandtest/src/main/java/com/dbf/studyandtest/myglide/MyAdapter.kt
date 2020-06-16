package com.dbf.studyandtest.myglide

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dbf.common.glide.Glide
import com.dbf.studyandtest.R
import com.dbf.studyandtest.databinding.ItemTestglideBinding

/**
 *Created by dbf on 2020/6/11
 *describe:
 */
class MyAdapter(val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var paths: List<String> = listOf()
    public fun setPaths(paths: List<String>): Unit {
        this.paths = paths;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_testglide, parent, false))
    }

    override fun getItemCount(): Int {
        return paths.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, paths[position])
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        init {

        }

        fun bind(context: Context, path: String) {
            Glide.with(context).load(path).into(view.findViewById(R.id.testGlideImgv));
        }
    }
}


package com.example.hilttutorial.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hilttutorial.R
import com.example.hilttutorial.model.Model

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyVh>() {
    private  val TAG = "MyAdapter"
    private lateinit var testList: ArrayList<Model>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_row,parent,false)
        return MyVh(itemView)
    }

    override fun onBindViewHolder(holder: MyVh, position: Int) {
        val curentItem = testList[position]
        holder.iv_image.setImageResource(curentItem.image)
        holder.tv_name.text = curentItem.name
        Log.d(TAG, "onBindViewHolder: "+curentItem.name)
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    fun setData(postList: ArrayList<Model>) {
        this.testList = postList
        notifyDataSetChanged()
    }

    class MyVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name: TextView = itemView.findViewById(R.id.body)
        val iv_image: ImageView = itemView.findViewById(R.id.imageView)

    }


}
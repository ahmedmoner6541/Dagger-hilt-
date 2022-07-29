package com.example.hilttutorial.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmoner.intefaces.OnListnerItemClick
import com.example.hilttutorial.R
import com.example.hilttutorial.databinding.ItemInRecyclerBinding
import com.example.hilttutorial.model.Model

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyVh>() {
    private val TAG = "MyAdapter"
    var clickListner:OnListnerItemClick? = null

    private var testList: List<Model> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        return MyVh(ItemInRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: MyVh, position: Int) {
      val curentItem = testList[position]
        holder.binding.body.text = curentItem.name
        holder.binding.imageView.setImageResource(curentItem.image)

        holder.binding.imageView.setOnClickListener {
            clickListner?.onItemClick(curentItem)
        }
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    fun setData(postList: List<Model>) {
        this.testList = postList
        notifyDataSetChanged()
    }

    class MyVh(val binding: ItemInRecyclerBinding) : RecyclerView.ViewHolder(binding.root)


}
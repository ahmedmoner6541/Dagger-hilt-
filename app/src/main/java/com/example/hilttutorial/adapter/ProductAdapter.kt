package com.example.hilttutorial.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmoner.intefaces.OnListnerItemClick


import com.example.hilttutorial.databinding.ItemProductBinding
import com.example.kotlinproject.data.model.responses.ProductResponse.Product
import com.example.kotlinproject.data.model.responses.ProductResponse.ProductResponse
import com.squareup.picasso.Picasso

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.MyVh>() {
    private val TAG = "MyAdapter"
  var clickListner: OnListnerItemClick? = null

    private var testList: List<Product> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        return MyVh(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: MyVh, position: Int) {

      val curentItem = testList[position]
       holder.bind(curentItem)




    }

    override fun getItemCount(): Int {
        return testList.size
    }

    fun setData(postList: List<Product>) {
        this.testList = postList
        notifyDataSetChanged()
    }

    class MyVh(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {

            Picasso.get().load(product.image).into(binding.imageViewporduct)
           binding.tvProductDiscrirbtion.text = product.description.toString()
            binding.tvDiscount.text = product.discount.toString()
            binding.textViewname.text = product.name
            binding.tvPrice.text = product.price.toString()
            Log.d("TAG", "bind: "+product.name)
        }

    }


}
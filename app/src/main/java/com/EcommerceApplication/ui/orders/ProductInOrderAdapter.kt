package com.EcommerceApplication.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.EcommerceApplication.databinding.ItemProductCartBinding
import com.EcommerceApplication.ui.product.ProductModel
import com.squareup.picasso.Picasso

class ProductInOrderAdapter : RecyclerView.Adapter<ProductInOrderAdapter.ProductInOrderVH>() {
   var productList : List<ProductModel> = arrayListOf()
    inner class ProductInOrderVH(
        val binding : ItemProductCartBinding
    ):RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: ProductModel) {

            binding.price.text = currentItem.price.toString()
            Picasso.get().load(currentItem.image).into(binding.productImageView)
            binding.tvProductName.text=currentItem.name
            binding.pice.text=currentItem.quantity.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInOrderVH {
        return ProductInOrderVH(ItemProductCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    fun setDate(productList:List<ProductModel>){
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductInOrderVH, position: Int) {
        val currentItem = productList[position]
        holder.bind(currentItem)
    }
}
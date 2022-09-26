package com.EcommerceApplication.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
  import com.EcommerceApplication.databinding.ItemProductSearchBinding
import com.EcommerceApplication.ui.product.ProductModel
import com.EcommerceApplication.util.Snackbar
import com.squareup.picasso.Picasso

class SearchAdapter:
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    private   val TAG = "SearchAdapter"
     private var DataList: List<ProductModel> = arrayListOf()
    var onProductClick: OnProductClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ItemProductSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return DataList.size
    }
    fun setData(DataList: List<ProductModel>) {
        this.DataList = DataList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        var currentItem = DataList[position]
        holder.bind(currentItem)
        Log.d(TAG, "onBindViewHoldercurrentItem:${currentItem} ")

        holder.itemView.setOnClickListener {
            if (currentItem.inCart) {
                Log.d(TAG, "binditemview: ")
                holder.itemView.Snackbar("This product has already been added to the cart")
                return@setOnClickListener
            } else {
                onProductClick?.onItemClick(currentItem)
            }
        }

    }
    inner class SearchViewHolder(
        val bindinig:ItemProductSearchBinding
    ):RecyclerView.ViewHolder(bindinig.root) {
        fun bind(currentItem: ProductModel) {
            bindinig.apply {

            bindinig.apply {
                tvProducSearchTName.text = currentItem.name
                Picasso.get().load(currentItem.image).into(productSearchImageView)
                price.text = currentItem.price.toString()
               // oldPrice.text = currentItem.oldPrice.toString()
                desctibtion.text = currentItem.description.toString()
             }
        }
        }

    }
    interface OnProductClick {
        fun onItemClick(product: ProductModel)

    }
}






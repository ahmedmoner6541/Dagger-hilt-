package com.EcommerceApplication.ui.favorite

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.EcommerceApplication.data.remote.response.favorite.getFavorites.DataX
import com.EcommerceApplication.data.remote.response.favorite.getFavorites.Product

import com.EcommerceApplication.databinding.ItemProductBinding
import com.EcommerceApplication.util.visable
import com.squareup.picasso.Picasso
//favoriteProducts: List<String>
class FavoriteProductAdapter( ) :
    RecyclerView.Adapter<FavoriteProductAdapter.MyVh>() {
    private val TAG = "MyAdapter"

    var onProductClick: OnProductClick? = null
    private var productList: List<DataX> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        return MyVh(ItemProductBinding.
        inflate(LayoutInflater.from(parent.context)
            , parent, false))

    }

    override fun onBindViewHolder(holder: MyVh, position: Int) {

        val curentItem = productList[position].product
        Log.d(TAG, "onBindViewHolder: ${curentItem.name}")
        holder.bind(curentItem)
    }

    override fun getItemCount(): Int {
        return productList.size
    }



    fun setData(postList: List<DataX>) {
        this.productList = postList
        notifyDataSetChanged()
    }


    inner class MyVh(val binding: ItemProductBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                chIsFavorite.visable(false)
                tvProductName.text = product.name
                Picasso.get().load(product.image).into(productImageView)

                if (product.discount.toString()=="0"){
                    tvDiscount.visable(false)
                    oldprice.visable(false)
                }
                tvDiscount.text = product.discount.toString()
                oldprice.text = product.oldPrice.toString()
                oldprice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                price.text =  "$ "+product.price.toString()
                itemView.setOnClickListener {
                    onProductClick?.onItemClick(product)
                }
                //ivFavorite.visable(false)

/*
                if (product.in_favorites){
                    ivFavorite.isChecked = true
                }else{
                    ivFavorite.isChecked = false

                }
                ivFavorite.setOnClickListener{
                    if (ivFavorite.isChecked == true){ // is already fav remove it
                        onProductClick?.onFavClickClick(product.id.toString())
                    }
                    if (ivFavorite.isChecked == false){
                        onProductClick?.onFavClickClick(product.id.toString())
                    }


                }*/
                ivDelete.visable(false)
                Log.d("TAG", "bindproduct.name: " + product.name)
                Log.d("TAG", "bindproduct.id: " + product.id)
            }
        }

    }

    interface OnProductClick {
        fun onItemClick(product: Product)
        fun onFavClickClick(productId: String)

    }
}

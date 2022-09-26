package com.EcommerceApplication.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.databinding.ItemProductBinding
import com.EcommerceApplication.util.Snackbar
import com.EcommerceApplication.util.visable
import com.squareup.picasso.Picasso

//favoriteProducts: List<String>
class ProductAdapter :
    RecyclerView.Adapter<ProductAdapter.MyVh>() {
    private val TAG = "MyAdapter"

    var onProductClick: OnProductClick? = null
    private var productList: List<Product> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        return MyVh(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: MyVh, position: Int) {

        val curentItem = productList[position]
        Log.d(TAG, "onBindViewHolder: ${curentItem.in_favorites}")
        holder.bind(curentItem)
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    fun setData(postList: List<Product>) {
        this.productList = postList
        notifyDataSetChanged()
    }


    inner class MyVh(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                tvProductName.text = product.name
                Picasso.get().load(product.image).into(productImageView)

                if (product.discount.toString() == "0") {
                    tvDiscount.visable(false)
                    oldprice.visable(false)
                }
                tvDiscount.text = product.discount.toString()
                oldprice.text = product.old_price.toString()
                oldprice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                price.text = "$ " + product.price.toString()
                itemView.setOnClickListener {
                    if (product.in_cart) {
                        itemView.Snackbar("This product has already been added to the cart")
                        return@setOnClickListener
                    } else {
                        onProductClick?.onItemClick(product)
                    }

                }

                if (product.in_favorites) {
                    chIsFavorite.isChecked = true
                }
                if (!product.in_favorites) {
                    chIsFavorite.isChecked = false
                }

                chIsFavorite.setOnClickListener {
                    if (chIsFavorite.isChecked == true) { // is already fav remove it
                        onProductClick?.onFavClickClick(product.id.toString())
                    }
                    if (chIsFavorite.isChecked == false) {
                        onProductClick?.onFavClickClick(product.id.toString())
                    }
                }

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

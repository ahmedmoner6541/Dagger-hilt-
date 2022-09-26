package com.EcommerceApplication.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.EcommerceApplication.data.remote.response.CartItem
import com.EcommerceApplication.databinding.ItemProductCartBinding
import com.EcommerceApplication.databinding.LayoutCircularLoaderBinding
import com.EcommerceApplication.util.visable
import com.squareup.picasso.Picasso

class CartAdapter : RecyclerView.Adapter<CartAdapter.MyVh>() {
    private val TAG = "MyAdapter"

    var onProductClick: OnProductClick? = null
    var totalQuantity: Int = 1
    var oldQuantity: Int = 1
    private var cartProductItemList: List<CartItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        return MyVh(ItemProductCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

    }

    override fun onBindViewHolder(
        holder: MyVh,
        position: Int)
        {
            val curentItem = cartProductItemList[position]
            holder.bind(curentItem)

        }

    override fun getItemCount(): Int {
        return cartProductItemList.size
    }

    fun setData(postList: List<CartItem>) {
        this.cartProductItemList = postList
        notifyDataSetChanged()
    }
    fun getProductAt(position: Int):CartItem{
        return cartProductItemList[position]
        notifyDataSetChanged()
    }

    inner class MyVh(
        val binding: ItemProductCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            var product = cartItem.product

             binding.apply {


                  oldQuantity = cartItem.quantity
                  totalQuantity = cartItem.quantity

                 binding.addPice.setOnClickListener(View.OnClickListener {
                     if (totalQuantity < 10) {
                         totalQuantity++
                         binding.pice.text = totalQuantity.toString()
                         onProductClick?.upDateQuantityProductInCart(cartItem,totalQuantity)
                     }
                 })
                 binding.removePice.setOnClickListener(View.OnClickListener {
                     if (totalQuantity > 0) {
                         totalQuantity--
                         binding.pice.text = totalQuantity.toString()
                         // onProductClick?.upDateQuantityProductInCart(cartItem,product.id,-1,binding.loaderLayout)
                          onProductClick?.upDateQuantityProductInCart(cartItem, totalQuantity)
                     }
                 })



                tvProductName.text = product.name
                Picasso.get().load(product.image).into(productImageView)
                price.text =product.price.toString() // *****

                pice.text = cartItem.quantity.toString()
                if (product.discount == 0){
                     oldPrice.visable(false)
                }else{
                    oldPrice.visable(true)

                }
                oldPrice.text = product.old_price.toString()
                oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG


                 if (product.in_favorites){
                     binding.chIsFavoriteInCart.isChecked = true
                 }
                 if (!product.in_favorites){
                     chIsFavoriteInCart.isChecked = false
                 }

                 chIsFavoriteInCart.setOnClickListener{
                     if (chIsFavoriteInCart.isChecked == true){ // is already fav remove it
                         onProductClick?.addTofavoriteItemClick(cartItem)
                     }
                     if (chIsFavoriteInCart.isChecked == false){
                         onProductClick?.addTofavoriteItemClick(cartItem)
                     }


                 }


                deleteItemInCart.setOnClickListener { onProductClick?.deleteItemInCartClick(cartItem,cartItem.id , cartItem.quantity, binding.loaderLayout)}



           /*     itemView.setOnClickListener {
                    onProductClick?.onItemClick(cartItem,cartItem.id , cartItem.quantity )
                }
*/
                Log.d("TAG", "bind: " + cartItem.product.name)
            }

        }

    }



    /*
        inner class MyVh(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {

            Picasso.get().load(product.image).into(binding.imageViewporduct)
            binding.tvProductDiscrirbtion.text = product.description.toString()
            binding.tvDiscount.text = product.discount.toString()
            binding.textViewname.text = product.name
            binding.tvPrice.text = product.price.toString()
            Log.d("TAG", "bind: " + product.name)
            itemView.setOnClickListener {

                onProductClick?.onItemClick(product)
            }
        }

    }
     */

    interface OnProductClick {
        //fun onItemClick(cartItem: CartItem,productIdInCart:Int,quantity:Int )

/*
        fun upDateQuantityProductInCart(cartItem: CartItem,productIdInCart:Int,quantity:Int, itemBinding: LayoutCircularLoaderBinding)
*/
        fun upDateQuantityProductInCart(cartItem: CartItem,totalQuantity:Int)

        fun addTofavoriteItemClick(cartItem: CartItem, )
        fun deleteItemInCartClick(cartItem: CartItem,productIdInCart:Int,quantity:Int, itemBinding: LayoutCircularLoaderBinding)
    }


}

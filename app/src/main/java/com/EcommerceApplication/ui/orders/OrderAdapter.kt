package com.EcommerceApplication.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.EcommerceApplication.data.remote.response.order.getOrders.Order
import com.EcommerceApplication.databinding.ItemOrderBinding

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderVH>() {


    private var orderItemList: List<Order> = arrayListOf()
    var onOrderclickListnener:onOrderClickListnener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVH {

        return OrderVH(ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }
    fun setData(orderList: List<Order>) {
        this.orderItemList = orderList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(
        holder: OrderVH,
        position: Int,
    ) {
        val currentItem = orderItemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return orderItemList.size
    }

    inner class OrderVH(
        val binding: ItemOrderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Order) {
            binding.apply {
                tvDateOrderItem.text = currentItem.date
                tvIdOrderItem.text = currentItem.id.toString()
                tvItemOrderState.text = currentItem.status
                tvItemOrderTotalPrice.text = currentItem.total.toString()

                itemView.setOnClickListener { onOrderclickListnener?.onClickItem(currentItem.id) }
            }
        }
    }
    interface onOrderClickListnener{
        fun onClickItem(id: Int)
    }


}
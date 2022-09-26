package com.EcommerceApplication.ui.Adrerss.getAddress

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.EcommerceApplication.data.remote.response.getAdress.Addresses
import com.EcommerceApplication.databinding.ItemAddressBinding
import com.EcommerceApplication.util.visable

class AddressesAdapter() : RecyclerView.Adapter<AddressesAdapter.AddressVH>() {
    private val TAG = "AddressesAdapter"
    var onAddressClick: OnAddressClick? = null
    private var addressesList: List<Addresses> = arrayListOf()


    var selectedItemPos = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressVH {
        return AddressVH(ItemAddressBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: AddressVH, position: Int) {
        var cuttentItem = addressesList[position]
        holder.bind(cuttentItem, position)


    }


    override fun getItemCount(): Int {
        return addressesList.size
        notifyDataSetChanged()
    }

    fun setDate(addressesList: List<Addresses>) {
        this.addressesList = addressesList
        notifyDataSetChanged()
        Log.d(TAG, "bind: addressesList = ${addressesList}")
    }

    inner class AddressVH(val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cuttentItem: Addresses, position: Int) {
            binding.apply {
                // holder.binding.phpone.text = cuttentItem.phone

                addressPersonNameTv.text = cuttentItem.name
                addressCompleteAddressTv.text = cuttentItem.name + " ," + cuttentItem.city + " ," + cuttentItem.details
                addressNoteTv.text = cuttentItem.notes

                if (cuttentItem.notes == null) addressNoteTv.visable(false)

                addressEditBtn.setOnClickListener {
                    onAddressClick?.updateAddress(cuttentItem)
                }
                addressDeleteBtn.setOnClickListener {
                    onAddressClick?.deleteAddress(cuttentItem)
                }

                if (selectedItemPos === position) {
                    itemView.isSelected = true //using selector drawable
                     addressCard.setStrokeColor(Color.GRAY)
                    Log.d(TAG, "bind: etSelected(true)")
                } else {
                    itemView.isSelected = false
                     addressCard.setStrokeColor(Color.WHITE)
                    Log.d(TAG, "bind: etSelected(false)")
                }
                itemView.setOnClickListener { v ->
                    if (selectedItemPos >= 0) notifyItemChanged(selectedItemPos)
                    selectedItemPos = adapterPosition
                    notifyItemChanged(selectedItemPos)
                    onAddressClick?.onAddressSelected(cuttentItem.id.toString())
                }

            }
        }
    }

    interface OnAddressClick {
        fun updateAddress(cuttentItem: Addresses)
        fun deleteAddress(cuttentItem: Addresses)
        fun onAddressSelected(cuttentItem: String)

    }

}
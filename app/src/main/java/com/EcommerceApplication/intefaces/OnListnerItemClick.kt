package com.ahmedmoner.intefaces


import com.EcommerceApplication.data.remote.response.product.ProductResponse



interface OnListnerItemClick {
    fun onItemClick(model: ProductResponse)

}

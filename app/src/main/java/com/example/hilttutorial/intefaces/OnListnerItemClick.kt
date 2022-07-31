package com.ahmedmoner.intefaces


import com.example.kotlinproject.data.model.responses.ProductResponse.ProductResponse



interface OnListnerItemClick {
    fun onItemClick(model: ProductResponse)
}

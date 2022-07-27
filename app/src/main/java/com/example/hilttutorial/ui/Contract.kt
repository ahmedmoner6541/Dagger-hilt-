package com.example.hilttutorial.ui

import com.example.hilttutorial.model.Model

class Contract {
    interface Iview{
       fun onSuccess(result:String);
       fun onfailure(result:String);
    }
    interface Ipresenter{
        fun getList():ArrayList<Model>
    }
}
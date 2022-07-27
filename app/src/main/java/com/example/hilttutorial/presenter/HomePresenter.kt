package com.example.hilttutorial.presenter

import com.example.hilttutorial.model.Model
import com.example.hilttutorial.repository.HomeRepository
import com.example.hilttutorial.ui.Contract

class HomePresenter(
    private val view:Contract.Iview
) :Contract.Ipresenter{
    lateinit var homeRepository: HomeRepository


    override fun getList(): ArrayList<Model> {
        homeRepository = HomeRepository()
        if (homeRepository != null) {
            view.onSuccess("success")
        } else {
            view.onfailure("Failuere")
        }
        return homeRepository.myList()

    }

    }

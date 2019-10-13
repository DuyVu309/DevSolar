package com.example.duyvd1.activities.main

class MainContract{
    interface MainView{
        fun setEmptyText()
    }

    interface MainViewModel{

        fun getListTab() : MutableList<String>

        fun onDestroy()
    }
}
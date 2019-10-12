package com.example.filternew

import androidx.fragment.app.Fragment

interface OnFragmentInteractListener {
    fun onBackFragment()
    fun onOpenFragment(fragment: Fragment)
}
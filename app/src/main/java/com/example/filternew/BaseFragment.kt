package com.example.filternew

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {
    protected var listener: OnFragmentInteractListener? = null

    override fun onStart() {
        super.onStart()
        setOnBackPressed(null) // Add this
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractListener) {
            listener = context
        }
    }

    fun setOnBackPressed(onBackAlternative: (() -> Unit)?) {
        Log.e("HVV1312","Ok set ne")
        (activity as MainActivity).onBackPressAlternative = onBackAlternative
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
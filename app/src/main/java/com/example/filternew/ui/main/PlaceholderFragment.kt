package com.example.filternew.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.filternew.R
import com.example.filternew.ui.news.NewsViewModel
import com.example.filternew.utils.InjectorUtil

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

 //   private lateinit var pageViewModel: PageViewModel
    private lateinit var viewModel: NewsViewModel
    //val viewModel by lazy { ViewModelProviders.of(this, InjectorUtil.getSpecifyNewsFactory()).get(NewsViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, InjectorUtil.getSpecifyNewsFactory(context!!)).get(NewsViewModel::class.java)

//        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
//            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
//        }

        viewModel.tag = "ronaldo"
        viewModel.getNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_news, container, false)
        //val textView: TextView = root.findViewById(R.id.section_label)
//        pageViewModel.text.observe(this, Observer<String> {
//            textView.text = it
//        })

        viewModel.onRefresh()

        viewModel.news.observe(this, Observer {
           // textView.text = it.articles!![0].title
        })



        return root
    }



    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
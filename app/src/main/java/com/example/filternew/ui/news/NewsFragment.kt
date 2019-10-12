package com.example.filternew.ui.news


import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.filternew.BaseFragment

import com.example.filternew.R
import com.example.filternew.data.model.Article
import com.example.filternew.databinding.FragmentNewsBinding
import com.example.filternew.ui.read.ReadFragment
import com.example.filternew.utils.Const
import com.example.filternew.utils.IOnNewsClickListener
import com.example.filternew.utils.InjectorUtil

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : BaseFragment(), IOnNewsClickListener {

    private val viewModel by lazy {
        ViewModelProviders.of(
            activity!!,
            InjectorUtil.getSpecifyNewsFactory()
        ).get(NewsViewModel::class.java)
    }
    private var page = Const.KEY_NEWS_FRAGMENT
    private lateinit var adapter: ArticesAdapter
    private lateinit var binding: FragmentNewsBinding

    override fun newClicked(article: Article) {
        listener?.onOpenFragment(ReadFragment.newInstance(article.url ?: ""))
    }

    override fun newLongClicked(
        article: Article,
        itemView: View, position: Int
    ) {
        showPopupMenu(itemView, article, position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt(Const.ARG_PAGE) ?: page
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.viewModel = viewModel
        adapter = ArticesAdapter()
        adapter.setListener(this)
        binding.adt = adapter
        viewModel.article.observe(this, Observer {
            adapter.setArticleList(it)
        })
        return binding.root
    }

    /* B
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_news, container, false)
        val binding = DataBindingUtil.bind<FragmentNewsBindingImpl>(root)
        binding?.viewModel = viewModel
        adapter = ArticesAdapter()
        adapter.setListener(this)
        binding?.adt = adapter
        viewModel.article.observe(this, Observer {
            adapter.setArticleList(it)
        })
        return root
    }*/
    private var popupMenu: PopupMenu? = null

    private fun showPopupMenu(view: View, article: Article?, i: Int) {
        val wrapper = ContextThemeWrapper(context, R.style.PopupMenu)
        popupMenu = PopupMenu(wrapper, view)
        when (page) {
            Const.KEY_NEWS_FRAGMENT -> {
                return
            }
            Const.KEY_SAVES_FRAGMENT -> {
                popupMenu?.inflate(R.menu.menu_option)
            }
            Const.KEY_FAVORITE_FRAGMENT -> {
                popupMenu?.inflate(R.menu.menu_option_2)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popupMenu?.gravity = Gravity.END
        }
        popupMenu?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete->{
                    deleteNews(article)
                }
                R.id.favorite->{
                    favoriteNews(article)
                }
            }
            true
        }
        popupMenu?.show()
    }

    private fun favoriteNews(article: Article?) {

    }

    private fun deleteNews(article: Article?) {

    }

    companion object {
        @JvmStatic
        fun newInstance(page: Int): NewsFragment {
            return NewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(Const.ARG_PAGE, page)
                }
            }
        }
    }

}

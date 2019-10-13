package com.example.filternew.ui.news


import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : BaseFragment(), IOnNewsClickListener,CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private val viewModel by lazy {
        ViewModelProviders.of(
            activity!!,
            InjectorUtil.getSpecifyNewsFactory(context!!)
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
        if (page == Const.KEY_NEWS_FRAGMENT) {
            insertValue(article)
        } else {
            showPopupMenu(itemView, article, position)
        }
    }

    private fun insertValue(article: Article?) {
        if(article!=null) {
            viewModel.insertArticle(article)
            showError(getString(R.string.e_insert))
        }
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
        when (page) {
            Const.KEY_NEWS_FRAGMENT -> viewModel.article.observe(this, Observer {
                if(it!=null) adapter.setArticleList(it)
            })
            Const.KEY_SAVES_FRAGMENT -> {
                viewModel.getArticle().observe(this, Observer {
                    if(it!=null) adapter.setArticleList(it)
                })
            }
            else -> {

            }
        }
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
                R.id.delete -> {
                    deleteNews(article)
                }
                R.id.favorite -> {
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
        // 2 TH
        if (article != null) {
            viewModel.deleteItem(article)
            showError(getString(R.string.e_delete))
        }
    }

    private fun showError(e:String){
        viewModel.cannotAction.observe(this, Observer {
            if (it) Toast.makeText(context,e,Toast.LENGTH_SHORT).show()
        })
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

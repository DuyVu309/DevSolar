package com.example.duyvd1.activities.main.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duyvd1.R
import com.example.duyvd1.activities.main.MainActivity
import com.example.duyvd1.databinding.FragmentArticlesBinding
import com.example.duyvd1.activities.main.adapter.ArticlesAdapter
import com.example.duyvd1.db.ArticlesDbManager
import com.example.duyvd1.model.Articles
import com.example.duyvd1.utils.AppConsant
import com.example.duyvd1.utils.CommonUtils
import com.example.duyvd1.utils.popup.PopupListItem

class ArticlesFragment : Fragment(), ArticlesFragmentContract.ArticlesView,
    PopupListItem.PopUpListener {

    private var dialog: ProgressDialog? = null
    private lateinit var mBinding: FragmentArticlesBinding
    private var mAdapter: ArticlesAdapter? = null
    private var mViewModel: ArticlesFragmentViewModel? = null
    private var mTextSearch: String = "google-news"

    companion object {
        fun newInstance(): ArticlesFragment {
            val bundle = Bundle()
            return ArticlesFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_articles,
            container,
            false
        )
        return mBinding.llListArticles
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ArticlesFragmentViewModel(context, this)
        initAdapter()
        if (MainActivity.isFirstInit) {
            mViewModel?.getListArticlesFromServer(mTextSearch)
            MainActivity.isFirstInit = false
        }
    }

    private fun initAdapter() {
        mAdapter = ArticlesAdapter(activity!!, mViewModel?.mList, this)
        mBinding.rcvArticles.layoutManager = LinearLayoutManager(activity!!)
        mBinding.rcvArticles.adapter = mAdapter
    }

    override fun onGetDataSuccess() {
        mAdapter?.notifyDataSetChanged()
    }

    override fun onGetDataError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun callApi(text: String) {
        if (context == null || !CommonUtils.isNetworkAvailable(context)) return
        if (text.isEmpty()) mTextSearch = "google-news"
        else mTextSearch = text
        mViewModel?.getListArticlesFromServer(mTextSearch)
    }

    fun getListDataSaved(text: String) {
        mTextSearch = text
        mAdapter?.isTabFavorite = false
        mViewModel?.getListDataSaved(mTextSearch)
    }

    fun getListDataFavorite(text: String) {
        mTextSearch = text
        mAdapter?.isTabFavorite = true
        mViewModel?.getListDataFavorite(mTextSearch)
    }

    override fun onAddToFavorite(articles: Articles?) {
        if (articles != null) {
            articles.isFavorite = AppConsant.TAB_FAVORITE
            ArticlesDbManager.newInstance(context!!).updateToFavorite(articles)
        }
    }

    override fun onRemoveFromListSaved(articles: Articles?) {
        if (articles != null) {
            ArticlesDbManager.newInstance(context!!)
                .deleteArticles(articles, object : ArticlesDbManager.OnDeleteListener {
                    override fun onDeleteDataSuccess(articles: Articles) {
                        mViewModel?.notifyItemDelete(articles)
                    }
                })
        }
    }

    override fun onRemoveFromFavorite(articles: Articles?) {
        if (articles != null) {
            articles.isFavorite = AppConsant.TAB_LIST_SAVE
            ArticlesDbManager.newInstance(context!!)
                .removeFavorite(articles, object : ArticlesDbManager.OnDeleteListener {
                    override fun onDeleteDataSuccess(articles: Articles) {
                        mViewModel?.notifyItemDelete(articles)
                    }
                })
        }
    }

    override fun onDeleteItemSuccess(position: Int) {
        mAdapter?.notifyItemRemoved(position)
        mAdapter?.notifyItemRangeChanged(position, mViewModel?.mList?.size!!)
    }

    override fun showLoading() {
        if (context != null && dialog == null) dialog = CommonUtils.showLoadingDialog(context)
        else if (dialog != null) dialog?.show()
    }

    override fun hideLoading() {
        dialog?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.reset()
    }
}
package com.solarapp.filtersearch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solarapp.filtersearch.R;
import com.solarapp.filtersearch.adapters.AdapterNews;
import com.solarapp.filtersearch.adapters.AdapterNewsSavedAndFavorite;
import com.solarapp.filtersearch.data.News;
import com.solarapp.filtersearch.data.viewmodels.NewsViewModel;
import com.solarapp.filtersearch.databinding.FragmentNewsBinding;
import com.solarapp.filtersearch.dialog.DialogLoading;
import com.solarapp.filtersearch.model.ArticlesItem;
import com.solarapp.filtersearch.utils.Constaint;

import java.util.ArrayList;


public class FragmentNews extends Fragment {
    private DialogLoading mLoadingDialog;
    private NewsViewModel newsViewModel;
    private AdapterNews newsAdapter;
    private static final String KEY_ARGS = "Type";
    private AdapterNewsSavedAndFavorite adapterNewsSavedAndFavorite;
    private FragmentNewsBinding binding;

    public static FragmentNews newInstance(String typeFragment) {
        Bundle args = new Bundle();
        args.putString(KEY_ARGS, typeFragment);
        FragmentNews fragment = new FragmentNews();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingDialog = DialogLoading.newInstance();

        binding.tvNoArticles.setVisibility(View.VISIBLE);
        // bind RecyclerView
        RecyclerView recyclerView = binding.rvNews;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        newsAdapter = new AdapterNews();
        adapterNewsSavedAndFavorite = new AdapterNewsSavedAndFavorite();
        recyclerView.setAdapter(newsAdapter);

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        if (getArguments() != null) {
            String typeFragmnent = getArguments().getString(KEY_ARGS);
            if (typeFragmnent != null && !typeFragmnent.equals(Constaint.TAB_NEWS)) {
                recyclerView.setAdapter(adapterNewsSavedAndFavorite);

                newsViewModel.getNewsSavedAndFavorite().observe(this, news -> {

                    if (news.size() > 0) {
                        binding.tvNoArticles.setVisibility(View.GONE);
                    }
                    if (typeFragmnent.equals(Constaint.TAB_SAVED)) {
                        adapterNewsSavedAndFavorite.setNewsArrayList((ArrayList<News>) news);
                    } else {
                        ArrayList<News> newsFavorite = new ArrayList<>();
                        for (News i : news) {
                            if (i.favorite) {
                                newsFavorite.add(i);
                            }
                        }
                        adapterNewsSavedAndFavorite.setNewsArrayList(newsFavorite);
                    }

                    adapterNewsSavedAndFavorite.onCallback(new AdapterNewsSavedAndFavorite.INewsCallback() {
                        @Override
                        public void onSaveNews(News news) {

                        }

                        @Override
                        public void onDeleteNews(News news) {

                        }
                    });

                    if (mLoadingDialog != null)
                        mLoadingDialog.dismiss();

                });


            }
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getDataFromApi(query);
                //
                if (getActivity() != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getDataFromApi(String query) {
        if (mLoadingDialog != null && getActivity() != null) {
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.show(getActivity().getSupportFragmentManager(), "");
        }
        //đưa dữ liệu vào adapter
        newsViewModel.getAllArticles(query).observe(this,
                articlesItems -> {
                    if (articlesItems.size() > 0) {
                        binding.tvNoArticles.setVisibility(View.GONE);
                    } else {
                        newsAdapter.setArticlesList((ArrayList<ArticlesItem>) articlesItems);
                        newsAdapter.onCallback(item -> {
                            //download

                        });
                    }
                    if (mLoadingDialog != null) mLoadingDialog.dismiss();
                });
    }


}

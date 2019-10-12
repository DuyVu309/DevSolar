package com.solarapp.filtersearch.fragment;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.solarapp.filtersearch.MainActivity;
import com.solarapp.filtersearch.R;
import com.solarapp.filtersearch.adapters.AdapterNews;
import com.solarapp.filtersearch.databinding.FragmentNewsBinding;
import com.solarapp.filtersearch.dialog.DialogLoading;
import com.solarapp.filtersearch.helpers.RetrofitClientInstance;
import com.solarapp.filtersearch.interfaces.GetDataService;
import com.solarapp.filtersearch.models.Response;
import com.solarapp.filtersearch.utils.Validator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FragmentNews extends Fragment {
    private DialogLoading mLoadingDialog;

    public static FragmentNews newInstance() {
        Bundle args = new Bundle();
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
        FragmentNewsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingDialog = DialogLoading.newInstance();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate( R.menu.menu_main, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (Validator.isEmpty(query)){
                    Toast.makeText(getActivity(),getString(R.string.error_key_search_empty),Toast.LENGTH_SHORT).show();
                }else {
                    getDataFromApi(query);
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
        if (mLoadingDialog!=null&&getActivity()!=null){
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.show(getActivity().getSupportFragmentManager(),"Loading");
        }
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Response>> call = service.getSearchResult(query);
        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                try {
                    if (response.body() != null) {
                        mLoadingDialog.dismiss();
                        generateData(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mLoadingDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                try {
                    mLoadingDialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.error_get_data),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    mLoadingDialog.dismiss();
                }
            }
        });
    }

    private void generateData(List<Response> body) {
        AdapterNews myRecyclerViewAdapter = new AdapterNews(body, getActivity());
//        binding.setMyAdapter(myRecyclerViewAdapter);
    }
}

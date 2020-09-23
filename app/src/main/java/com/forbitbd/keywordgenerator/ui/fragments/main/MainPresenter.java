package com.forbitbd.keywordgenerator.ui.fragments.main;

import com.forbitbd.keywordgenerator.api.ApiClient;
import com.forbitbd.keywordgenerator.api.ServiceGenerator;
import com.forbitbd.keywordgenerator.models.TagRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
    }

    @Override
    public boolean validate(TagRequest tagRequest) {
        mView.clearError();
        if(tagRequest.getQuery().equals("")){
            mView.setError("Empty Field is Not Allowed");
            return false;
        }
        return true;
    }

    @Override
    public void submitQuery(TagRequest tagRequest) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getTags(tagRequest)
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if(response.isSuccessful()){
                            mView.processResults(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {

                    }
                });
    }
}

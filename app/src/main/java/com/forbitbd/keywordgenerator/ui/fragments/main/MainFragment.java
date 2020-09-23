package com.forbitbd.keywordgenerator.ui.fragments.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.keywordgenerator.R;
import com.forbitbd.keywordgenerator.ui.tagResult.TagResultActivity;
import com.forbitbd.keywordgenerator.models.TagRequest;
import com.forbitbd.keywordgenerator.utils.AppPreference;
import com.forbitbd.keywordgenerator.utils.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements View.OnClickListener, MainContract.View {

    private TextInputLayout tiQuery;
    private TextInputEditText etQuery;
    private MaterialButton btnGenerate;
    private MainPresenter mPresenter;

    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);


        return view;
    }

    private void initView(View view) {
        tiQuery = view.findViewById(R.id.ti_query);
        etQuery = view.findViewById(R.id.et_query);
        btnGenerate = view.findViewById(R.id.generate);
        btnGenerate.setOnClickListener(this);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onClick(View view) {
        String query = etQuery.getText().toString().trim();
        TagRequest tagRequest = new TagRequest();
        tagRequest.setQuery(query);

        boolean valid = mPresenter.validate(tagRequest);
        if(!valid){
            return;
        }
        mPresenter.submitQuery(tagRequest);
    }

    @Override
    public void clearError() {
        tiQuery.setErrorEnabled(false);
    }

    @Override
    public void setError(String message) {
        tiQuery.setError(message);
        etQuery.requestFocus();
    }

    @Override
    public void processResults(List<String> tags) {
        AppPreference.getInstance(getContext()).increaseCounter();
        Intent intent = new Intent(getContext(), TagResultActivity.class);
        intent.putStringArrayListExtra(Constant.TAG_RESULTS, (ArrayList<String>) tags);
        startActivity(intent);
    }
}
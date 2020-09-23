package com.forbitbd.keywordgenerator.ui.fragments.main;

import com.forbitbd.keywordgenerator.models.TagRequest;

import java.util.List;

public interface MainContract {

    interface View{
        void clearError();
        void setError(String message);
        void processResults(List<String> tags);
    }

    interface Presenter{
        boolean validate(TagRequest tagRequest);
        void submitQuery(TagRequest tagRequest);
    }
}

package com.forbitbd.keywordgenerator.ui.tagResult;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.forbitbd.keywordgenerator.R;
import com.forbitbd.keywordgenerator.ui.main.MainActivity;
import com.forbitbd.keywordgenerator.utils.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class TagResultActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> tags;

    private String values="";
    private ChipGroup chipGroup;


    private MaterialButton btnCopy;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_result);

        tags = getIntent().getStringArrayListExtra(Constant.TAG_RESULTS);

        chipGroup = findViewById(R.id.chipgroup);

        btnCopy = findViewById(R.id.copy);
        btnCopy.setOnClickListener(this);

        for (String x:tags){
            //values = values+x+",\n";
            ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.chip_style);
            final Chip chip = new Chip(newContext);
            chip.setWidth(ChipGroup.LayoutParams.WRAP_CONTENT);
            chip.setHeight(ChipGroup.LayoutParams.WRAP_CONTENT);
            chip.setChecked(true);


            chip.setText(x);

            chipGroup.addView(chip);



        }



        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onClick(View view) {
        if(view==btnCopy){
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copied Text",values);
            manager.setPrimaryClip(clipData);
            Toast.makeText(this, "Copied Text", Toast.LENGTH_SHORT).show();
        }
    }
}
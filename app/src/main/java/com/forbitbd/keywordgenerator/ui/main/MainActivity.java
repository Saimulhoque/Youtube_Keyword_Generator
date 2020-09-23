package com.forbitbd.keywordgenerator.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.forbitbd.keywordgenerator.R;
import com.forbitbd.keywordgenerator.ui.fragments.main.MainFragment;
import com.forbitbd.keywordgenerator.ui.fragments.reward.MyListener;
import com.forbitbd.keywordgenerator.ui.fragments.reward.RewardFragment;
import com.forbitbd.keywordgenerator.utils.AppPreference;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity implements MyListener {

    private RewardedAd rewardedAd;

    private RewardedAdCallback mRewardCallback = new RewardedAdCallback() {
        @Override
        public void onUserEarnedReward(@NonNull com.google.android.gms.ads.rewarded.RewardItem rewardItem) {
        }

        @Override
        public void onRewardedAdOpened() {
            // Ad opened.
        }

        @Override
        public void onRewardedAdClosed() {
            // Ad closed.
            Log.d("HHHHHH","onRewardedAdClosed");
            loadAd();
            AppPreference.getInstance(getApplicationContext()).increaseCounter();
        }

        @Override
        public void onRewardedAdFailedToShow(AdError adError) {
            // Ad failed to display.
        }
    };

    RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
        @Override
        public void onRewardedAdLoaded() {
            // Ad successfully loaded.
            Toast.makeText(MainActivity.this, "onRewardedAdLoaded", Toast.LENGTH_SHORT).show();
            //rewardedAd.show(MainActivity.this, mRewardCallback);
        }

        @Override
        public void onRewardedAdFailedToLoad(LoadAdError adError) {
            // Ad failed to load.
            Toast.makeText(MainActivity.this, "onRewardedAdFailedToLoad", Toast.LENGTH_SHORT).show();
            loadAd();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rewardedAd = new RewardedAd(this,
                "/6499/example/rewarded-video");
        loadAd();
    }

    private void loadAd(){
        rewardedAd.loadAd(new PublisherAdRequest.Builder().build(), adLoadCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HHHHHH","onResume");
        AppPreference preference = AppPreference.getInstance(this);
        if(preference.getCounter() % 10==0){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new RewardFragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new MainFragment()).commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onRewardButtonClick() {
        rewardedAd.show(MainActivity.this, mRewardCallback);
    }
}
package com.enliple.mobmixersdk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.mobwith.MobwithSDK;
import com.mobwith.sdk.R;
import com.mobwith.sdk.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        binding.btnBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BannerViewActivity.class));
            }
        });

        binding.btnArticleBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BannerArticleViewActivity.class));
            }
        });

        binding.btnInterstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InterstitialAdActivity.class));
            }
        });

        binding.btnEnding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EndingAdActivity.class));
            }
        });

        binding.btnNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NativeAdActivity.class));
            }
        });

        binding.btnNativeLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NativeAdLoaderActivity.class));
            }
        });

        binding.btnFreePassView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FreePassAdActivity.class));
            }
        });

        binding.btnRewardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RewardAdActivity.class));
            }
        });

        binding.btnPointAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PointBannerActivity.class));
            }
        });

        binding.btnAdopTest.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AdopTestActivity.class));
        });

    }

    @Override
    protected void initData() {
        /**
         * TODO: SDK 로그 설정
         * 에러 발생 시 로그 추적을 위한 기능
         */
        MobwithSDK.getInstance().setLogPrint(true);
        /**
         * TODO: 레벨플레이 SDK 설정
         * 전달 받은 LevelPlay Appkey 설정
         */
        MobwithSDK.getInstance().setLevelPlayAppKey(this, "200aec285");
        /**
         * TODO: Unity SDK 설정
         * 전달 받은 UnityGameId 설정
         */
        MobwithSDK.getInstance().setUnityGameId(this,"5737823");
        /**
         * TODO: Pangle SDK 설정
         * 전달 받은 PangleAppKey 설정
         */
//        MobwithSDK.getInstance().setPangleAppKey();
        /**
         * TODO: DT Exchange SDK 설정
         * 전달 받은 DT Exchange AppKey 설정
         */
//        MobwithSDK.getInstance().setDTExChangeAppKey("206620");
    }
}
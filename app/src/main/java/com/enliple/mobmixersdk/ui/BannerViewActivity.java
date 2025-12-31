package com.enliple.mobmixersdk.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.enliple.mobmixersdk.utils.LogUtil;
import com.enliple.mobmixersdk.utils.Utils;
import com.mobwith.MobwithSDK;
import com.mobwith.sdk.MobwithBannerView;
import com.mobwith.sdk.callback.iBannerCallback;
import com.mobwith.sdk.models.MobwithAdCategoryModel;
import com.mobwith.sdk.R;
import com.mobwith.sdk.databinding.ActivityBannerViewBinding;

public class BannerViewActivity extends BaseActivity<ActivityBannerViewBinding> {

    private MobwithBannerView adBannerView;
    private String bannerUnitID = "10884457";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_banner_view;
    }

    @Override
    protected void initView() {
        binding.btnNext.setEnabled(false);

        binding.etUnitId.setText(bannerUnitID);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.log("btnLoad");

                Utils.hideKeyboard(BannerViewActivity.this, getCurrentFocus());
                loadAd();
            }
        });

        binding.btnNext.setOnClickListener( v -> {
            if (adBannerView != null) {
                adBannerView.showNextAd();
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void setLoadAd(){
        binding.bannerContainer.removeAllViews();
        adBannerView = new MobwithBannerView(this);
        adBannerView.setBannerUnitId(binding.etUnitId.getText().toString());
        adBannerView.setMobwithAdCategoryModel(new MobwithAdCategoryModel("","","",""));
        adBannerView.setUseHouseBanner(binding.radioButtonUseHouseBanner.isChecked());
        /**
         * TODO: 배너 광고 FullScreenMode 설정
         * Banner 광고가 사용자가 원하는 사이즈에 꽉차게 보이고 싶을 때 설정 (Default : false)
         */
        MobwithSDK.getInstance().setFullScreenMode(binding.radioButtonUseFullScreen.isChecked());

        //하우스 배너를 표시하는 경우
        if (adBannerView.getParent() == null){
            binding.bannerContainer.addView(adBannerView);
        }

        adBannerView.setAdListener(new iBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorCode) {
                if (result){
                    if (adBannerView.getParent() == null){
                        binding.bannerContainer.addView(adBannerView);
                    }
                    binding.btnNext.setEnabled(true);
                } else  {
                    if (!binding.radioButtonUseHouseBanner.isChecked()){
                        clearAdView();
                    }
                }

                binding.btnLoad.setEnabled(true);
            }

            @Override
            public void onAdClicked() {

            }
        });
    }

    private void loadAd(){
        LogUtil.log("loadAd");
        binding.btnNext.setEnabled(false);
        binding.btnLoad.setEnabled(false);

        setLoadAd();

        adBannerView.loadAd();
    }

    private void clearAdView() {
        if (adBannerView != null) {
            adBannerView.destroyAd();
            adBannerView = null;
        }
    }
}

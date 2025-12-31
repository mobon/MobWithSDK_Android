package com.enliple.mobmixersdk.ui;

import android.view.View;

import com.enliple.mobmixersdk.utils.Utils;
import com.mobwith.sdk.MobwithBannerWithArticleView;
import com.mobwith.sdk.R;
import com.mobwith.sdk.callback.iBannerCallback;
import com.mobwith.sdk.callback.iBannerWithArticleCallback;
import com.mobwith.sdk.databinding.ActivityBannerArticleViewBinding;
import com.mobwith.sdk.models.MobwithAdCategoryModel;

public class BannerArticleViewActivity extends BaseActivity<ActivityBannerArticleViewBinding> {

    private MobwithBannerWithArticleView adBannerView;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_banner_article_view;
    }

    @Override
    protected void initView() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(BannerArticleViewActivity.this, getCurrentFocus());
                loadAd();
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adBannerView != null) {
                    adBannerView.showNextAd();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void loadAd(){
        binding.btnNext.setEnabled(false);
        adBannerView = new MobwithBannerWithArticleView(this);
        adBannerView.setBannerUnitId(binding.etUnitId.getText().toString());
        adBannerView.setMobwithAdCategoryModel(new MobwithAdCategoryModel("","A0001","B0001","C0001"));
        adBannerView.setAdListener(new iBannerWithArticleCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorCode) {
                System.out.println("[MobwithBannerWithArticleView] onLoadedAdInfo :  "  +result);
                if (result) {
                    //배너 광고 로딩 성공
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (adBannerView.getParent() == null) {
                                binding.bannerContainer.addView(adBannerView);
                            }
                            System.out.println("[MobwithBannerWithArticleView] Success");
                        }
                    });
                }
                else {
                    System.out.println("[MobwithBannerWithArticleView] error message : " + errorCode);
                    clearAdView();
                }
                binding.btnNext.setEnabled(true);
            }

            @Override
            public void onAdClicked() {
                System.out.println("[MobwithBannerWithArticleView] onAdClicked");
            }

            @Override
            public void onArticleClicked() {
                System.out.println("[MobwithBannerWithArticleView] onArticleClicked");
            }
        });

        adBannerView.loadAd();
    }

    private void clearAdView() {
        if (adBannerView != null) {
            adBannerView.destroyAd();
            adBannerView = null;
        }
    }
}

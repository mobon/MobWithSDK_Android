package com.enliple.mobmixersdk.ui;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.enliple.mobmixersdk.utils.Utils;
import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.MobwithNativeAdView;
import com.mobwith.sdk.R;
import com.mobwith.sdk.callback.iBannerCallback;
import com.mobwith.sdk.databinding.ActivityNativeAdBinding;
import com.mobwith.sdk.models.MobwithAdCategoryModel;

public class NativeAdActivity extends BaseActivity<ActivityNativeAdBinding>{

    private MobwithNativeAdView nativeAdView;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_native_ad;
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.btnAction.setEnabled(false);

        binding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(NativeAdActivity.this, getCurrentFocus());
                loadAd();
            }
        });

        binding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nativeAdView.performAdClicked();
            }
        });
    }

    private void loadAd(){
        binding.btnAction.setEnabled(false);

        nativeAdView = new MobwithNativeAdView(this,
                binding.etUnitId.getText().toString(),
                binding.bannerContainer,
                R.layout.example_native_ad_view,
                R.id.mediaContainerView,
                R.id.imageViewAD,
                R.id.imageViewLogo,
                R.id.textViewTitle,
                R.id.textViewDesc,
                R.id.buttonGo,
                R.id.infoViewLayout,
                R.id.imageViewInfo
        );
        nativeAdView.setMobwithAdCategoryModel(new MobwithAdCategoryModel("","","",""));
        nativeAdView.setAdListener(new iBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            binding.btnAction.setEnabled(true);
                            Toast.makeText(NativeAdActivity.this, "광고 로드 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NativeAdActivity.this, "광고 로드 실패 : " + errorStr, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onAdClicked() {
                LogPrint.d("MobwithNativeAdView - Ad Clicked");
            }
        });

        nativeAdView.setUnitId(binding.etUnitId.getText().toString());
        nativeAdView.loadAd();
    }

}

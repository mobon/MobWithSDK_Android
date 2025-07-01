package com.enliple.mobmixersdk.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.enliple.mobmixersdk.utils.LogUtil;
import com.enliple.mobmixersdk.utils.Utils;
import com.mobwith.sdk.InterstitialDialog;
import com.mobwith.sdk.Key;
import com.mobwith.sdk.R;
import com.mobwith.sdk.callback.iInterstitialCallback;
import com.mobwith.sdk.databinding.ActivityInterstitialAdBinding;
import com.mobwith.sdk.models.MobwithAdCategoryModel;

public class InterstitialAdActivity extends BaseActivity<ActivityInterstitialAdBinding> {

    private InterstitialDialog interstitialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        interstitialDialog = new InterstitialDialog(this)
                .setBackCancel(true);
        interstitialDialog.setMobwithAdCategoryModel(new MobwithAdCategoryModel("", "", "", ""));
        interstitialDialog.setAdListener(new iInterstitialCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorCode) {
                LogUtil.log("onLoadedAdInfo : " + errorCode);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            Toast.makeText(InterstitialAdActivity.this, "광고 로드 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(InterstitialAdActivity.this, "광고 로드 실패 : " + errorCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE keyCode) {
                if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_AD) {
                    LogUtil.log("전면 닫음");
                }
                else if (keyCode == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
                    LogUtil.log("전면 광고 클릭");
                }
            }

            @Override
            public void onOpened() {
                LogUtil.log("전면 오픈");
            }

            @Override
            public void onClosed() {
                LogUtil.log("전면 닫음");
            }

            @Override
            public void onFailOpened() {
                LogUtil.log("전면 열기 실패");
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_interstitial_ad;
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
                Utils.hideKeyboard(InterstitialAdActivity.this, getCurrentFocus());
                loadAd();
            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialDialog.isLoaded()) {
                    interstitialDialog.show();
                } else {
                    Toast.makeText(InterstitialAdActivity.this, "광고가 로딩되지 않았습니다. 광고 로딩후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void loadAd(){
        Key.INTERSTITIAL_TYPE interstitialType;
        if (binding.radioButtonInterstitialNormal.isChecked()){
            interstitialType = Key.INTERSTITIAL_TYPE.NORMAL;
        } else {
            interstitialType = Key.INTERSTITIAL_TYPE.FULL;
        }
        interstitialDialog.setType(interstitialType);
        interstitialDialog.setUnitId(binding.etUnitId.getText().toString());
        interstitialDialog.build();
        interstitialDialog.load();
    }
}

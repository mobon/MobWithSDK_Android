package com.enliple.mobmixersdk.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.enliple.mobmixersdk.utils.LogUtil;
import com.enliple.mobmixersdk.utils.Utils;
import com.mobwith.sdk.EndingDialog;
import com.mobwith.sdk.Key;
import com.mobwith.sdk.callback.iInterstitialCallback;
import com.mobwith.sdk.models.MobwithAdCategoryModel;
import com.mobwith.sdk.R;
import com.mobwith.sdk.databinding.ActivityEndingAdBinding;

public class EndingAdActivity extends BaseActivity<ActivityEndingAdBinding>{

    private EndingDialog endingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        endingDialog = new EndingDialog(this).setBackCancel(false);
        endingDialog.setMobwithAdCategoryModel(new MobwithAdCategoryModel("","","",""));
        endingDialog.setAdListener(new iInterstitialCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorCode) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            Toast.makeText(EndingAdActivity.this, "광고 로드 성공(ENDING)", Toast.LENGTH_SHORT).show();
                            binding.btnShow.setEnabled(true);
                        } else {
                            Toast.makeText(EndingAdActivity.this, "광고 로드 실패(ENDING) : " + errorCode, Toast.LENGTH_SHORT).show();
                        }
                        binding.btnLoad.setEnabled(true);
                    }
                });
            }

            @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE keyCode) {
                if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_AD) {
                    Toast.makeText(EndingAdActivity.this, "엔딩 - 닫음", Toast.LENGTH_SHORT).show();
                    endingDialog.load();
                }
                else if (keyCode == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
                    LogUtil.log("엔딩 - 광고 클릭)");
                }
                else if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_APP) {
                    Toast.makeText(EndingAdActivity.this, "엔딩 - 종료 버튼 클릭", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onOpened() {
                LogUtil.log("엔딩 - 광고 오픈)");
            }

            @Override
            public void onClosed() {
                LogUtil.log("엔딩 - 광고 닫음");
            }

            @Override
            public void onFailOpened() {
                LogUtil.log("엔딩 - 광고 열기 실패");
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ending_ad;
    }

    @Override
    protected void initView() {
        binding.btnShow.setEnabled(false);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(EndingAdActivity.this, getCurrentFocus());
                loadAd();
            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (endingDialog.isLoaded()){
                    endingDialog.show();
                } else {
                    Toast.makeText(EndingAdActivity.this, "광고가 로딩되지 않았습니다. 광고 로딩후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void loadAd(){
        binding.btnLoad.setEnabled(false);
        binding.btnShow.setEnabled(false);
        endingDialog.setUnitId(binding.etUnitId.getText().toString());
        endingDialog.build();
        endingDialog.load();
    }
}

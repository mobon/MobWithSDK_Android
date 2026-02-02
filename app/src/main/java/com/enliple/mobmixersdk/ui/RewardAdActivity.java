package com.enliple.mobmixersdk.ui;

import android.view.View;
import android.widget.Toast;

import com.enliple.mobmixersdk.utils.Utils;
import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.MobwithRewardVideoDialog;
import com.mobwith.sdk.callback.iRewardAdsCallback;
import com.mobwith.sdk.R;
import com.mobwith.sdk.databinding.ActivityRewardAdBinding;

public class RewardAdActivity extends BaseActivity<ActivityRewardAdBinding> {

    private MobwithRewardVideoDialog rewardVideoDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reward_ad;
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
                Utils.hideKeyboard(RewardAdActivity.this, getCurrentFocus());
                loadAd();
            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardVideoDialog.isLoaded()){
                    rewardVideoDialog.show();
                } else {
                    Toast.makeText(RewardAdActivity.this, "광고가 로딩되지 않았습니다. 광고 로딩후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void loadAd(){
        binding.btnLoad.setEnabled(false);
        rewardVideoDialog = new MobwithRewardVideoDialog(this);
        rewardVideoDialog.setUnitId(binding.etUnitId.getText().toString());
        rewardVideoDialog.load();
        rewardVideoDialog.setAdListener(new iRewardAdsCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            Toast.makeText(RewardAdActivity.this, "광고 로드 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RewardAdActivity.this, "광고 로드 실패 : " + errorStr, Toast.LENGTH_SHORT).show();
                        }
                        binding.btnLoad.setEnabled(true);
                    }
                });
            }

            @Override
            public void onAdClicked() {
                LogPrint.d("RewardAdTestActivity", "onClickEvent");
            }

            @Override
            public void onOpened() {
                LogPrint.d("RewardAdTestActivity", "onOpened");
            }

            @Override
            public void onClosed() {
                LogPrint.d("RewardAdTestActivity", "onClosed");
            }

            @Override
            public void onSkip() {
                LogPrint.d("RewardAdTestActivity", "onSkip");
                Toast.makeText(RewardAdActivity.this, "광고 스킵 - 리워드 조건 미충족", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReward() {
                LogPrint.d("RewardAdTestActivity", "onReward");
                Toast.makeText(RewardAdActivity.this, "광고 시청 완료 - 리워드 조건 충족됨", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailOpened() {
                LogPrint.d("RewardAdTestActivity", "onFailOpened");
                Toast.makeText(RewardAdActivity.this, "광고 열기 실패", Toast.LENGTH_SHORT).show();
            }
        });
        rewardVideoDialog.load();
    }
}

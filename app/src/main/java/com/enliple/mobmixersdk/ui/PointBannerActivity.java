package com.enliple.mobmixersdk.ui;

import android.view.View;

import com.enliple.mobmixersdk.utils.Utils;
import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.MobwithMultiPointBannerView;
import com.mobwith.sdk.callback.iMultiPointBannerCallback;
import com.mobwith.sdk.R;
import com.mobwith.sdk.databinding.ActivityPointBannerBinding;

public class PointBannerActivity extends BaseActivity<ActivityPointBannerBinding>{

    /******************
     *  Unit ID list  *
     ******************
     *
     * 포인트 배너 1 -> 10887119
     * 포인트 배너 2 -> 10887120
     * 포인트 배너 3 -> 10887121
     * 포인트 배너 4 -> 10887122
     * 포인트 배너 5 -> 10887123
     *
     */

    private MobwithMultiPointBannerView pointBannerView;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_point_banner;
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
                Utils.hideKeyboard(PointBannerActivity.this, getCurrentFocus());
                loadAd();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pointBannerView != null) {
            pointBannerView.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pointBannerView != null) {
            pointBannerView.restart();
        }
    }

    private void loadAd(){
        binding.btnLoad.setEnabled(false);

        String[] unitIds = binding.etUnitId.getText().toString().split(",");

        pointBannerView = new MobwithMultiPointBannerView(this);
        pointBannerView.setBannerUnitIds(unitIds);
        if (pointBannerView.getParent() == null) {
            binding.bannerContainer.addView(pointBannerView);
        }
        pointBannerView.setAdListener(new iMultiPointBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                LogPrint.d("onLoadedAdInfo: " + result + ", " + errorStr);
                binding.btnLoad.setEnabled(true);
            }

            @Override
            public void onAdClicked(int index, boolean isRewarded) {
                LogPrint.d("onAdClicked: " + index);
                pointBannerView.setRewarded(index);
            }
        });

        pointBannerView.loadAd();
    }
}

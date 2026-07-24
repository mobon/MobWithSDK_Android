package com.enliple.mobmixersdk.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.MobwithSplashBannerView;
import com.mobwith.sdk.R;
import com.mobwith.sdk.utils.MainThreadHandler;

public class SplashActivity extends BaseActivity {

    private static final String DEFAULT_UNIT_ID = "10891162";
    private static final long DISMISS_DELAY_MS = 3000L;
    private static final long TIMEOUT_MS = 4000L;

    private MobwithSplashBannerView splashBannerView;
    private boolean isFullScreen = false;
    private String unitId = DEFAULT_UNIT_ID;
    private boolean isLoaded = false;

    private final Runnable dismissRunnable = new Runnable() {
        @Override
        public void run() {
            finishSafely();
        }
    };

    private final Runnable timeoutRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isLoaded) {
                finishSafely();
            }
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        String intentUnitId = getIntent().getStringExtra("unitId");
        if (!TextUtils.isEmpty(intentUnitId)) {
            unitId = intentUnitId;
        }
        isFullScreen = getIntent().getBooleanExtra("isFullScreen", false);

        splashBannerView = new MobwithSplashBannerView(this, unitId, isFullScreen);
        splashBannerView.setAdListener(new MobwithSplashBannerView.OnSplashAdListener() {
            @Override
            public void onSplashAdDidReceived() {
                LogPrint.d("onSplashAdDidReceived");
                isLoaded = true;
                MainThreadHandler.postDelayed(dismissRunnable, DISMISS_DELAY_MS);
            }

            @Override
            public void onSplashAdFailToReceived(String message) {
                LogPrint.d("onSplashAdFailToReceived");
                if (!isFinishing() && !isDestroyed()) {
                    Toast.makeText(getApplicationContext(),
                            "광고 로드에 실패하였습니다. (" + message + ")",
                            Toast.LENGTH_SHORT).show();
                }
                finishSafely();
            }
        });

        splashBannerView.useFullScreenAd(isFullScreen);
        splashBannerView.setUnitId(unitId);
        splashBannerView.setTimeOutSec(5);
        splashBannerView.loadAd();

        MainThreadHandler.postDelayed(timeoutRunnable, TIMEOUT_MS);
    }

    @Override
    protected void initData() {

    }


    private void finishSafely() {
        if (!isFinishing() && !isDestroyed()) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        MainThreadHandler.removeCallbacks(dismissRunnable);
        MainThreadHandler.removeCallbacks(timeoutRunnable);

        if (splashBannerView != null) {
            splashBannerView.destroyAd();
            splashBannerView = null;
        }
        super.onDestroy();
    }
}
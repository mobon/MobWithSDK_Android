package com.enliple.mobmixersdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.adfit.ads.na.AdFitBizBoardAdTemplateLayout;
import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.MobwithBannerView;
import com.mobwith.sdk.InterstitialDialog;
import com.mobwith.sdk.Key;
import com.mobwith.sdk.callback.iBannerCallback;
import com.mobwith.sdk.callback.iInterstitialCallback;

public class MainActivity extends AppCompatActivity {
    private MobwithBannerView adBannerView;
    private LinearLayout banner_container;

    private Button buttonLoadBannerAD;
    private Button buttonLoadSquareAD;
    private Button buttonNextAD;

    private final String bannerUnitID_300x250 = "YOUR_AD_UNIT_ID";
    private final String bannerUnitID_320x50 = "YOUR_AD_UNIT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner_container = findViewById(R.id.banner_container);
        buttonLoadBannerAD = (Button)findViewById(R.id.buttonLoadBannerAD);
        buttonLoadSquareAD = (Button)findViewById(R.id.buttonLoadSquareAD);
        buttonNextAD = (Button)findViewById(R.id.buttonNextAd);

        LogPrint.setLogPrint(true);

        buttonLoadBannerAD.setOnClickListener( v -> {
            loadAdWith(bannerUnitID_320x50);
        });
        buttonLoadSquareAD.findViewById(R.id.buttonLoadSquareAD).setOnClickListener( v -> {
            loadAdWith(bannerUnitID_300x250);
        });

        buttonNextAD.setOnClickListener( v -> {
            if (adBannerView != null) {
                adBannerView.showNextAd();
            }
        });

        loadAd(bannerUnitID_320x50);

        findViewById(R.id.buttonNativeAdViewTest).setOnClickListener( v -> {
            Intent intent = new Intent(this, NativeAdViewTestActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.buttonNativeAdLoaderTest).setOnClickListener( v -> {
            Intent intent = new Intent(this, NativeAdLoaderTestActivity.class);
            startActivity(intent);
        });


    }


    private void loadAdWith(String defaultUnitID) {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }

        String adUnitId = ((EditText)findViewById(R.id.editTextAdUnitID)).getText().toString();
        if (adUnitId != null && adUnitId.isEmpty()) {
            loadAd(defaultUnitID);
        }
        else {
            loadAd(adUnitId);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    MobwithBannerView getAdBannerView(String bannerUnitID) {
        banner_container.removeAllViews();

        if (adBannerView != null) {
            adBannerView.destroyAd();
        }

        adBannerView = new MobwithBannerView(this).setBannerUnitId(bannerUnitID);
        adBannerView.setBannerUnitId(bannerUnitID);

        return adBannerView;
    }

    void setUpAdBannerViewListener(String bannerUnitID, LinearLayout banner_container) {
        MobwithBannerView bannerView = getAdBannerView(bannerUnitID);
        if (bannerView == null) {
            return ;
        }

        adBannerView.setAdListener(new iBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorcode) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! onLoadedAdInfo :  "  +result);
                if (result) {

                    //배너 광고 로딩 성공
                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (adBannerView.getParent() != null) {
                            ((ViewGroup)adBannerView.getParent()).removeView(adBannerView);
                        }
                        banner_container.addView(adBannerView);

                        buttonNextAD.setEnabled(true);
                    });

                } else {
                    System.out.println("광고실패 : " + errorcode);
                    adBannerView.destroyAd();
                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    buttonLoadBannerAD.setEnabled(true);
                    buttonLoadSquareAD.setEnabled(true);
                });


            }

            @Override
            public void onAdClicked() {

            }

        });
    }


    void loadAd(String bannerUnitID) {
        buttonLoadBannerAD.setEnabled(false);
        buttonLoadSquareAD.setEnabled(false);
        buttonNextAD.setEnabled(false);

        setUpAdBannerViewListener(bannerUnitID, banner_container);
        adBannerView.loadAd();
    }



}
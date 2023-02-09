package com.enliple.mobmixersdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

public class NativeAdViewTestActivity extends AppCompatActivity {

    String adUnitID = "10881787";

    MobwithNativeAdView nativeAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad_view_test);

        findViewById(R.id.buttonBack).setOnClickListener( v -> {
            finish();
        });

        findViewById(R.id.buttonLoadAD).setOnClickListener( v -> {
            InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            View focusedView = getCurrentFocus();
            if (focusedView != null) {
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }

            loadAd();
        });

        ((EditText)findViewById(R.id.editTextBannerUnitID)).setText(adUnitID);

        nativeAdView = new MobwithNativeAdView(this,
                adUnitID,
                (FrameLayout) findViewById(R.id.adview_container),
                R.layout.custom_native_ad_view,
                R.id.mediaContainerView,
                R.id.imageViewAD,
                R.id.imageViewLogo,
                R.id.textViewTitle,
                R.id.textViewDesc,
                R.id.buttonGo,
                R.id.infoViewLayout,
                R.id.imageViewInfo);
    }



    void loadAd() {
        if (nativeAdView == null) {
            return;
        }

        adUnitID = ((EditText)findViewById(R.id.editTextBannerUnitID)).getText().toString();
        nativeAdView.setUnitId(adUnitID);
        nativeAdView.loadAd();
    }
}
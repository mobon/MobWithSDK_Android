package com.enliple.mobmixersdk.ui;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobwith.sdk.HybridBannerBridge;
import com.mobwith.sdk.R;

public class HybridBannerBridgeTestActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_hybrid_banner_bridge_test;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.webView);
        HybridBannerBridge hybridBannerBridge = HybridBannerBridge.attach(this, webView);
        hybridBannerBridge.setOnAdListener(new HybridBannerBridge.OnAdListener() {
            @Override
            public void onAdLoaded() {

            }@Override
            public void onAdLoadedFail(String s) {

            }

            @Override
            public void onAdClicked(@NonNull String url) {

            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hybridBannerBridge.webViewFinishLoad();
            }
        });


        webView.loadUrl("https://dhnasvar.synology.me/link/test.html");
    }

    @Override
    protected void initData() {

    }
}

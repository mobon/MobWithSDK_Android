package com.enliple.mobmixersdk.ui;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.mobwith.sdk.HybridBannerBridge;
import com.mobwith.sdk.R;
import com.mobwith.sdk.databinding.ActivityHybridBannerBridgeTestBinding;

public class HybridBannerBridgeTestActivity extends BaseActivity<ActivityHybridBannerBridgeTestBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_hybrid_banner_bridge_test;
    }

    @Override
    protected void initView() {
        HybridBannerBridge hybridBannerBridge = HybridBannerBridge.attach(this, binding.webView);
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

        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hybridBannerBridge.webViewFinishLoad(true);
            }
        });


        binding.webView.loadUrl("https://dhnasvar.synology.me/link/test.html");
    }

    @Override
    protected void initData() {

    }
}

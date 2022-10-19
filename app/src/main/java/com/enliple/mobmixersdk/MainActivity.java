package com.enliple.mobmixersdk;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.adfit.ads.na.AdFitBizBoardAdTemplateLayout;
import com.mobmixer.sdk.AdBannerView;
import com.mobmixer.sdk.InterstitialDialog;
import com.mobmixer.sdk.Key;
import com.mobmixer.sdk.callback.iBannerCallback;
import com.mobmixer.sdk.callback.iInterstitialCallback;

public class MainActivity extends AppCompatActivity {

    private AdBannerView adBannerView;
    private LinearLayout banner_container;
    private String UNIT_ID = "56_320_50";

    public static AdFitBizBoardAdTemplateLayout bizBoardAdTemplateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InterstitialDialog interstitialDialog = new InterstitialDialog(this).setType(Key.INTERSTITIAL_TYPE.FULL).setUnitId("").build();

        interstitialDialog.setAdListener(new iInterstitialCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                if(result)
                    interstitialDialog.show();
            }

            @Override
            public void onClickEvent() {

            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });


        findViewById(R.id.btn_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interstitialDialog.load();
            }
        });


//        List<String> list = new ArrayList<>(3);
//        list.add("7c0b9682-9fa7-486c-88bb-1c0f0a183aa8");
//        final AppLovinSdk sdk = AppLovinSdk.getInstance( this );
//        sdk.getSettings().setTestDeviceAdvertisingIds(list);


        banner_container = findViewById(R.id.banner_container);

//        AppLovinSdk.getInstance(this).getSettings().setTestDeviceAdvertisingIds(Arrays.asList(SpManager.getString(this, Key.ADID)));
//
//        MaxAdView adView;
//
//        adView = new MaxAdView( "54f3b3c3a1163159", this );
//        adView.setListener(new MaxAdViewAdListener() {
//            @Override
//            public void onAdExpanded(MaxAd ad) {
//                System.out.println("!!!!!!!!!!! onAdExpanded ");
//            }
//
//            @Override
//            public void onAdCollapsed(MaxAd ad) {
//                System.out.println("!!!!!!!!!!! onAdExpanded ");
//            }
//
//            @Override
//            public void onAdLoaded(MaxAd ad) {
//                System.out.println("!!!!!!!!!!! onAdExpanded ");
//            }
//
//            @Override
//            public void onAdDisplayed(MaxAd ad) {
//                System.out.println("!!!!!!!!!!! onAdExpanded ");
//            }
//
//            @Override
//            public void onAdHidden(MaxAd ad) {
//                System.out.println("!!!!!!!!!!! onAdExpanded ");
//            }
//
//            @Override
//            public void onAdClicked(MaxAd ad) {
//                System.out.println("!!!!!!!!!!! onAdExpanded ");
//            }
//
//            @Override
//            public void onAdLoadFailed(String adUnitId, MaxError error) {
//                System.out.println("!!!!!!!!!!! onAdLoadFailed " + error.getMessage());
//            }
//
//            @Override
//            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
//                System.out.println("!!!!!!!!!!! onAdDisplayFailed " + error.getMessage());
//            }
//        });
//
//        // Stretch to the width of the screen for banners to be fully functional
//        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//
//        // Banner height on phones and tablets is 50 and 90, respectively
//        int heightPx = getResources().getDimensionPixelOffset(R.dimen.height_banner);
//
//        adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
//        banner_container.addView( adView );
//
//        // Load the ad
//        System.out.println("!!!!!!!!!!! Load the ad " );
//        adView.loadAd();


        adBannerView = new AdBannerView(this).setBannerUnitId("10881549");
        adBannerView.setAdListener(new iBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorcode) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! onLoadedAdInfo :  "  +result);
                if (result) {
                    //배너 광고 로딩 성공

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                              banner_container.addView(adBannerView);
                          //  AdfitAdapter.nativeAdBinder.bind(bizBoardAdTemplateLayout);
                        }
                    });

                } else {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, errorcode, Toast.LENGTH_SHORT).show();
                        }
                    });

                    System.out.println("광고실패 : " + errorcode);
                    adBannerView.destroyAd();
                }
            }

            @Override
            public void onAdClicked() {

            }

        });

        adBannerView.loadAd();
    }
}
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
import com.mobwith.MobwithSDK;
import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.EndingDialog;
import com.mobwith.sdk.MobwithBannerView;
import com.mobwith.sdk.InterstitialDialog;
import com.mobwith.sdk.Key;
import com.mobwith.sdk.callback.iBannerCallback;
import com.mobwith.sdk.callback.iInterstitialCallback;

public class MainActivity extends AppCompatActivity {
    private MobwithBannerView adBannerView;
    private LinearLayout banner_container;

    private Button buttonLoadBannerAD;
    private Button buttonLoadBanner100AD;
    private Button buttonLoadSquareAD;
    private Button buttonNextAD;

    private final String bannerUnitID_300x250 = "YOUR_AD_UNIT_ID";
    private final String bannerUnitID_320x50 = "YOUR_AD_UNIT_ID";

    private final String bannerUnitID_320x100 = "YOUR_AD_UNIT_ID";
    private final String bannerUnitID_Interstitial = "YOUR_AD_UNIT_ID";


    private InterstitialDialog interstitialDialog;
    private InterstitialDialog interstitialFullDialog;

    private EndingDialog endingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner_container = findViewById(R.id.banner_container);
        buttonLoadBannerAD = (Button)findViewById(R.id.buttonLoadBannerAD);
        buttonLoadBanner100AD = (Button)findViewById(R.id.buttonLoadBanner100AD);
        buttonLoadSquareAD = (Button)findViewById(R.id.buttonLoadSquareAD);
        buttonNextAD = (Button)findViewById(R.id.buttonNextAd);

        MobwithSDK.getInstance().setLogPrint(false);

        interstitialDialog = new InterstitialDialog(this).setBackCancel(true).setType(Key.INTERSTITIAL_TYPE.NORMAL).setUnitId(bannerUnitID_Interstitial).build(); //481299
        interstitialDialog.setAdListener(new iInterstitialCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            Toast.makeText(MainActivity.this, "광고 로드 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "광고 로드 실패 : " + errorStr, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE keyCode) {
                if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_AD) {
                    System.out.println("전면 닫음");
                    interstitialDialog.load();
                }
                else if (keyCode == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
                    System.out.println("전면 광고 클릭");
                }
            }


            @Override
            public void onOpened() {
                System.out.println("전면 오픈");
            }

            @Override
            public void onClosed() {
                System.out.println("전면 닫음");
                interstitialDialog.load();
            }
        });


        interstitialFullDialog = new InterstitialDialog(this).setBackCancel(true).setType(Key.INTERSTITIAL_TYPE.FULL).setUnitId(bannerUnitID_Interstitial).build(); //481299
        interstitialFullDialog.setAdListener(new iInterstitialCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            Toast.makeText(MainActivity.this, "광고 로드 성공(FULL)", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "광고 로드 실패(FULL) : " + errorStr, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE keyCode) {
                if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_AD) {
                    System.out.println("전면 닫음(FULL)");
                    interstitialFullDialog.load();
                }
                else if (keyCode == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
                    System.out.println("전면 광고 클릭(FULL)");
                }
            }


            @Override
            public void onOpened() {
                System.out.println("전면 오픈(FULL)");
            }

            @Override
            public void onClosed() {
                System.out.println("전면 닫음(FULL)");
                interstitialFullDialog.load();
            }
        });


        endingDialog = new EndingDialog(this).setBackCancel(false).setUnitId(bannerUnitID_Interstitial).build();
        endingDialog.setAdListener(new iInterstitialCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            Toast.makeText(MainActivity.this, "광고 로드 성공(ENDING)", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "광고 로드 실패(ENDING) : " + errorStr, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE keyCode) {
                if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_AD) {
                    Toast.makeText(MainActivity.this, "엔딩 - 닫음", Toast.LENGTH_SHORT).show();
                    endingDialog.load();
                }
                else if (keyCode == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
                    System.out.println("엔딩 - 광고 클릭)");
                }
                else if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_APP) {
                    Toast.makeText(MainActivity.this, "엔딩 - 종료 버튼 클릭", Toast.LENGTH_SHORT).show();
                    endingDialog.load();
                }
            }


            @Override
            public void onOpened() {
                System.out.println("엔딩 광고 오픈");
            }

            @Override
            public void onClosed() {
                System.out.println("엔딩 광고 닫음");
                endingDialog.load();
            }
        });


        buttonLoadBannerAD.setOnClickListener( v -> {
            loadAdWith(bannerUnitID_320x50);
        });
        buttonLoadBanner100AD.setOnClickListener( v -> {
            loadAdWith(bannerUnitID_320x100);
        });

        buttonLoadSquareAD.findViewById(R.id.buttonLoadSquareAD).setOnClickListener( v -> {
            loadAdWith(bannerUnitID_300x250);
        });

        buttonNextAD.setOnClickListener( v -> {
            if (adBannerView != null) {
                adBannerView.showNextAd();
            }
        });

        loadAd(bannerUnitID_320x100);

        findViewById(R.id.buttonNativeAdViewTest).setOnClickListener( v -> {
            Intent intent = new Intent(this, NativeAdViewTestActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.buttonNativeAdLoaderTest).setOnClickListener( v -> {
            Intent intent = new Intent(this, NativeAdLoaderTestActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.buttonInterstitail).setOnClickListener( v -> {
            if (interstitialDialog.isLoaded()) {
                interstitialDialog.show();
            }
            else {
                interstitialDialog.load();
            }
        });
        findViewById(R.id.buttonInterstitailFull).setOnClickListener( v -> {
            if (interstitialFullDialog.isLoaded()) {
                interstitialFullDialog.show();
            }
            else {
                interstitialFullDialog.load();
            }
        });
        findViewById(R.id.buttonEdningDialog).setOnClickListener( v -> {
            if (endingDialog.isLoaded()) {
                endingDialog.show();
            }
            else {
                endingDialog.load();
            }
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
                    buttonLoadBanner100AD.setEnabled(true);
                });


            }

            @Override
            public void onAdClicked() {

            }

        });
    }


    void loadAd(String bannerUnitID) {
        buttonLoadBannerAD.setEnabled(false);
        buttonLoadBanner100AD.setEnabled(false);
        buttonLoadSquareAD.setEnabled(false);
        buttonNextAD.setEnabled(false);

        setUpAdBannerViewListener(bannerUnitID, banner_container);
        adBannerView.loadAd();
    }



}
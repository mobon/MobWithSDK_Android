package com.enliple.mobmixersdk;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.adfit.ads.na.AdFitBizBoardAdTemplateLayout;
import com.mobwith.sdk.MobwithBannerView;
import com.mobwith.sdk.InterstitialDialog;
import com.mobwith.sdk.Key;
import com.mobwith.sdk.callback.iBannerCallback;
import com.mobwith.sdk.callback.iInterstitialCallback;

public class MainActivity extends AppCompatActivity {

    private MobwithBannerView adBannerView;
    private LinearLayout banner_container;
    private String UNIT_ID = "Your-Unit-Id";

    public static AdFitBizBoardAdTemplateLayout bizBoardAdTemplateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner_container = findViewById(R.id.banner_container);

        adBannerView = new MobwithBannerView(this).setBannerUnitId(UNIT_ID);
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

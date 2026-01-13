package com.enliple.mobmixersdk.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mobwith.manager.nativeadview.NativeAdViewItemModel;
import com.mobwith.sdk.MobwithFrameBannerView;
import com.mobwith.sdk.R;
import com.mobwith.sdk.callback.iBannerCallback;
import com.mobwith.sdk.databinding.ActivityFrameAdBinding;
import com.mobwith.sdk.models.MobwithAdCategoryModel;
import com.mobwith.sdk.utils.MainThreadHandler;

public class SmartFrameAdActivity extends BaseActivity<ActivityFrameAdBinding> {
    private String mUnitId = "10892178";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_frame_ad;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobwithFrameBannerView bannerView = new MobwithFrameBannerView(this,
                mUnitId,
                binding.adviewContainer,
                new NativeAdViewItemModel(
                        R.layout.custom_native_ad_view,
                        R.id.mediaContainerView,
                        R.id.imageViewAD,
                        R.id.imageViewLogo,
                        R.id.textViewTitle,
                        R.id.textViewDesc,
                        R.id.buttonGo,
                        R.id.infoViewLayout,
                        R.id.imageViewInfo
                ),
                new NativeAdViewItemModel(
                        R.layout.custom_native_ad_view_2,
                        R.id.mediaContainerView,
                        R.id.imageViewAD,
                        R.id.imageViewLogo,
                        R.id.textViewTitle,
                        R.id.textViewDesc,
                        R.id.buttonGo,
                        R.id.infoViewLayout,
                        R.id.imageViewInfo
                ));

        bannerView.setAdListener(new iBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                MainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result){
                            Toast.makeText(SmartFrameAdActivity.this, "광고 로드 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SmartFrameAdActivity.this, "광고 로드 실패 - " + errorStr, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onAdClicked() {
                Toast.makeText(SmartFrameAdActivity.this, "광고 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.editTextBannerUnitID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUnitId = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.buttonLoadAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerView.setUnitID(mUnitId);
                bannerView.setMobwithAdCategoryModel(new MobwithAdCategoryModel("업체코드","대분류","중분류","소분류"));
                bannerView.loadAd();
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}

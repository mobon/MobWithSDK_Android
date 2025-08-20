package com.enliple.mobmixersdk.ui;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.enliple.mobmixersdk.utils.LogUtil;
import com.enliple.mobmixersdk.utils.Utils;
import com.mobwith.manager.SpManager;
import com.mobwith.sdk.MobwithFreePassAdView;
import com.mobwith.sdk.callback.iFreePassBannerCallback;
import com.mobwith.sdk.custom.freepass.MobwithFreePassViewModel;
import com.mobwith.sdk.models.MobwithAdCategoryModel;
import com.mobwith.sdk.R;
import com.mobwith.sdk.databinding.ActivityFreePassAdBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class FreePassAdActivity extends BaseActivity<ActivityFreePassAdBinding>{

    private MobwithFreePassAdView freePassAdView;

    final String[] frames = new String[]{"기본", "프레임 1", "프레임 2", "프레임 3", "프레임 4", "프레임 5", "프레임 6", "프레임 7", "프레임 8", "프레임 9", "프레임 10"};
    int clickedItemId = 0;

    MobwithFreePassViewModel.ViewType normalViewType;
    MobwithFreePassViewModel.ViewType productViewType;

    ArrayList<MobwithFreePassViewModel.ViewType> normalViewTypeList = MobwithFreePassViewModel.ViewType.getNormalViewType();
    ArrayList<MobwithFreePassViewModel.ViewType> productViewTypeList = MobwithFreePassViewModel.ViewType.getProductViewType();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_free_pass_ad;
    }

    @Override
    protected void initView() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        updateLastClickedTime();
        binding.btnFrame.setText("FRAME (기본)");
        binding.btnFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameSelectClick();
            }
        });

        binding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(FreePassAdActivity.this, getCurrentFocus());
                loadAd();
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void loadAd(){
        try {
            String intervalTimeStr = binding.etNoAd.getText().toString();
            if (intervalTimeStr.isEmpty()){
                intervalTimeStr = "0";
            }
            int iIntervalTime = Integer.parseInt(intervalTimeStr);
            SpManager.setInteger(this, "lastClickedTime", iIntervalTime);

            Date lastClickedDate = MobwithFreePassAdView.getLastAdClickedTime(this);
            long lastClickedTimeMillis = lastClickedDate != null ? lastClickedDate.getTime() : 0;
            long currentTimeMillis = System.currentTimeMillis();

            long intervalTime = (long) iIntervalTime * 60 * 1000;        //분 단위로 입력 받음

            if (currentTimeMillis - lastClickedTimeMillis < intervalTime) {
                LogUtil.log("광고 안보임");
                return;
            }
            freePassAdView = new MobwithFreePassAdView(this);
            freePassAdView.setMcName("후후");
            freePassAdView.setMcLogoImage(ContextCompat.getDrawable(this, com.mobwith.sdk.R.drawable.logo_whowho));
            freePassAdView.setMobwithAdCategoryModel(new MobwithAdCategoryModel("","A0001","B0001","C0001"));
            freePassAdView.setBannerUnitId(binding.etUnitId.getText().toString());
            freePassAdView.setFrame(normalViewType, productViewType);
            freePassAdView.setAdListener(new iFreePassBannerCallback() {
                @Override
                public void onLoadedAdInfo(boolean result, String errorStr) {
                    System.out.println("[MobwithFreePassAdViewTest] onLoadedAdInfo :  " + result);
                    if (result) {
                        //배너 광고 로딩 성공
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (freePassAdView.getParent() == null) {
                                    binding.bannerContainer.addView(freePassAdView);
                                }
                                System.out.println("[MobwithFreePassAdViewTest] Success");
                            }
                        });
                    } else {
                        System.out.println("[MobwithFreePassAdViewTest] error message : " + errorStr);
                        clearAdView();
                    }
                }

                @Override
                public void onAdClicked(Date startLandingDate) {
                    updateLastClickedTime();
                }
            });

            freePassAdView.loadAd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearAdView() {
        binding.bannerContainer.removeAllViews();

        if (freePassAdView != null) {
            freePassAdView.destroyAd();
            freePassAdView = null;
        }
    }

    private void updateLastClickedTime() {

        Date lastClickedDate = MobwithFreePassAdView.getLastAdClickedTime(this);
        if (lastClickedDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getDefault());
            String formattedDate = dateFormat.format(lastClickedDate);

            binding.tvClickedTime.setText(formattedDate);
        } else {
            binding.tvClickedTime.setText("없음");
        }
    }

    public void frameSelectClick() {
        int preItemId = clickedItemId;
        new AlertDialog.Builder(this).setTitle("프레임을 선택하세요.").setSingleChoiceItems(frames, clickedItemId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickedItemId = which;
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (clickedItemId == 0){
                    normalViewType = null;
                    productViewType = null;
                    binding.btnFrame.setText("FRAME (기본)");
                } else {
                    normalViewType = normalViewTypeList.get(clickedItemId - 1);
                    productViewType = productViewTypeList.get(clickedItemId - 1);
                    binding.btnFrame.setText("FRAME ("+clickedItemId+")");
                }
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickedItemId = preItemId;
            }
        }).show();

    }
}

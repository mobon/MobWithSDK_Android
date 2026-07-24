package com.enliple.mobmixersdk.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.EndingBannerView;
import com.mobwith.sdk.R;
import com.mobwith.sdk.callback.iBannerCallback;

public class EndingBannerViewTestActivity extends BaseActivity {

    private static final String DEFAULT_UNIT_ID = "10891162";

    private Button buttonLoadAD;
    private Button buttonExit;
    private EditText editTextAdUnitID;

    private EndingBannerDialog bannerDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ending_banner_view_test;
    }

    @Override
    protected void initView() {
        editTextAdUnitID = findViewById(R.id.editTextAdUnitID);
        buttonLoadAD = findViewById(R.id.buttonLoadAD);
        buttonExit = findViewById(R.id.buttonExit);

        buttonLoadAD.setOnClickListener(v -> loadAdWith());
        buttonExit.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {

    }

    private void loadAdWith() {
        hideKeyboard();

        String input = editTextAdUnitID.getText().toString().trim();
        String unitId = input.isEmpty() ? DEFAULT_UNIT_ID : input;

        showBannerDialog(unitId);
    }

    private void showBannerDialog(String unitId) {
        // 이전 다이얼로그가 열려있으면 닫기
        if (bannerDialog != null && bannerDialog.isShowing()) {
            bannerDialog.dismiss();
        }

        buttonLoadAD.setEnabled(false);

        bannerDialog = new EndingBannerDialog(this, unitId)
                .setActionListener(new EndingBannerDialog.OnDialogActionListener() {
                    @Override
                    public void onClose() {
                        LogPrint.d("다이얼로그 닫기");
                        buttonLoadAD.setEnabled(true);
                    }

                    @Override
                    public void onViewMore() {
                        LogPrint.d("자세히보기 클릭");
                        buttonLoadAD.setEnabled(true);
                        Toast.makeText(
                                EndingBannerViewTestActivity.this,
                                "광고 랜딩 페이지로 이동합니다.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        // 광고 로드 실패 시에도 버튼 복구되도록
        bannerDialog.setOnDismissListener(dialog -> buttonLoadAD.setEnabled(true));

        bannerDialog.show();
    }

    private void hideKeyboard() {
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager)
                    getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                    focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        if (bannerDialog != null && bannerDialog.isShowing()) {
            bannerDialog.dismiss();
        }
        super.onDestroy();
    }

    public static class EndingBannerDialog extends Dialog {

        public interface OnDialogActionListener {
            /**
             * 닫기 버튼 클릭
             */
            void onClose();

            /**
             * 자세히보기 버튼 클릭
             */
            void onViewMore();
        }

        private final Activity mActivity;
        private final String mUnitId;
        private OnDialogActionListener mActionListener;

        private EndingBannerView endingBannerView;
        private boolean isAdLoaded = false;

        public EndingBannerDialog(@NonNull Activity activity, @NonNull String unitId) {
            super(activity);
            this.mActivity = activity;
            this.mUnitId = unitId;
        }

        public EndingBannerDialog setActionListener(OnDialogActionListener listener) {
            this.mActionListener = listener;
            return this;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // 타이틀바 제거
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_ending_banner);

            // 다이얼로그 너비 설정 (화면의 90%)
            Window window = getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = (int) (mActivity.getResources().getDisplayMetrics().widthPixels * 0.9f);
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(params);
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }

            // 백그라운드 터치로 닫기 방지
            setCanceledOnTouchOutside(false);
            setCancelable(false);

            setupButtons();
            setupBannerView();
        }

        private void setupButtons() {
            Button btnClose = findViewById(R.id.btn_close);
            Button btnViewMore = findViewById(R.id.btn_view_more);

            btnClose.setOnClickListener(v -> {
                dismiss();
                if (mActionListener != null) mActionListener.onClose();
            });

            btnViewMore.setOnClickListener(v -> {
                // 자세히보기: 광고 클릭 액션과 동일하게 처리
                // 어댑터 내부 클릭 이벤트가 처리되므로 여기선 다이얼로그만 닫음
                dismiss();
                if (mActionListener != null) mActionListener.onViewMore();
            });
        }

        private void setupBannerView() {
            FrameLayout adContainer = findViewById(R.id.fl_ad_container);

            endingBannerView = new EndingBannerView(mActivity).setBannerUnitId(mUnitId);
            endingBannerView.setListener(new iBannerCallback() {
                @Override
                public void onLoadedAdInfo(boolean result, String errorStr) {
                    if (result) {
                        isAdLoaded = true;
                        LogPrint.d("EndingBannerDialog 광고 로드 성공");

                        mActivity.runOnUiThread(() -> {
                            // 광고 뷰를 컨테이너에 추가
                            if (endingBannerView.getParent() == null) {
                                adContainer.addView(endingBannerView,
                                        new FrameLayout.LayoutParams(
                                                FrameLayout.LayoutParams.MATCH_PARENT,
                                                FrameLayout.LayoutParams.MATCH_PARENT));
                            }
                        });
                    } else {
                        LogPrint.d("EndingBannerDialog 광고 로드 실패: " + errorStr);
                        // 광고 로드 실패 시 다이얼로그 닫기
                        mActivity.runOnUiThread(() -> {
                            if (isShowing()) dismiss();
                        });
                    }
                }

                @Override
                public void onAdClicked() {
                    // 광고 뷰 자체 클릭 → 다이얼로그 닫기
                    dismiss();
                    if (mActionListener != null) mActionListener.onViewMore();
                }
            });

            endingBannerView.loadAd();
        }

        @Override
        public void dismiss() {
            if (endingBannerView != null) {
                endingBannerView.destroy();
                endingBannerView = null;
            }
            super.dismiss();
        }
    }
}
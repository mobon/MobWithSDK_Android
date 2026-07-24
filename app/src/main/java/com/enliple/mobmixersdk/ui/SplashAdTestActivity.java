package com.enliple.mobmixersdk.ui;

import android.content.Intent;
import android.widget.EditText;
import android.widget.RadioButton;

import com.mobwith.sdk.R;

public class SplashAdTestActivity extends BaseActivity {

    private RadioButton fullRadioBtn;
    private EditText editTextAdUnitID;

    private String unitID = "10891162";   // 10887209, 10886135 10891160 10893004

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash_ad;
    }

    @Override
    protected void initView() {
        fullRadioBtn = findViewById(R.id.radioButtonFull);
        editTextAdUnitID = findViewById(R.id.editTextAdUnitID);

        findViewById(R.id.buttonExit).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.buttonLoadAD).setOnClickListener(v -> {
            loadAdWith();
        });
    }

    @Override
    protected void initData() {

    }

    private void loadAdWith() {
        if (!editTextAdUnitID.getText().toString().isEmpty()){
            unitID = editTextAdUnitID.getText().toString();
        }
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("unitId", unitID);
        intent.putExtra("isFullScreen", fullRadioBtn.isChecked());
        startActivity(intent);
    }
}

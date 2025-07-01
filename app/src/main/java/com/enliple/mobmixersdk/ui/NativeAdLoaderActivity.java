package com.enliple.mobmixersdk.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.R;
import com.mobwith.sdk.databinding.ActivityNativeAdLoaderBinding;
import com.mobwith.sdk.loader.MobwithNativeAdLoader;
import com.mobwith.sdk.loader.iNativeBannerCallback;
import com.mobwith.sdk.models.MobwithAdCategoryModel;

public class NativeAdLoaderActivity extends BaseActivity<ActivityNativeAdLoaderBinding>{

    /*
     * 1. MW_쿠차_APP_네이티브_320*250_AOS_테스트1 (모비위드) 10882166
     * 2. MW_쿠차_APP_네이티브_320*250_AOS_테스트2 (모비위드) 10882167
     * 3. MW_쿠차_APP_네이티브_320*250_AOS_테스트3 (모비위드) 10882168
     * 4. MW_쿠차_APP_네이티브_320*250_AOS_테스트4 (모비위드) 10882169
     * */
    String[] adUnitIDs = new String[] {
            "10882166",
            "10882167",
            "10882168",
            "10882169"
    };

    MobwithNativeAdLoader adLoader;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_native_ad_loader;
    }

    @Override
    protected void initView() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        RecyclerView recyclerView = binding.nativeLoaderRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SampleAdapter());
    }

    @Override
    protected void initData() {

    }

    @Override
    public void finish() {
        super.finish();
        getAdLoader().destroyAllAd();
    }

    private MobwithNativeAdLoader getAdLoader() {

        if (adLoader == null) {
            adLoader = new MobwithNativeAdLoader(this, adUnitIDs);
            adLoader.setMobwithAdCategoryModel(new MobwithAdCategoryModel("","A0001","B0001","C0001"));
            adLoader.setAdListener(new iNativeBannerCallback() {

                @Override
                public void onLoadedAd(int index, boolean result, String errorStr) {
                    LogPrint.d("[AD Loader] callback : [index] " + index + " [result] " + result + " [msg] " + errorStr);
                    if (result) {
                        RecyclerView recyclerView = binding.nativeLoaderRecycler;
                        assert recyclerView.getAdapter() != null;
                        recyclerView.getAdapter().notifyItemChanged(index);
                    }
                }

                @Override
                public void onAdClicked(int index) {

                }
            });

            adLoader.setNativeADView(this,
                    R.layout.example_native_ad_view,
                    R.id.mediaContainerView,
                    R.id.imageViewAD,
                    R.id.imageViewLogo,
                    R.id.textViewTitle,
                    R.id.textViewDesc,
                    R.id.buttonGo,
                    R.id.infoViewLayout,
                    R.id.imageViewInfo);
        }

        return adLoader;
    }
    class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView1 ;
            FrameLayout frameLayout;

            int index;

            ViewHolder(View itemView) {
                super(itemView) ;

                textView1 = itemView.findViewById(R.id.textViewTitle) ;
                frameLayout = itemView.findViewById(R.id.adview_container);
            }

            public void removeAdViews() {
                frameLayout.removeAllViews();
                frameLayout.setVisibility(View.GONE);

            }

            public void showAdViews(int index) {
                this.index = index;

                frameLayout.removeAllViews();
                frameLayout.setVisibility(View.VISIBLE);

                ViewGroup adView = getAdLoader().loadAD(NativeAdLoaderActivity.this, index);
                if (adView != null && getAdLoader().isLoadedAd(index) ) {
                    textView1.setBackgroundColor(0x00000000);
                    frameLayout.addView(adView);
                }
                else {
                    LogPrint.d("[AD Loader] Not Loaded ADs...");
                }
            }
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext() ;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            View view = inflater.inflate(R.layout.sample_recyclerview_item, parent, false) ;
            SampleAdapter.ViewHolder vh = new SampleAdapter.ViewHolder(view) ;

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.index = position;
            if (position % 5 == 4) {
                holder.textView1.setText("show ad at " + position) ;
                holder.textView1.setBackgroundColor(0x1f000000);
                holder.showAdViews(position);

            }
            else {
                holder.removeAdViews();

                String text = "Position is " + position;
                holder.textView1.setText(text);
                holder.textView1.setBackgroundColor(0x00000000);
            }

        }

        @Override
        public int getItemCount() {
            return 500;
        }
    }
}

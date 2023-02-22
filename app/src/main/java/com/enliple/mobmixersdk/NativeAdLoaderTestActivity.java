package com.enliple.mobmixersdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mobwith.manager.LogPrint;
import com.mobwith.sdk.loader.MobwithNativeAdLoader;
import com.mobwith.sdk.loader.iNativeBannerCallback;



public class NativeAdLoaderTestActivity extends AppCompatActivity {


    String[] adUnitIDs = new String[] {
            "10882166",
            "10882167",
            "10882168",
            "10882169"
    };

    MobwithNativeAdLoader adLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad_loader_test);

        findViewById(R.id.buttonBack).setOnClickListener( v -> {
            finish();
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SampleAdapter());

    }

    public MobwithNativeAdLoader getAdLoader() {

        if (adLoader == null) {
            adLoader = new MobwithNativeAdLoader(this, adUnitIDs);
            adLoader.setAdListener(new iNativeBannerCallback() {

                @Override
                public void onLoadedAd(int index, boolean result, String errorStr) {
                    LogPrint.d("[AD Loader] callback : [index] " + index + " [result] " + result + " [msg] " + errorStr);
                    if (result) {
                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//                        recyclerView.getAdapter().notifyDataSetChanged();
                        recyclerView.getAdapter().notifyItemChanged(index);
                    }
                }

                @Override
                public void onAdClicked(int index) {

                }
            });

            adLoader.setNativeADView(this,
                    R.layout.custom_native_ad_view,
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



    void loadAd() {
        ViewGroup adView = getAdLoader().loadAD(NativeAdLoaderTestActivity.this, 0);
        ((FrameLayout) findViewById(R.id.adview_container)).addView(adView);
    }


    @Override
    public void finish() {
        super.finish();

        getAdLoader().destroyAllAd();
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

                ViewGroup adView = getAdLoader().loadAD(NativeAdLoaderTestActivity.this, index);
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

//        @Override
//        public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
//            super.onViewDetachedFromWindow(holder);
//            getAdLoader().detachADView(holder.index);
//        }

        @Override
        public int getItemCount() {
            return 500;
        }
    }


}
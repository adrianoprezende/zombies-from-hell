package com.adrianoprezende.zombies.advertising;

import android.content.Context;

import com.adrianoprezende.zombies.main.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by adrianopr on 01/07/2015.
 */
public class AdManager {

    final static String TEST_DEVICE_ID = "01546C0E0900800A";

    private static AdManager mInstance = null;

    private InterstitialAd mInterstitialAd;

    private AdManager() {}
    public static AdManager getInstance() {
        if(mInstance == null)
        {
            mInstance = new AdManager();
        }
        return mInstance;
    }

    public void createAd(Context context) {
        // Create an ad.
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.banner_ad_unit_id));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID).build();

        // Load the interstitial ad.
        mInterstitialAd.loadAd(adRequest);
    }

    public InterstitialAd getAd() {
        return mInterstitialAd;
    }
}

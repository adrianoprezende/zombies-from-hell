package com.adrianoprezende.zombies.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.Toast;

import com.adrianoprezende.zombies.advertising.AdManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * EndGameView class.
 * @author Adriano Pereira Rezende
 */
public class EndGameView extends Activity {

	InterstitialAd mInterstitialAd;

	private boolean timerLock = false;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.gameover);
    	
    	SoundManager.getInstance();
 	   	SoundManager.playSound(SoundManager.GAMEOVER_SHOUT_FX);

		mInterstitialAd = AdManager.getInstance().getAd();

		if(mInterstitialAd != null) {
			if (mInterstitialAd.isLoaded()) {
				mInterstitialAd.show();
			}
		}

		/*
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
		requestNewInterstitial();

		mInterstitialAd.setAdListener(new AdListener() {

			@Override
			public void onAdLoaded() {
				if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
					mInterstitialAd.show();
				}
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				finishViewAndCallConquestsView();
			}

			@Override
			public void onAdClosed() {
				finishViewAndCallConquestsView();
			}
		});

		/*
		if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		} else {
			Toast.makeText(this, "****Ad did not load", Toast.LENGTH_SHORT).show();
		}
		*/

//    	Handler handler = null;
//	    handler = new Handler(); 
//	    handler.post(new Runnable(){ 
//	       public void run(){
//	    	   
//	       }
//	    });


    	new CountDownTimer(2000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onFinish() {
				timerLock = true;
			}
		}.start();

    	
	}

	private void finishViewAndCallConquestsView() {
		if(timerLock) {
			Intent conquestsViewIntent = new Intent(EndGameView.this, ConquestsView.class);
			setResult(1, conquestsViewIntent);
			finish();
		}
	}

	/*
	private void requestNewInterstitial() {
		//TelephonyManager tm = (TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		//String deviceId = tm.getDeviceId();
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("01546C0E0900800A")
				.build();

		mInterstitialAd.loadAd(adRequest);
	}
	*/
    
    @Override
    protected void onPause() {
    	SoundManager.stopMusic();
    	setResult(3);
    	super.onPause();
    }
    
//  public void onResume() {
//	super.onResume();
//
//}
    
    @Override
    protected void onStop() {
    	super.onStop();
    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    }

    @Override
    public void onDestroy() {
    	super.onDestroy();
    }

    @Override
    public void onBackPressed() {
		finishViewAndCallConquestsView();
    }

	public boolean onTouchEvent(MotionEvent event) {
		finishViewAndCallConquestsView();
		return true;
	}

	/*
    public boolean onTouchEvent(MotionEvent event) {
    	if(event.getAction() == MotionEvent.ACTION_DOWN) {
			if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
				mInterstitialAd.show();
			} else {
				finishViewAndCallConquestsView();
			}

    	}

    	return false;
    }
    */
    
   
}

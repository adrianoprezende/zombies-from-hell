package com.powerup.zombie.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * EndGameView class.
 * @author Adriano Pereira Rezende
 */
public class EndGameView extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.gameover);
    	
    	SoundManager.getInstance();
 	   	SoundManager.playSound(SoundManager.GAMEOVER_SHOUT_FX);
    	
    	
//    	Handler handler = null;
//	    handler = new Handler(); 
//	    handler.post(new Runnable(){ 
//	       public void run(){
//	    	   
//	       }
//	    });

    	new CountDownTimer(3000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onFinish() {
				Intent conquestsViewIntent = new Intent(EndGameView.this, ConquestsView.class);
				setResult(1,conquestsViewIntent);
				finish();
			}
		}.start();
        
    	
	}
    
    
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
    	Intent conquestsViewIntent = new Intent(EndGameView.this, ConquestsView.class);
		setResult(1,conquestsViewIntent);
		finish();
    }
    
//    public boolean onTouchEvent(MotionEvent event) {
//    	if(event.getAction() == MotionEvent.ACTION_DOWN) {
//    		Intent rankingGameIntent = new Intent(EndGameView.this, RankingView.class);
//    		setResult(1,rankingGameIntent);
//    		finish();
//    	}
//    	
//    	return false;
//    }
    
   
}

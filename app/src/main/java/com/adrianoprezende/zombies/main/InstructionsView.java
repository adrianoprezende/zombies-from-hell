package com.adrianoprezende.zombies.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * InstructionsView class.
 * @author Adriano Pereira Rezende
 */
public class InstructionsView extends Activity {
	
	//private int[] instructionsImages = new int[]{R.drawable.manual1};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        SoundManager.getInstance();
        boolean loop = true;
        SoundManager.playMusicBG(SoundManager.INSTRUCTIONS_MUSIC_BGM,loop);
        setContentView(R.layout.instructions);
        
        new CountDownTimer(7000, 7000) {
        	LinearLayout layout = (LinearLayout)findViewById(R.id.instructions);

			@Override
			public void onTick(long millisUntilFinished) {
				//layout.setBackgroundResource(instructionsImages[0]);
			}

			@Override
			public void onFinish() {
				Intent startGameIntent = new Intent(InstructionsView.this, StartGameView.class);
				setResult(1,startGameIntent);
				finish();
				
			}
		}.start();
    }
    
    
    @Override
    protected void onPause() {
    	SoundManager.stopMusic();
    	setResult(3);
    	InstructionsView.this.finish();
    	
    	super.onPause();
    }
    
    
//    @Override
//    public void onStop() {
//    	super.onStop();
//    	SoundManager.stopMusic();
//    	SoundManager.cleanup();
//    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    }
    
    public void onDestroy() {
    	super.onDestroy();
    	this.finish();
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	if(event.getAction() == MotionEvent.ACTION_DOWN) {
    		Intent startGameIntent = new Intent(InstructionsView.this, StartGameView.class);
			setResult(1,startGameIntent);
			finish();
    	}
    	
    	return false;
    }
    
    @Override
    public void onBackPressed() {
    	InstructionsView.this.setResult(3);
    	super.onBackPressed();
    }

}
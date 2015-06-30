package com.adrianoprezende.zombies.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;

/**
 * MenuView class.
 * @author Adriano Pereira Rezende
 */
public class MenuView extends Activity {
	static Dialog dialog;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();
    	
        boolean loop = true;
        SoundManager.playMusicBG(SoundManager.INTRO_MUSIC_BGM,loop);
        
        /**********************************************
        dialog = new Dialog(MenuView.this);

    	dialog.setContentView(R.layout.custom_dialog);
    	dialog.setTitle("CAUTION!");

    	TextView text = (TextView) dialog.findViewById(R.id.PauseText);
    	text.setText("This game contains strong scenes of gore and blood.");
    	ImageView image = (ImageView) dialog.findViewById(R.id.PauseImage);
    	image.setImageResource(R.drawable.frans_hurt);
    	dialog.setCancelable(false);
    	
		dialog.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						MenuView.this.setVisible(true);
						
					}
				});
    	
    	
    	if (savedInstanceState == null) {
            // We were just launched -- set up a new game
    		
    		this.setVisible(false);
    		
    		new CountDownTimer(5000, 1000) {

       	     public void onTick(long millisUntilFinished) {
       	    	 dialog.show();
       	     }

       	     public void onFinish() {
       	    	 dialog.cancel();
         		TableLayout table = (TableLayout)findViewById(R.id.menu_buttons_group);
      	    	table.setVisibility(View.INVISIBLE);
      	    	
      	    	
      	    	new CountDownTimer(5000, 1000) {

      	      	     public void onTick(long millisUntilFinished) {
      	      	    	 //
      	      	     }

      	      	     public void onFinish() {
      	      	    	 
      	      	    	TableLayout table = (TableLayout)findViewById(R.id.menu_buttons_group);
      	      	    	table.setVisibility(View.VISIBLE);
      	      	    	 
      	      	     }
      	      	  }.start();
      	    	
      	    	
      	    	
      	    	

       	     }
       	  }.start();
    	
    		
        } 
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	Handler handler = null;
//        handler = new Handler(); 
//        handler.postDelayed(new Runnable(){ 
//             public void run(){
//                 dialog.cancel();
//                 dialog.dismiss();
//             }
//        }, 5000);
        /**********************************************/
        
    	setContentView(R.layout.main);
    	
    	TableLayout menuButtons = (TableLayout)findViewById(R.id.menu_buttons_group);
	    menuButtons.setVisibility(View.INVISIBLE);
    	
    	Button StartGameButton = (Button)findViewById(R.id.StartGame);
	    	StartGameButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent StartGameIntent = new Intent(MenuView.this, InstructionsView.class);
					startActivityForResult(StartGameIntent, 1);
					
				}
			});
	    	
	    	
	    	Button ExitGameButton = (Button)findViewById(R.id.ExitGame);
	    	ExitGameButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
			    	finish();
					//SoundManager.stopMusic();
					//SoundManager.cleanup();
					//System.exit(RESULT_OK);
				}
			});
	    	
	    	Button RankingButton = (Button)findViewById(R.id.RankingButton);
	    	RankingButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent RankingIntent = new Intent(MenuView.this, RankingView.class);
					startActivityForResult(RankingIntent, 3);
				}
			});
	    	
	    	Button AchievementsButton = (Button)findViewById(R.id.AchievementsButton);
	    	AchievementsButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent RankingIntent = new Intent(MenuView.this, AchievementsView.class);
					startActivityForResult(RankingIntent, 3);
				}
			});
	    	
	    	
	    	new CountDownTimer(5000, 1000) {

	      	     public void onTick(long millisUntilFinished) {
	      	    	 // do nothing
	      	     }

	      	     public void onFinish() {
	      	    	TableLayout menuButtons = (TableLayout)findViewById(R.id.menu_buttons_group);
	      	    	menuButtons.setVisibility(View.VISIBLE);
	      	     }
	      	  }.start();
    	
	}
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	if (resultCode == 1) {

    	    startActivityForResult(data, requestCode);

    	}
    	
    	if (resultCode == 2) {

    	    startActivity(data);

    	}
    	
    	if (resultCode == 3) {

	    	boolean loop = true;
	        SoundManager.playMusicBG(SoundManager.INTRO_MUSIC_BGM,loop); 

    	}
    }//onActivityResult
    
    
    @Override
    protected void onPause() {
    	SoundManager.stopMusic();
    	super.onPause();
    }
    
  public void onResume() {
	super.onResume();
    
}
    
    @Override
    protected void onStop() {
    	super.onStop();
    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    	
//    	SoundManager.cleanup();
//    	SoundManager.getInstance();
//        SoundManager.initSounds(this);
//        SoundManager.loadSounds();
//    	
//        boolean loop = true;
//        SoundManager.playMusicBG(SoundManager.INTRO_MUSIC_BGM,loop);
        
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	SoundManager.stopMusic();
    	SoundManager.cleanup();
    }
    
   
}

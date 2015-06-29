package com.adrianoprezende.zombies.main;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * StartGameView class.
 * @author Adriano Pereira Rezende
 */
public class StartGameView extends Activity {
	private MainGamePanel mainGamePanel;
	private Random rand = new Random(System.currentTimeMillis());
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	SoundManager.getInstance();
        
        boolean loop = true;
        SoundManager.playMusicBG(raffleMusic(),loop);
        mainGamePanel = new MainGamePanel(this);
        setContentView(mainGamePanel);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    
    
    @Override
    public void onBackPressed() {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to exit?")
    	       .setCancelable(false)
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	               
    	        	   MainGamePanel.setMainMode(MainGamePanel.STOP);
    	        	   synchronized (mainGamePanel) {
    	        		   mainGamePanel.notifyAll();
    	        	   }
    	        	   StartGameView.this.setResult(3);
    	        	   StartGameView.this.finish();
    	           }
    	       })
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   
    	        	   MainGamePanel.setMainMode(MainGamePanel.RUNNING);
    	               synchronized (mainGamePanel) {
    	            	   mainGamePanel.notifyAll();
    	               }
    	        	   dialog.dismiss();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	MainGamePanel.setMainMode(MainGamePanel.PAUSE);
    	alert.show();
    }
    
    @Override
    protected void onPause() {
    	SoundManager.stopMusic();
    	
    	if(mainGamePanel.getMainMode() == MainGamePanel.PAUSE) {
    		MainGamePanel.setMainMode(MainGamePanel.RUNNING);
            synchronized (mainGamePanel) {
         	   mainGamePanel.notifyAll();
            }
    	}
    	
    	setResult(3);
    	StartGameView.this.finish();
    	
    	super.onPause();
    }
    
    
    @Override
    public void onRestart() {
    	super.onRestart();
    }
    
    public void onDestroy() {
    	super.onDestroy();
    }
    
    /**
     * Selects randomly the music to the game
     * @return
     */
    private int raffleMusic() {
    	switch(rand.nextInt(2)) {
	    	case 0: return SoundManager.MAIN_MUSIC_BGM;
	    	case 1: return SoundManager.MAIN_MUSIC_BGM_2;
	    	default : return SoundManager.MAIN_MUSIC_BGM;
    	}
    }

}
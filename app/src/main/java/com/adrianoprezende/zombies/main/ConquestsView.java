package com.adrianoprezende.zombies.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.adrianoprezende.zombies.providers.helpers.Score;

/**
 * ConquestsView class.
 * @author Adriano Pereira Rezende
 */
public class ConquestsView extends Activity {
	
	private Cursor mCursorScore;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	SoundManager.getInstance();
    	boolean loop = true;
    	SoundManager.playMusicBG(SoundManager.GAMEOVER_MUSIC_BGM,loop);
    	
    	setContentView(R.layout.conquests);
    	
    	mCursorScore = ConquestsView.this.getContentResolver().
                query(Score.CONTENT_URI, null, null, null, null);

		int zombies = 0;
		int werewolf = 0;
		int mummy = 0;
		int frankeinstein = 0;
		int vampire = 0;
		int innocent = 0;
		int total = 0;
		int time = 0;
		int id = 0;
		int position = 0;
  
		mCursorScore.moveToLast();
			
		time = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.TIME));
		total = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.TOTAL));
		zombies = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.ZOMBIE));
		werewolf = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.WEREWOLF));
		mummy = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.MUMMY));
		frankeinstein = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.FRANKEINSTEIN));
		vampire = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.VAMPIRE));
		innocent = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.INNOCENT));
		id = mCursorScore.getInt(mCursorScore.getColumnIndex(Score._ID));
		
      	mCursorScore.close();
      	
      	//Run the query again to get the position of the player in overall ranking
      	mCursorScore = ConquestsView.this.getContentResolver().
				query(Score.CONTENT_URI, null, null, null, "ABS("+ Score.TOTAL +") DESC, ABS("+ Score.TIME +") DESC");
		
		int x = mCursorScore.getCount();
		
		mCursorScore.moveToFirst();
		
		for(int i=0; i < x; i++) {
			Integer id_2 = mCursorScore.getInt(mCursorScore.getColumnIndex(Score._ID));
			
			if(id_2.equals(Integer.valueOf(id))) {
				position = i+1;
				break;
			}
			
			mCursorScore.moveToNext();
		}
		mCursorScore.close();
      	
      	String textList = 
      					+zombies+" - ZOMBIE KILLS\n"
      					+werewolf+" - WEREWOLF KILLS\n"
      					+mummy+" - MUMMY KILLS\n"
      					+frankeinstein+" - FRANKEINSTEIN KILLS\n"
      					+vampire+" - VAMPIRE KILLS\n"
      					+innocent+" - INNOCENT KILLS\n\n"
      					+time+" SECONDS\n"+
      					"TOTAL SCORE: "+total+"\n"+
      					"RANK: "+position+"¼\n\n"+
						"CLICK TO LOOK THE OVERALL SCORE!";
      	
        TextView textView = (TextView)findViewById(R.id.ConquestText);
        textView.setText(textList);
		
	}
    
    
    @Override
    protected void onPause() {
    	SoundManager.stopMusic();
    	setResult(3);
    	super.onPause();
    }
    
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
    	ConquestsView.this.setResult(3);
    	super.onBackPressed();
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	if(event.getAction() == MotionEvent.ACTION_DOWN) {
    		Intent rankingGameIntent = new Intent(ConquestsView.this, RankingView.class);
    		setResult(1,rankingGameIntent);
    		finish();
		}
    	
    	return false;
    }
    
   
}

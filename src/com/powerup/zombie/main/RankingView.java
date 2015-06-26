package com.powerup.zombie.main;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.powerup.zombie.providers.helpers.Score;

/**
 * RankingView class.
 * @author Adriano Pereira Rezende
 */
public class RankingView extends Activity {
	private Cursor mCursorScore;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	SoundManager.getInstance();
    	boolean loop = true;
    	SoundManager.playMusicBG(SoundManager.GAMEOVER_MUSIC_BGM,loop);
    	
    	setContentView(R.layout.ranking);
    	
    	mCursorScore = RankingView.this.getContentResolver().
				query(Score.CONTENT_URI, null, null, null, "ABS("+ Score.TOTAL +") DESC, ABS("+ Score.TIME +") DESC");
		
		ListView rankinglist = (ListView)findViewById(R.id.ListView01);
		
		int x = mCursorScore.getCount();
		int y;
		if(x >= 10) {
			y = 10;
		} else {
			y = x;
		}
		String[] scoreIndexArray = new String[y];
		
		mCursorScore.moveToFirst();
		
		for(int i=0; i < y; i++) {
			Integer score = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.TOTAL));
			Integer time = mCursorScore.getInt(mCursorScore.getColumnIndex(Score.TIME));
			
			scoreIndexArray[i] = i+1 +"¼ - "+score+" points, "+time+" secs";
			mCursorScore.moveToNext();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(          
				this, R.layout.ranking_item_index, scoreIndexArray);
		
		rankinglist.setAdapter(adapter);
		mCursorScore.close();
		
      	Button ExitButton = (Button)findViewById(R.id.MenuButton);
      	ExitButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setResult(3);
					finish();
				}
		});
	  	
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
    	RankingView.this.setResult(3);
    	super.onBackPressed();
    }
    
}

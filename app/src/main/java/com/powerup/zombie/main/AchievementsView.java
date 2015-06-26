package com.powerup.zombie.main;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.powerup.zombie.providers.helpers.Score;

/**
 * AchievementsView class.
 * @author Adriano Pereira Rezende
 */
public class AchievementsView extends Activity {
	
	private Cursor mCursorScore;
	private HashMap<Integer, Boolean> achievementMap;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	SoundManager.getInstance();
    	boolean loop = true;
    	SoundManager.playMusicBG(SoundManager.ACHIEVEMENTS_MUSIC_BGM,loop);
    	
    	setContentView(R.layout.achievements);
    	
    	Button ExitButton = (Button)findViewById(R.id.MenuButton);
		ExitButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setResult(3);
					finish();
				}
		});
    	
    	
    	GridView gridview = (GridView) findViewById(R.id.gridview_achievements);
        gridview.setAdapter(new ImageAdapter(this));
    	
        achievementMap = hideOrShowAchievements();
        
	}
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
    	//SoundManager.stopMusic();
    	setResult(3);
    	super.onPause();
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
    	super.onStop();
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRestart()
     */
    @Override
    public void onRestart() {
    	super.onRestart();
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
    	AchievementsView.this.setResult(3);
    	super.onBackPressed();
    }
    
    /**
     * Controls if the achievement trophies must be shown or not
     * @return HashMap<Integer, Boolean>
     */
    private HashMap<Integer, Boolean> hideOrShowAchievements() {
    	HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
    	
    	mCursorScore = AchievementsView.this.getContentResolver().
                query(Score.CONTENT_URI, null, null, null, "ABS("+ Score.TOTAL +") DESC, ABS("+ Score.TIME +") DESC");

    	int x = mCursorScore.getCount();
    	int mummy = 0;
    	int frankeinstein = 0;
		int zombies = 0;
		int werewolf = 0;
		int playTime = 0;
		int vampire = 0;
  
		mCursorScore.moveToFirst();
		
		for(int i = 0; i < x; i++) {
			zombies += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.ZOMBIE));
			werewolf += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.WEREWOLF));
			mummy += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.MUMMY));
			frankeinstein += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.FRANKEINSTEIN));
			vampire += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.VAMPIRE));
			playTime += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.TIME));
			mCursorScore.moveToNext();
		}
		
      	mCursorScore.close();
      	
      	//Tests the rules of game
      	if(mummy >= AchievementsRules.MUMMY_NUM_KILLS) {
      		map.put(R.drawable.trophy_mummy, true);
      	} else {
      		map.put(R.drawable.trophy_mummy, false);
      	}
      	
      	if(frankeinstein >= AchievementsRules.FRANKEINSTEIN_NUM_KILLS) {
      		map.put(R.drawable.trophy_monster_bust, true);
      	} else {
      		map.put(R.drawable.trophy_monster_bust, false);
      	}
      	
      	if(zombies >= AchievementsRules.ZOMBIE_NUM_KILLS) {
      		map.put(R.drawable.trophy_necklace_of_ears, true);
      	} else {
      		map.put(R.drawable.trophy_necklace_of_ears, false);
      	}
      	
      	if(werewolf >= AchievementsRules.WEREWOLF_NUM_KILLS) {
      		map.put(R.drawable.trophy_werewolf_claws, true);
      	} else {
      		map.put(R.drawable.trophy_werewolf_claws, false);
      	}
      	
      	if(playTime >= AchievementsRules.PLAY_TIME) {
      		map.put(R.drawable.trophy_medal_of_honor, true);
      	} else {
      		map.put(R.drawable.trophy_medal_of_honor, false);
      	}
      	
      	if(vampire >= AchievementsRules.VAMPIRE_NUM_KILLS) {
      		map.put(R.drawable.trophy_vampire_coffin, true);
      	} else {
      		map.put(R.drawable.trophy_vampire_coffin, false);
      	}
      	
      	return map;
    }
    
    

/**
 * Inner Class
 */
class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setPadding(10, 10, 10, 10);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            
            if(achievementMap.get(mThumbIds[position]) != null) {
            	if(achievementMap.get(mThumbIds[position]) == true) {
            		imageView.setVisibility(ImageView.VISIBLE);
            	} else {
            		imageView.setVisibility(ImageView.INVISIBLE);
            	}
            } else {
            	imageView.setVisibility(ImageView.VISIBLE);
            }
            
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.trophy_mummy, R.drawable.trophy_monster_bust,
                R.drawable.plate_mummy_head, R.drawable.plate_monster_bust,
                R.drawable.trophy_necklace_of_ears, R.drawable.trophy_werewolf_claws,
                R.drawable.plate_necklace_of_ears, R.drawable.plate_werewolf_claws,
                R.drawable.trophy_medal_of_honor, R.drawable.trophy_vampire_coffin,
                R.drawable.plate_medal_of_honor, R.drawable.plate_vampire_coffin
        };
    }
    
   
}

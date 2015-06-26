package com.powerup.zombie.characters;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.powerup.zombie.core.InteractiveObject;
import com.powerup.zombie.main.R;
import com.powerup.zombie.main.SoundManager;

/**
 * Humans class.
 * @author Adriano Pereira Rezende
 */
public class Humans extends InteractiveObject {
	
	private static Random rand = new Random();
	
	private static final int MISTER_HUMAN = R.drawable.man;
	private static final int LADY_HUMAN = R.drawable.lady;
	private static final int HUNTER_HUMAN = R.drawable.hunter;
	
	private static final int TIME_LIMIT = 3;

	private String key;
	private boolean isActive;
	
	//private Paint debug = new Paint();

	
	/**
	 * Method constructor, that returns a new instance of a Human
	 */
	public Humans(Resources res, String keyMap) {
		super(res,raffleBitmap());
		this.key = keyMap;
		this.setCoordinatesByKey(keyMap);
		isActive = false;
		//this.debug.setColor(Color.WHITE);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.InteractiveObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		if(isActive) {
			super.draw(canvas);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.InteractiveObject#handleActionDown(int, int)
	 */
	@Override
	public void handleActionDown(int eventX, int eventY) {
		if(isActive) {
			super.handleActionDown(eventX, eventY);
			if(isTouched()) {
				setTouched(true);
				if(this.getBitmapID() == LADY_HUMAN) {
					SoundManager.playSound(SoundManager.WOMAN_SHOUT_FX);
				} else {
					SoundManager.playSound(SoundManager.MAN_SHOUT_FX);
				}
				isActive = false;
				resetTimer();
			}
			
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.InteractiveObject#animate()
	 */
	public void animate() {
		if(isActive) {
			if(!isTouched()) {
				//starts the timer
				if(!isTimeStarted()) {
					startTimer();
				}
				
				setElapsedTime((float)(System.currentTimeMillis() - getStartTime())/1000);
				if( getElapsedTime() >= TIME_LIMIT ) {
					resetTimer();
					isActive = false;
				}
			}
			
		}
	    	
	}
	
	
	/**
	 * Sets the coordinates(x and y position attributes) according a string key passed by parameter.
	 * @param key
	 */
	public void setCoordinatesByKey(String key) {
		
		setX(parseXY(key,0, 3));
		setY(parseXY(key,3, 6));
	}
	
	/**
	 * Parses the string key according from the start index(begin) to end index (end).
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	private int parseXY(String key, int begin, int end) {
		return Integer.parseInt(key.substring(begin, end));
	}
	
	/**
	 * Method that returns a random image of a human.
	 * @return
	 */
	private static int raffleBitmap() {
		int zombieID = HUNTER_HUMAN;
		
		switch(rand.nextInt(3)) {
			case 0 : zombieID = MISTER_HUMAN; break;
			case 1 : zombieID = LADY_HUMAN; break;
			case 2 : zombieID = HUNTER_HUMAN; break;
		}
		return zombieID;
		
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	
	/**
	 * Gets the string key
	 * @return key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Sets the string key
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * Gets the isActive value
	 * @return isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	
	/**
	 * Sets the isActive value
	 * @param isActive
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}

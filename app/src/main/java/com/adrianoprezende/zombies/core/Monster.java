package com.adrianoprezende.zombies.core;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.adrianoprezende.zombies.main.Claw;
import com.adrianoprezende.zombies.main.SoundManager;

/**
 * This abstract class must be inherited and implemented if the desired object is a Monster.
 * @author Adriano Pereira Rezende
 */
public abstract class Monster extends InteractiveObject {
	
	protected static Random rand = new Random();
	
	private String monsterNoise_SoundID;
	private int maxHealth;
	private float maxSpeed;
	private int atackPower;
	private int valueScorePoints;

	private float y0; // the origin position on Y axis.
	private float yLimit; // the limit position of zombie animation
	private int health; // health variable
	private boolean toHurt; // variable to decrement player's health
	private boolean attacked;
	
	private boolean limitReached;
	private String key;
	
	// Object that refers to Claw animation (claw)
	private Claw claw;
	
	/**
	 * Monster Default Constructor Method.
	 * @param res
	 * @param keyMap
	 * @param yRange
	 * @param monsterBitmap_ID
	 * @param monsterNoise_SoundID
	 * @param maxHealth
	 * @param maxSpeed
	 * @param atackPower
	 * @param valueScorePoints
	 */
	public Monster(Resources res, String keyMap, float yRange,
			int monsterBitmap_ID, String monsterNoise_SoundID, int maxHealth,
			float maxSpeed, int atackPower, int valueScorePoints) {
		super(res, monsterBitmap_ID);
		this.key = keyMap;
		this.setCoordinatesByKey(keyMap);
		this.yLimit = y0 - yRange;
		this.limitReached = false;
		this.toHurt = false;
		this.monsterNoise_SoundID = monsterNoise_SoundID;
		this.maxHealth = maxHealth;
		this.maxSpeed = maxSpeed;
		this.atackPower = atackPower;
		this.valueScorePoints = valueScorePoints;
		attacked = false;
		health = raffleHealth();
		claw = new Claw(res);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.InteractiveObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		claw.draw(canvas);
	}
	
	/**
	 * Handles all touches on screen from user, setting if the monster was touched or not.
	 * Changes the monster bitmap to monster wounded if it was touched.
	 * Decrements in -1 the monster health.
	 */
	@Override
	public void handleActionDown(int eventX, int eventY) {
		super.handleActionDown(eventX, eventY);
		if(isTouched()) {
			//changes the bitmap, if zombie was attacked
			if(!attacked) {
				super.changeBitmap(getBitmapID() + 1);
				attacked = true;
			}
			if(health > 0) {
				setTouched(false);
				health--;
			} else {
				setTouched(true);
				SoundManager.playSound(monsterNoise_SoundID);
			}
		}
	}
	
	/**
	 * Handles all touches on screen from user, setting if the monster was touched or not. 
	 * Changes the monster bitmap to monster wounded if it was touched.
	 * Decrements the monster health according with the gun damage.
	 * @param eventX
	 * @param eventY
	 * @param gun
	 */
	public void handleActionDown(int eventX, int eventY, Gun gun) {
		super.handleActionDown(eventX, eventY);
		if(isTouched()) {
			//changes the bitmap, if zombie was attacked
			if(!attacked) {
				super.changeBitmap(getBitmapID() + 1);
				attacked = true;
			}
			if(health > 0) {
				setTouched(false);
				decrementHealth(gun.getDamage());
			} else {
				setTouched(true);
				SoundManager.playSound(monsterNoise_SoundID);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.InteractiveObject#animate()
	 */
	public void animate() {
		boolean attack = false;
		
		if(!isTouched()) {
			//starts the timer
			if(!isTimeStarted()) {
				startTimer();
			}
			
			if(!limitReached) {
				setElapsedTime((float)(System.currentTimeMillis() - getStartTime())/1000);
				setY(getY() - maxSpeed * getElapsedTime());
	    		if( getY() <= yLimit ) {
	    			limitReached = true;
	    			resetTimer();
	    		}
	    	} else {
	    		setElapsedTime((float)(System.currentTimeMillis() - getStartTime())/1000);
	    		
	    		if(getElapsedTime() > 2) {
	    			setY(getY() + maxSpeed * getElapsedTime());
		    		if( getY() >= y0 ) {
		    			attack = true;
		    			claw.setScratch(true);
		    			limitReached = false;
		    			resetTimer();
		    		}
	    		}
    			
	    	}
		}
		setToHurt(attack);
		claw.animate();
	}
	
	
	/**
	 * Sets the coordinates(x and y position attributes) according a string key passed by parameter.
	 * @param key
	 */
	public void setCoordinatesByKey(String key) {
		
		setX(parseXY(key,0, 3));
		setY0(parseXY(key,3, 6));
		setY(parseXY(key,3, 6));
	}
	
	/**
	 * Parses the string key according from the start index(begin) to end index (end)
	 * @param key
	 * @param begin
	 * @param end
	 * @return coordinates(x and y) position key.
	 */
	private int parseXY(String key, int begin, int end) {
		return Integer.parseInt(key.substring(begin, end));
	}
	
	/**
	 * Raffles the monster health according with a margin.
	 * @return the health to use.
	 */
	private int raffleHealth() {
		int margin = rand.nextInt(3);
		return maxHealth - margin;
	}
	
	/**
	 * Decrements the monster health.
	 * @param value
	 */
	private void decrementHealth(int value) {
		this.health -= value;
	}
	
	
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	/**
	 * Gets the Y0 limit position.
	 * @return y0
	 */
	public float getY0() {
		return y0;
	}
	
	/**
	 * Sets the Y0 limits position.
	 * @param y0
	 */
	public void setY0(int y0) {
		this.y0 = y0;
	}
	
	/**
	 * Gets the string key
	 * @return key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Sets the string key.
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets a boolean value that indicates if the monster was attacked or not.
	 * @return the toHurt
	 */
	public boolean isToHurt() {
		return toHurt;
	}

	/**
	 * Sets the monster state, if it was attacked or not.
	 * @param toHurt the toHurt to set
	 */
	public void setToHurt(boolean toHurt) {
		this.toHurt = toHurt;
	}

	/**
	 * Gets the monster attack power.
	 * @return atackPower
	 */
	public int getAtackPower() {
		return atackPower;
	}
	
	/**
	 * Gets the monster value score points.
	 * @return valueScorePoints
	 */
	public int getValueScorePoints() {
		return valueScorePoints;
	}
	
}

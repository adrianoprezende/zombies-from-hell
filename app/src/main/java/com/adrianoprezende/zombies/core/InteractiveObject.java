package com.adrianoprezende.zombies.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * This abstract class must be inherited and implemented if the desired object is just a single bitmap without transformations (Not sprite).
 * @author Adriano Pereira Rezende
 */
public abstract class InteractiveObject implements InteractiveObjectInterface {
		
	private float x;
	private float y;
	
	private boolean isTouched;
	
	// Variables to control the time of animation
	private boolean isTimeStarted;
	private float elapsedTime;
	private long startTime;
	
	private Bitmap bitmap;
	private int bitmapID;
	
	private Resources resources;
	
	/**
	 * Interactive Object Default Constructor Method.
	 * Creates a new bitmap by the parameters res and id, and set default values to some attributes.
	 * @param res
	 * @param id
	 */
	public InteractiveObject(Resources res, int id) {
		x = 0;
		y = 0;
		setBitmapID(id);
		bitmap = BitmapFactory.decodeResource(res,id);
		isTimeStarted = false;
		elapsedTime = 0l;
		startTime = 0l;
		resources = res;
	}
	
	/**
	 * Draws the bitmap on screen in the given coordinates (x and y class attributes).
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2, null);
	}
	
	/**
	 * Handles all touches on screen from user.
	 * @param eventX
	 * @param eventY
	 */
	public void handleActionDown(int eventX, int eventY) {
		if(eventX >= (x - bitmap.getWidth() / 2) && eventX <= (x + bitmap.getWidth() / 2)) {
			if(eventY >= (y - bitmap.getHeight() / 2) && eventY <= (y + bitmap.getHeight() / 2)) {
				setTouched(true);
			} else setTouched(false);
		} else setTouched(false);
	}
	
	/**
	 * Changes the actual Bitmap Resource passing the desired bitmap id.
	 * @param bitmapID
	 */
	public void changeBitmap(int bitmapID) {
		bitmap = BitmapFactory.decodeResource(resources,bitmapID);
	}
	
	/**
	 * Animates the bitmap on screen.
	 */
	public abstract void animate();
	
	
	/**
	 * Get the x bitmap position
	 * @return x
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the x bitmap position
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Get the y bitmap position
	 * @return y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the y bitmap position
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Sets both x and y bitmap position. 
	 * @param x
	 * @param y
	 */
	public void setCoordinates(float x, float y) {
		this.y = y;
		this.x = x;
	}

	/**
	 * Returns a boolean indicating if the bitmap was touched or not.
	 * @return isTouched
	 */
	public boolean isTouched() {
		return isTouched;
	}

	/**
	 * Sets the state of the bitmap, if it was touched or not.
	 * @param isTouched
	 */
	public void setTouched(boolean isTouched) {
		this.isTouched = isTouched;
	}
	
	/**
	 * Returns a boolean indicating if the time was started.
	 * @return isTimeStarted
	 */
	public boolean isTimeStarted() {
		return isTimeStarted;
	}

	/**
	 * Sets the state of the time, if it was started or not.
	 * @param isTimeStarted
	 */
	public void setTimeStarted(boolean isTimeStarted) {
		this.isTimeStarted = isTimeStarted;
	}

	/**
	 * Returns the elapsed time.
	 * @return elapsedTime
	 */
	public float getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * Sets the elapsed time.
	 * @param time
	 */
	public void setElapsedTime(float time) {
		this.elapsedTime = time;
	}

	/**
	 * Gets the initial start time.
	 * @return
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Sets the initial start time.
	 * @param startTime
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Starts the timer setting the startTime attribute with current time and isTimeStarted attribute with true.
	 */
	public void startTimer() {
			this.startTime = System.currentTimeMillis();
			this.isTimeStarted = true;
	}
	
	/**
	 * Resets the timer.
	 */
	public void resetTimer() {
			this.startTime = 0L;
			this.elapsedTime = 0L;
			this.isTimeStarted = false;
	}
	
	/**
	 * Gets the actual bitmap.
	 * @return bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	/**
	 * Sets the bitmap.
	 * @param bitmap
	 */
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * Gets the actual bitmap id.
	 * @return the bitmapID
	 */
	public int getBitmapID() {
		return bitmapID;
	}

	/**
	 * Sets the bitmap id.
	 * @param bitmapID the bitmapID to set
	 */
	public void setBitmapID(int bitmapID) {
		this.bitmapID = bitmapID;
	}


}

package com.powerup.zombie.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * This abstract class must be inherited and implemented if the desired object is a sprite.
 * @author Adriano Pereira Rezende
 */
public abstract class InteractiveSpriteObject implements InteractiveObjectInterface {

	private float x;
	private float y;
	private int screenWidth;
	private int screenHeight;
	
	private int currentFrame;
	private Bitmap[] frameVector;
	
	private boolean isTouched;
	
	// Variables to control the time of animation
	private boolean isTimeStarted;
	private float elapsedTime;
	private long startTime;	
	
	/**
	 * Interactive Sprite Default Constructor Method.
	 * Creates a new bitmap frame vector and set default values to some attributes.
	 */
	public InteractiveSpriteObject() {
		x = 0;
		y = 0;
		currentFrame = 0;
		frameVector = new Bitmap[]{};
		isTimeStarted = false;
		elapsedTime = 0l;
		startTime = 0l;
	}
	
	/**
	 * Draws the bitmap on screen in the given coordinates (x and y class attributes).
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		if(currentFrame < frameVector.length) {
			canvas.drawBitmap(frameVector[currentFrame], x - frameVector[currentFrame].getWidth() /2, y - frameVector[currentFrame].getHeight() /2, null);
		}
	}
	
	/**
	 * Handles all touches on screen from user.
	 * @param eventX
	 * @param eventY
	 */
	public void handleActionDown(int eventX, int eventY) {			
		if(currentFrame < frameVector.length) {
			if(eventX >= (x - frameVector[currentFrame].getWidth() / 2) && eventX <= (x + frameVector[currentFrame].getWidth() / 2)) {
				if(eventY >= (y - frameVector[currentFrame].getHeight() / 2) && eventY <= (y + frameVector[currentFrame].getHeight() / 2)) {
					setTouched(true);
				} else setTouched(false);
			} else setTouched(false);
		}
		
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
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Sets both x and y bitmap position. 
	 * @param x
	 * @param y
	 */
	public void setCoordinates(int x, int y) {
		this.y = y;
		this.x = x;
	}
	
	/**
	 * Gets the screen width.
	 * @return screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}
	
	/**
	 * gets the screen height.
	 * @return screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}
	
	/**
	 * Sets the screen Width and screen Height.
	 * @param screenWidth
	 * @param screenHeight
	 */
	public void setScreenMeasures(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}


	/**
	 * Returns a boolean indicating if the object was touched or not.
	 * @return isTouched
	 */
	public boolean isTouched() {
		return isTouched;
	}

	/**
	 * Sets the state of the object, if it was touched or not.
	 * @param isTouched
	 */
	public void setTouched(boolean isTouched) {
		this.isTouched = isTouched;
	}
	
	/**
	 * Gets the Bitmap[] frame vector.
	 * @return frameVector
	 */
	public Bitmap[] getFrameVector() {
		return frameVector;
	}

	/**
	 * Sets the Bitmap[] frame vector.
	 * @param frameVector
	 */
	public void setFrameVector(Bitmap[] frameVector) {
		this.frameVector = frameVector;
	}

	/**
	 * Gets the current frame (frame vector's current position).
	 * @return
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * Sets the frame vector's current position.
	 * @param currentFrame
	 */
	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	/**
	 * Increments the frame vector's current frame.
	 */
	public void incrementCurrentFrame() {
		this.currentFrame++;
	}
	
	/**
	 * Decrements the frame vector's current frame.
	 */
	public void decrementCurrentFrame() {
		this.currentFrame--;
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
	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
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
	


}

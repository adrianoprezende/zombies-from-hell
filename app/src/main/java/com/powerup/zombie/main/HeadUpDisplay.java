package com.powerup.zombie.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.CountDownTimer;

/**
 * HeadUpDisplay class work with all messages and game information that needs to be shown in screen.
 * @author Adriano Pereira Rezende
 */
public class HeadUpDisplay {
	
	private static HeadUpDisplay _instance;
	
	private static Paint frontBar = new Paint();
	private static Paint backBar = new Paint();
	private static Paint borderBar = new Paint();
	private static Paint upperTextPaint = new Paint();
	private static Paint reloadTextPaint = new Paint();
	
	private static Typeface mFont;
	
	private static boolean showHorizBar;
	private static float left;
	private static float top;
	private static float right;
	private static float bottom;
	private static float innerBarValue;
	private static boolean fullBar;
	private static int screenWidth;
	private static int screenHeight;
	
	private static float timeXCoord;
	private static float timeYCoord;
	private static float scoreXCoord;
	private static float scoreYCoord;
	
	private HeadUpDisplay() {
			
		}
	
	/**
	 * Requests the instance of the HeadUpDisplay and creates it
	 * if it does not exist.
	 * @return Returns the single instance of the HeadUpDisplay
	 */
	static synchronized public HeadUpDisplay getInstance() {
		if(_instance == null) {
			_instance = new HeadUpDisplay();	
		}
		return _instance;
	}
	
	public static void initHeadUpDisplay(int viewWidth, int viewHeight, Typeface font) {
		showHorizBar = false;
		screenWidth = viewWidth;
		screenHeight = viewHeight;
		upperTextPaint.setTypeface(font);
		upperTextPaint.setColor(Color.RED);
		upperTextPaint.setTextSize((viewWidth/100) * 9.25f);
		reloadTextPaint.setTypeface(font);
		reloadTextPaint.setColor(Color.RED);
		reloadTextPaint.setTextSize((viewWidth/5));
		setTimeAndScoreCoordinates();
		mFont = font;
	}
	
	public static void draw(Canvas canvas, long time, int score) {
		// Draw the time
		canvas.drawText("TIME: "+time, timeXCoord - (upperTextPaint.getTextSize()), timeYCoord, upperTextPaint);
		
		//Draw the player's score
		canvas.drawText("SCORE: "+score, scoreXCoord - (upperTextPaint.getTextSize()), scoreYCoord, upperTextPaint);
		
		//Draw the horizontal bar
		if(showHorizBar) {
			canvas.drawRect(left-2, top-2, right+2, bottom+2, borderBar);
			canvas.drawRect(left, top, right, bottom, backBar);
			canvas.drawRect(left, top, left+innerBarValue, bottom, frontBar);
		}
		
	}
	
	/**
	 * Draws the reload message in the screen
	 * @param canvas
	 * @param text
	 */
	public static void drawReload(Canvas canvas, String text) {
		// Draws the reload message
		canvas.drawText(text, screenWidth/8, screenHeight/2, reloadTextPaint);
	}
	
	public static void createHorizontalBar(int frontBarColor, int backBarColor, int borderBarColor) {
		showHorizBar = true;
		frontBar.setColor(frontBarColor);
		backBar.setColor(backBarColor);
		borderBar.setColor(borderBarColor);
		innerBarValue = 0;
		setBarBounds();
		innerBarValue = right-2;
	}
	
	public static void stopToShowHorizontalBar() {
		showHorizBar = false;
		innerBarValue = 0;
	}
	
	private static void setBarBounds() {
		left = (float)screenWidth/10;
		right = (float)screenWidth/3;
		top = screenHeight/10;
		bottom = screenHeight/8;
	}
	
	private static void setTimeAndScoreCoordinates() {
		timeXCoord = (screenWidth/10) * 5;
		timeYCoord = (screenHeight/100) * 7.75f;
		scoreXCoord = screenWidth/10;
		scoreYCoord = (screenHeight/100) * 7.75f;
	}
	
	public static float getInnerBarValue() {
		return innerBarValue;
	}
	
	public static void setInnerBarValue(float value) {
		innerBarValue = value;
	}

	public static void incrementInnerBar(float value) {
		if(!isFullBar()) {
			if(left + (value+innerBarValue) < right) {
				innerBarValue += value;
			} else if(left + (value+innerBarValue) >= right) {
				innerBarValue = right-left;
				setFullBar(true);
			}
			
		}
	}
	
	public static void decrementInnerBar(float value) {
		if(innerBarValue > 0) {
			if((left + innerBarValue) - value > left) {
				innerBarValue -= value;
			} else if((left + innerBarValue) - value <= left) {
				setEmptyBar();
			}
			
		}
	}
	
	public static void decrementInnerBarRoutine(long time) {
		new CountDownTimer(5000, 1000) {
				
			@Override
			public void onTick(long millisUntilFinished) {
				HeadUpDisplay.decrementInnerBar(10f);
			}
			
			@Override
			public void onFinish() {
				
			}
		};
	}
	
	public static void setEmptyBar() {
		innerBarValue = 0;
		setFullBar(false);
	}
	
	public static boolean isEmptyBar() {
		if(innerBarValue == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFullBar() {
		return fullBar;
	}

	public static void setFullBar(boolean value) {
		fullBar = value;
	}

	public static Typeface getmFont() {
		return mFont;
	}

	public static void setmFont(Typeface mFont) {
		HeadUpDisplay.mFont = mFont;
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int screenWidth) {
		HeadUpDisplay.screenWidth = screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int screenHeight) {
		HeadUpDisplay.screenHeight = screenHeight;
	}
	
}

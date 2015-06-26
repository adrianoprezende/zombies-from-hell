package com.powerup.zombie.weapons;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.powerup.zombie.core.Gun;
import com.powerup.zombie.main.R;
import com.powerup.zombie.main.SoundManager;

/**
 * The Rocket class.
 * @author Adriano Pereira Rezende
 */
public class Rocket extends Gun {
	
	// Constants
	private static final int MAX_BULLETS = 4;
	private static final int MAX_DAMAGE = 10;
	//Attributes that controls the limit of sensor listener accuracy.
	private boolean topLimitAcc;
	private boolean bottomLimitAcc;
	
	// Images
	private Bitmap frame1;
	private Bitmap frame2;
	private Bitmap frame3;
	private Bitmap frame4;
	
	// Default Constructor
	public Rocket(Resources res) {
		super(MAX_BULLETS, MAX_DAMAGE);
		topLimitAcc = false;
		bottomLimitAcc = false;
		
		// Initial Charge to every frame
		frame1 = BitmapFactory.decodeResource(res, R.drawable.rocket0);
		frame2 = BitmapFactory.decodeResource(res, R.drawable.rocket1);
		frame3 = BitmapFactory.decodeResource(res, R.drawable.rocket2);
		frame4 = BitmapFactory.decodeResource(res, R.drawable.rocket3);
		
		setFrameVector(new Bitmap[]{frame1, frame2, frame3, frame4});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.Gun#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		if(this.isActive()) {
			if(getCurrentFrame() >= getFrameVector().length) {
				setCurrentFrame(getFrameVector().length);
			} else {
				super.draw(canvas);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.Gun#animate()
	 */
	public void animate() {
		reload();

	}

	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.Gun#screenTouched()
	 */
	public void screenTouched() {

		if(getCurrentFrame() < getFrameVector().length) {
			incrementCurrentFrame();
		}
		
		if(getBullets() > 0) {
			SoundManager.playSound(SoundManager.SHOOT_ROCKET_FX);
			decrementBullets();
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.InteractiveSpriteObject#handleActionDown(int, int)
	 */
	@Override
	public void handleActionDown(int eventX, int eventY) {			
		if(this.isActive()) {
			super.handleActionDown(eventX, eventY);
			screenTouched();
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.Gun#reload()
	 */
	public void reload() {
		if(getBullets() <= 0) {
			SoundManager.playSound(SoundManager.RELOAD_ROCKET_FX);
			setBullets(MAX_BULLETS);
			setCurrentFrame(0);
		}
	}
	
	/**
	 * Gets a boolean value that indicates if the top limit accuracy was reached(about sensor listener top limit).
	 * @return topLimitAcc
	 */
	public boolean isTopLimitAcc() {
		return topLimitAcc;
	}
	
	/**
	 * Sets the top limit accuracy.
	 * @param topLimitAcc
	 */
	public void setTopLimitAcc(boolean topLimitAcc) {
		this.topLimitAcc = topLimitAcc;
	}
	
	/**
	 * Gets a boolean value that indicates if the bottom limit accuracy was reached(about sensor listener top limit).
	 * @return bottomLimitAcc
	 */
	public boolean isBottomLimitAcc() {
		return bottomLimitAcc;
	}
	
	/**
	 * Sets the bottom limit accuracy.
	 * @param bottomLimitAcc
	 */
	public void setBottomLimitAcc(boolean bottomLimitAcc) {
		this.bottomLimitAcc = bottomLimitAcc;
	}

}

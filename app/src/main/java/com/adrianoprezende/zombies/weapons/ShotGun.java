package com.adrianoprezende.zombies.weapons;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.adrianoprezende.zombies.core.Gun;
import com.adrianoprezende.zombies.main.HeadUpDisplay;
import com.adrianoprezende.zombies.main.R;
import com.adrianoprezende.zombies.main.SoundManager;

/**
 * The ShotGun class.
 * @author Adriano Pereira Rezende
 */
public class ShotGun extends Gun {
	
	// Constants
	private static final int MAX_BULLETS = 6;
	private static final int MAX_DAMAGE = 5;
	//Attributes that controls the limit of sensor listener accuracy.
	private boolean topLimitAcc;
	private boolean bottomLimitAcc;
	
	// Images
	private Bitmap frame1;
	private Bitmap frame2;
	private Bitmap frame3;
	private Bitmap frame4;
	private Bitmap frame5;
	private Bitmap frame6;
	
	// Default Constructor
	public ShotGun(Resources res) {
		super(MAX_BULLETS, MAX_DAMAGE);
		topLimitAcc = false;
		bottomLimitAcc = false;
		
		// Initial Charge to every frame
		frame1 = BitmapFactory.decodeResource(res, R.drawable.shotgun_shells0);
		frame2 = BitmapFactory.decodeResource(res, R.drawable.shotgun_shells1);
		frame3 = BitmapFactory.decodeResource(res, R.drawable.shotgun_shells2);
		frame4 = BitmapFactory.decodeResource(res, R.drawable.shotgun_shells3);
		frame5 = BitmapFactory.decodeResource(res, R.drawable.shotgun_shells4);
		frame6 = BitmapFactory.decodeResource(res, R.drawable.shotgun_shells5);
		
		setFrameVector(new Bitmap[]{frame1, frame2, frame3, frame4, frame5, frame6});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.Gun#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		if(this.isActive()) {
			if(getCurrentFrame() >= getFrameVector().length) {
				setCurrentFrame(getFrameVector().length);
				HeadUpDisplay.drawReload(canvas, "RELOAD!");
			} else {
				super.draw(canvas);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.Gun#animate()
	 */
	public void animate() {
		//do nothing
	}

	/**
	 * Handles all touches on screen from user.
	 * @param eventX
	 * @param eventY
	 */
	@Override
	public void handleActionDown(int eventX, int eventY) {
		if(this.isActive()) {

			if(eventX >= (getX() - getFrameVector()[0].getWidth() / 2) && eventX <= (getX() + getFrameVector()[0].getWidth() / 2)) {
				if(eventY >= (getY() - getFrameVector()[0].getHeight() / 2) && eventY <= (getY() + getFrameVector()[0].getHeight() / 2)) {
					setTouched(true);
				} else setTouched(false);
			} else setTouched(false);

			screenTouched();
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.Gun#screenTouched()
	 */
	public void screenTouched() {

		if(isTouched()) {
			// Tocou no tambor, entao recarrega a arma
			reload();
		} else {

			if(getCurrentFrame() < getFrameVector().length) {
				incrementCurrentFrame();
			}

			if(getBullets() > 0) {
				SoundManager.playSound(SoundManager.SHOOT_SHOTGUN_FX);
				decrementBullets();
			}
			else {
				SoundManager.playSound(SoundManager.EMPTY_GUN_FX);
			}

		}

			
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.Gun#reload()
	 */
	public void reload() {
		setStartTime(System.currentTimeMillis());
		setTimeStarted(true);

		SoundManager.playSound(SoundManager.RELOAD_SHOTGUN_FX);
		setBullets(MAX_BULLETS);
		setCurrentFrame(0);

		if(isTimeStarted()) {
			if((System.currentTimeMillis() - getStartTime()) /1000 >= 1) {
				resetTimer();
			}
			
		}
	}

}

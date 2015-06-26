package com.powerup.zombie.weapons;

import java.util.Timer;
import java.util.TimerTask;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.powerup.zombie.core.Gun;
import com.powerup.zombie.main.R;
import com.powerup.zombie.main.SoundManager;

/**
 * The Magnum class.
 * @author Adriano Pereira Rezende
 */
public class Magnum extends Gun {
	
	// Constants
	private static final int MAX_BULLETS = 6;
	private static final int MAX_DAMAGE = 2;
	
	// Sound Constants
	private static final String SHOOT_ID = "shootSE";
	private static final String RELOAD_ID = "reloadSE";
	private static final String EMPTY_GUN_ID = "emptyGunSE";
	
	//Timer attributes
	private Timer timer1 = new Timer(false);
	private Timer timer2 = new Timer(false);
	
	// Images
	private Bitmap frame1;
	private Bitmap frame2;
	private Bitmap frame3;
	private Bitmap frame4;
	private Bitmap frame5;
	private Bitmap frame6;
	private Bitmap frame7;
	private Bitmap frame8;
	private Bitmap frame9;
	private Bitmap frame10;
	private Bitmap frame11;
	private Bitmap frame12;
	private Bitmap frame13;
	private Bitmap frame14;
	private Bitmap frame15;
	private Bitmap frame16;
	private Bitmap frame17;
	private Bitmap frame18;
	private Bitmap frame19;
	
	// Default Constructor
	public Magnum(Resources res) {
		super(MAX_BULLETS, MAX_DAMAGE);
		
		// Initial Charge to every frame
		frame1 = BitmapFactory.decodeResource(res, R.drawable.cylinder10);
		frame2 = BitmapFactory.decodeResource(res, R.drawable.cylinder11);
		frame3 = BitmapFactory.decodeResource(res, R.drawable.cylinder12);
		frame4 = BitmapFactory.decodeResource(res, R.drawable.cylinder20);
		frame5 = BitmapFactory.decodeResource(res, R.drawable.cylinder21);
		frame6 = BitmapFactory.decodeResource(res, R.drawable.cylinder22);
		frame7 = BitmapFactory.decodeResource(res, R.drawable.cylinder30);
		frame8 = BitmapFactory.decodeResource(res, R.drawable.cylinder31);
		frame9 = BitmapFactory.decodeResource(res, R.drawable.cylinder32);
		frame10 = BitmapFactory.decodeResource(res, R.drawable.cylinder40);
		frame11 = BitmapFactory.decodeResource(res, R.drawable.cylinder41);
		frame12 = BitmapFactory.decodeResource(res, R.drawable.cylinder42);
		frame13 = BitmapFactory.decodeResource(res, R.drawable.cylinder50);
		frame14 = BitmapFactory.decodeResource(res, R.drawable.cylinder51);
		frame15 = BitmapFactory.decodeResource(res, R.drawable.cylinder52);
		frame16 = BitmapFactory.decodeResource(res, R.drawable.cylinder60);
		frame17 = BitmapFactory.decodeResource(res, R.drawable.cylinder61); // Empty Animation Initial Frame
		frame18 = BitmapFactory.decodeResource(res, R.drawable.cylinder70);
		frame19 = BitmapFactory.decodeResource(res, R.drawable.cylinder71);
		
		setFrameVector(new Bitmap[]{frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10, frame11, frame12, frame13, frame14,
				frame15, frame16, frame17, frame18, frame19});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.Gun#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		if(this.isActive()) {
			if(getCurrentFrame() >= getFrameVector().length) {
				setCurrentFrame(getFrameVector().length -1);
			}
			super.draw(canvas);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.Gun#animate()
	 */
	public void animate() {
		//do nothing
	}

	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.Gun#screenTouched()
	 */
	public void screenTouched() {
		
		if(isTouched()) {
			// Tocou no tambor, entao recarrega a arma
			reload();
		} else {
			// Atirou, ent‹o segue fluxo para animar o tambor
			if(getCurrentFrame() < getFrameVector().length) {
				incrementCurrentFrame();
			} else if(getCurrentFrame() >= getFrameVector().length) {
				setCurrentFrame(getFrameVector().length - 3);
			}
			
			timer1.schedule(new CylinderRoutine(), 100);
			timer2.schedule(new CylinderRoutine(), 200);
			
			if(getBullets() > 0) {
				SoundManager.playSound(SHOOT_ID);
				decrementBullets();
			}
			else {
				SoundManager.playSound(EMPTY_GUN_ID);
			}
			
		}
		
		
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.InteractiveSpriteObject#handleActionDown(int, int)
	 */
	@Override
	public synchronized void handleActionDown(int eventX, int eventY) {			
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
		SoundManager.playSound(RELOAD_ID);
		setBullets(MAX_BULLETS);
		setCurrentFrame(0);
		setTouched(false);
	}
	
	
	/**
	 * Inner Class - Routine to Cylinder spin
	 * @author adrianopr
	 *
	 */
	class CylinderRoutine extends TimerTask {

		@Override
		public void run() {
			incrementCurrentFrame();
		}
		
	}
	
	
}
	




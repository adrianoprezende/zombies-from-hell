package com.powerup.zombie.main;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.powerup.zombie.core.InteractiveObject;

/**
 * Claw class represents the monsters attack.
 * @author Adriano Pereira Rezende
 */
public class Claw extends InteractiveObject {
	private static final int CLAW = R.drawable.claw;
	private boolean scratch;
	private boolean drawClaw;
	
	/**
	 * Default constructor method.
	 * @param res
	 */
	public Claw(Resources res) {
		super(res, CLAW);
		setScratch(false);
		setDrawClaw(false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.InteractiveObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		if(drawClaw) {
			setCoordinates(canvas.getWidth()/2, canvas.getHeight()/2);
			super.draw(canvas);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.InteractiveObject#animate()
	 */
	@Override
	public void animate() {
		if(scratch) {
			if(!isTimeStarted()) {
				SoundManager.playSound(SoundManager.CLAW_FX);
				startTimer();
			}
			setElapsedTime((float)(System.currentTimeMillis() - getStartTime())/1000);
			if(getElapsedTime() < 0.1) {
				setDrawClaw(true);
			}
			if(getElapsedTime() >= 0.1) {
				setScratch(false);
				setDrawClaw(false);
				resetTimer();
			}
		}
		
	}

	/**
	 * @return the scratch
	 */
	public boolean isScratch() {
		return scratch;
	}

	/**
	 * @param scratch the scratch to set
	 */
	public void setScratch(boolean scratch) {
		this.scratch = scratch;
	}


	/**
	 * @return the drawClaw
	 */
	public boolean isDrawClaw() {
		return drawClaw;
	}

	/**
	 * @param drawClaw the drawClaw to set
	 */
	public void setDrawClaw(boolean drawClaw) {
		this.drawClaw = drawClaw;
	}

}

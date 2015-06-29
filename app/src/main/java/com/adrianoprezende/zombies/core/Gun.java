package com.adrianoprezende.zombies.core;

import android.graphics.Canvas;

import com.adrianoprezende.zombies.main.HeadUpDisplay;

/**
 * This abstract class must be inherited and implemented if the desired object is a gun.
 * @author Adriano Pereira Rezende
 */
public abstract class Gun extends InteractiveSpriteObject {
	
	// Variable to manipulate the bullets
	private int bullets;
	private int maxBullets;
	
	private int damage;
	
	private boolean isActive;
	private boolean isShaked;
	
	private boolean startAnimate;
	
	/**
	 * Item Default Constructor Method.
	 * Creates a new gun game object.
	 * @param maxBullets
	 * @param damage
	 */
	public Gun(int maxBullets, int damage) {
		super();
		this.damage = damage;
		this.maxBullets = maxBullets;
		bullets = maxBullets;
		startAnimate = false;
		isActive = true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.InteractiveSpriteObject#draw(android.graphics.Canvas)
	 */
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(bullets <= 0) {
			HeadUpDisplay.drawReload(canvas, "RELOAD!");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.InteractiveSpriteObject#animate()
	 */
	public abstract void animate();

	
	/**
	 * Must be called every time that the screen has touched.
	 */
	public abstract void screenTouched();
	
	/**
	 * Reload a gun.
	 */
	public abstract void reload();
	
	/**
	 * Gets the number of bullets of the gun.
	 * @return bullets
	 */
	public int getBullets() {
		return bullets;
	}
	
	/**
	 * Sets the number of bullets of the gun.
	 * @param bullets
	 */
	public void setBullets(int bullets) {
		this.bullets = bullets;
	}
	
	/**
	 * Decrements the number of bullets of the gun.
	 */
	public void decrementBullets() {
		this.bullets--;
	}

	/**
	 * Returns a boolean indicating if the gun is active or not.
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Sets the gun state, if is active or not.
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		if(isActive != this.isActive) {
			this.isActive = isActive;
			bullets = maxBullets;
			setCurrentFrame(0);
			resetTimer();
			setTouched(false);
			setShaked(false);
		}
	}

	/**
	 * Returns a boolean indicating if the user device was shacked or not.
	 * @return the isShaked
	 */
	public boolean isShaked() {
		return isShaked;
	}

	/**
	 * Sets the shake state of the user device, if it was shacked or not.
	 * @param isShaked the isShaked to set
	 */
	public void setShaked(boolean isShaked) {
		this.isShaked = isShaked;
	}
	
	/**
	 * Returns a boolean indicating if the Animation was started or not.
	 * @return
	 */
	public boolean isStartAnimate() {
		return startAnimate;
	}
	
	/**
	 * Sets the startAnimate state, is it was started or not.
	 * @param startAnimate
	 */
	public void setStartAnimate(boolean startAnimate) {
		this.startAnimate = startAnimate;
	}

	/**
	 * Gets the damage value.
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Sets the damage value.
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

}

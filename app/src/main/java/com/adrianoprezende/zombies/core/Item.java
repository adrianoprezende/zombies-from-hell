package com.adrianoprezende.zombies.core;

import android.content.res.Resources;

/**
 * This abstract class must be inherited and implemented if the desired object is an item.
 * @author Adriano Pereira Rezende
 */
public abstract class Item extends InteractiveObject {
	
	private boolean show;
	
	public static final int MAX_TIME_TO_SHOW = 3;
	
	/**
	 * Item Default Constructor Method.
	 * Creates a new item game object.
	 * @param res
	 * @param id
	 */
	public Item(Resources res, int id) {
		super(res,id);
		show = false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.InteractiveObject#handleActionDown(int, int)
	 */
	@Override
	public void handleActionDown(int eventX, int eventY) {
		super.handleActionDown(eventX, eventY);
		if(isTouched()) {
			resetTimer();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.InteractiveObject#animate()
	 */
	public void animate() {
		
		if(!isTouched() && isShow()) {
			//starts the timer
			if(!isTimeStarted()) {
				startTimer();
			}
			
			setElapsedTime((System.currentTimeMillis() - getStartTime())/1000);
			
			if(getElapsedTime() >= MAX_TIME_TO_SHOW) {
				setShow(false);
				resetTimer();
			}
		}
				

	}


	/**
	 * Returns a boolean indicating if the item is been showing or not.
	 * @return the show
	 */
	public boolean isShow() {
		return show;
	}


	/**
	 * Sets the state of the item, if the item is been showing or not.
	 * @param show the show to set
	 */
	public void setShow(boolean show) {
		this.show = show;
	}

}

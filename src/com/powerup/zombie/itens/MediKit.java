package com.powerup.zombie.itens;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.powerup.zombie.characters.Player;
import com.powerup.zombie.core.Item;
import com.powerup.zombie.main.R;

/**
 * Medikit Class.
 * @author Adriano Pereira Rezende
 */
public class MediKit extends Item {
	
	private static final int RESTORE_HEALTH = 2;
	private static final int MEDI_KIT = R.drawable.medikit;
	
	//Default constructor
	public MediKit(Resources res) {
		super(res,MEDI_KIT);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.InteractiveObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		if(!isTouched() && isShow()) {
			super.draw(canvas);
		}
	}
	
	/**
	 * Handles all touches on screen from user, setting if the medikit was touched or not. 
	 * @param player
	 * @param eventX
	 * @param eventY
	 */
	public void handleActionDown(Player player, int eventX, int eventY) {
		super.handleActionDown(eventX, eventY);
		if(isShow()) {
			if(isTouched()) {
				player.incrementHealth(RESTORE_HEALTH);
				setShow(false);
				setTouched(false);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.powerup.zombie.core.Item#animate()
	 */
	@Override
	public void animate() {
		super.animate();
	} 
	
	
}

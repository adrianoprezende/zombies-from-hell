package com.adrianoprezende.zombies.itens;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.adrianoprezende.zombies.characters.Player;
import com.adrianoprezende.zombies.core.Item;
import com.adrianoprezende.zombies.main.HeadUpDisplay;
import com.adrianoprezende.zombies.main.R;
import com.adrianoprezende.zombies.main.SoundManager;

/**
 * Grenade Class.
 * @author Adriano Pereira Rezende
 */
public class Grenade extends Item {
	
	private static final int GRENADE = R.drawable.grenade_medium;
	private static final int BIG_GRENADE = R.drawable.grenade_big;
	
	private boolean isTriggered;
	
	//Default constructor
	public Grenade(Resources res) {
		super(res,GRENADE);
		isTriggered = false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adrianoprezende.zombies.core.InteractiveObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		if(isShow()) {
			super.draw(canvas);
		}
	}
	
	/**
	 * Handles all touches on screen from user, setting if the grenade was triggered or not. 
	 * @param player
	 * @param eventX
	 * @param eventY
	 * @return boolean
	 */
	public boolean handleActionDown(Player player, int eventX, int eventY) {
		super.handleActionDown(eventX, eventY);
		if(isShow() && !isTriggered) {
			if(isTouched()) {
				isTriggered = true;
				triggered();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Trigger the grenade
	 */
	private void triggered() {
		SoundManager.playSound(SoundManager.GRENADE_TRIGGER_FX);
		this.changeBitmap(BIG_GRENADE);
		this.setX(HeadUpDisplay.getScreenWidth()/2);
		this.setY(HeadUpDisplay.getScreenHeight()/2);
	}
	
	/**
	 * Throws the grenade
	 */
	public void throwGrenade() {
		isTriggered = false;
		setShow(false);
		setTouched(false);
		this.changeBitmap(GRENADE);
	}
	
	
}

package com.powerup.zombie.characters;

import android.content.res.Resources;

import com.powerup.zombie.core.Monster;
import com.powerup.zombie.main.R;
import com.powerup.zombie.main.SoundManager;

/**
 * Zombie class.
 * @author Adriano Pereira Rezende
 */
public class Zombie extends Monster {
	
	private static final int BECKY_ZOMBIE = R.drawable.becky;
	private static final int ZOMBILADIN_ZOMBIE = R.drawable.zombiladin;
	private static final int FRANS_ZOMBIE = R.drawable.frans;
	private static final int HANS_ZOMBIE = R.drawable.hans;
	private static final int ROXIE_ZOMBIE = R.drawable.roxie;
	private static final int JIMMY_ZOMBIE = R.drawable.jimmy;
	private static final int MEATCAKE_ZOMBIE = R.drawable.meatcake;
	
	@SuppressWarnings("unused")
	private static final int BECKY_ZOMBIE_HURT = R.drawable.becky_hurt;
	@SuppressWarnings("unused")
	private static final int ZOMBILADIN_ZOMBIE_HURT = R.drawable.zombiladin_hurt;
	@SuppressWarnings("unused")
	private static final int FRANS_ZOMBIE_HURT = R.drawable.frans_hurt;
	@SuppressWarnings("unused")
	private static final int HANS_ZOMBIE_HURT = R.drawable.hans_hurt;
	@SuppressWarnings("unused")
	private static final int ROXIE_ZOMBIE_HURT = R.drawable.roxie_hurt;
	@SuppressWarnings("unused")
	private static final int JIMMY_ZOMBIE_HURT = R.drawable.jimmy_hurt;
	@SuppressWarnings("unused")
	private static final int MEATCAKE_ZOMBIE_HURT = R.drawable.meatcake_hurt;
	
	private static final String MONSTER_NOISE_ID = SoundManager.MONSTER_HURTED_FX;
	private static final int MAX_HEALTH = 4;
	private static final float ZOMBIE_SPEED = 4;
	private static final int ZOMBIE_ATACK_POWER = 1;
	private static final int ZOMBIE_SCORE_POINTS = 1;

	/**
	 * Method constructor, that returns a new instance of a Zombie
	 * @param res
	 * @param keyMap
	 * @param yRange
	 */
	public Zombie(Resources res, String keyMap, float yRange) {
		super(res, keyMap, yRange, raffleBitmap(), MONSTER_NOISE_ID, MAX_HEALTH, ZOMBIE_SPEED, ZOMBIE_ATACK_POWER, ZOMBIE_SCORE_POINTS);
	}
	
	/**
	 * Method that returns a random image of a zombie.
	 * @return zombieID
	 */
	private static int raffleBitmap() {
		int zombieID = JIMMY_ZOMBIE;
		
		switch(rand.nextInt(7)) {
			case 0 : zombieID = BECKY_ZOMBIE; break;
			case 1 : zombieID = FRANS_ZOMBIE; break;
			case 2 : zombieID = HANS_ZOMBIE; break;
			case 3 : zombieID = JIMMY_ZOMBIE; break;
			case 4 : zombieID = MEATCAKE_ZOMBIE; break;
			case 5 : zombieID = ROXIE_ZOMBIE; break;
			case 6 : zombieID = ZOMBILADIN_ZOMBIE; break;
		}
		return zombieID;
		
	}
	
}

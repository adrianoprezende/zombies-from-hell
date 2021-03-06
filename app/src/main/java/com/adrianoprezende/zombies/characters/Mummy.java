package com.adrianoprezende.zombies.characters;

import android.content.res.Resources;

import com.adrianoprezende.zombies.core.Gun;
import com.adrianoprezende.zombies.core.Monster;
import com.adrianoprezende.zombies.main.R;
import com.adrianoprezende.zombies.main.SoundManager;
import com.adrianoprezende.zombies.weapons.Rocket;
import com.adrianoprezende.zombies.weapons.ShotGun;

/**
 * Mummy class.
 * @author Adriano Pereira Rezende
 */
public class Mummy extends Monster {

	private static final int MUMMY = R.drawable.mummy;

	@SuppressWarnings("unused")
	private static final int MUMMY_HURT = R.drawable.mummy_hurt;

	private static final String MONSTER_NOISE_ID = SoundManager.MONSTER_HURTED_FX;
	private static final int DEFAULT_HEALTH = 8;
	private static final float MUMMY_SPEED = 4;
	private static final int MUMMY_ATACK_POWER = 1;
	private static final int MUMMY_SCORE_POINTS = 2;

	/**
	 * Method constructor, that returns a new instance of a Mummy
	 * @param res
	 * @param keyMap
	 * @param yRange
	 * @param gun
	 */
	public Mummy(Resources res, String keyMap, float yRange, Gun gun) {
		super(res, keyMap, yRange, MUMMY, MONSTER_NOISE_ID, DEFAULT_HEALTH,
				MUMMY_SPEED, MUMMY_ATACK_POWER, MUMMY_SCORE_POINTS);
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private static int adjustHealth(Gun gun) {
		int health = DEFAULT_HEALTH;
		if(gun instanceof ShotGun) {
			health = 12;
		} else if(gun instanceof Rocket) {
			health = 18;
		}
		
		return health;
		
	}

}

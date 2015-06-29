package com.adrianoprezende.zombies.characters;

import android.content.res.Resources;

import com.adrianoprezende.zombies.core.Gun;
import com.adrianoprezende.zombies.core.Monster;
import com.adrianoprezende.zombies.main.R;
import com.adrianoprezende.zombies.main.SoundManager;
import com.adrianoprezende.zombies.weapons.Rocket;
import com.adrianoprezende.zombies.weapons.ShotGun;

/**
 * Vampire class.
 * @author Adriano Pereira Rezende
 */
public class Vampire extends Monster {

	private static final int VAMPIRE = R.drawable.vampire;

	@SuppressWarnings("unused")
	private static final int VAMPIRE_HURT = R.drawable.vampire_hurt;

	private static final String MONSTER_NOISE_ID = SoundManager.MONSTER_HURTED_FX;
	private static final int DEFAULT_HEALTH = 10;
	private static final float VAMPIRE_SPEED = 8;
	private static final int VAMPIRE_ATACK_POWER = 1;
	private static final int VAMPIRE_SCORE_POINTS = 5;

	/**
	 * Method constructor, that returns a new instance of a Vampire
	 * @param res
	 * @param keyMap
	 * @param yRange
	 * @param gun
	 */
	public Vampire(Resources res, String keyMap, float yRange, Gun gun) {
		super(res, keyMap, yRange, VAMPIRE, MONSTER_NOISE_ID, DEFAULT_HEALTH,
				VAMPIRE_SPEED, VAMPIRE_ATACK_POWER, VAMPIRE_SCORE_POINTS);
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private static int adjustHealth(Gun gun) {
		int health = DEFAULT_HEALTH;
		if(gun instanceof ShotGun) {
			health = 15;
		} else if(gun instanceof Rocket) {
			health = 25;
		}
		
		return health;
		
	}

}

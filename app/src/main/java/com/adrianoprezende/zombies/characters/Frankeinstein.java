package com.adrianoprezende.zombies.characters;

import android.content.res.Resources;

import com.adrianoprezende.zombies.core.Gun;
import com.adrianoprezende.zombies.core.Monster;
import com.adrianoprezende.zombies.main.R;
import com.adrianoprezende.zombies.main.SoundManager;
import com.adrianoprezende.zombies.weapons.Rocket;
import com.adrianoprezende.zombies.weapons.ShotGun;

/**
 * Frankeinstein class.
 * @author Adriano Pereira Rezende
 */
public class Frankeinstein extends Monster {

	private static final int FRANKEINSTEIN = R.drawable.frankeinstein;

	@SuppressWarnings("unused")
	private static final int FRANKEINSTEIN_HURT = R.drawable.frankeinstein_hurt;

	private static final String MONSTER_NOISE_ID = SoundManager.MONSTER_HURTED_FX;
	private static final float FRANKEINSTEIN_SPEED = 4;
	private static final int FRANKEINSTEIN_ATACK_POWER = 2;
	private static final int FRANKEINSTEIN_SCORE_POINTS = 3;
	private static final int DEFAULT_HEALTH = 10;
	
	/**
	 * Method constructor, that returns a new instance of a Frankeinstein
	 * @param res
	 * @param keyMap
	 * @param yRange
	 * @param gun
	 */
	public Frankeinstein(Resources res, String keyMap, float yRange, Gun gun) {
		super(res, keyMap, yRange, FRANKEINSTEIN, MONSTER_NOISE_ID, DEFAULT_HEALTH,
				FRANKEINSTEIN_SPEED, FRANKEINSTEIN_ATACK_POWER,
				FRANKEINSTEIN_SCORE_POINTS);
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

package com.adrianoprezende.zombies.characters;

import android.content.res.Resources;

import com.adrianoprezende.zombies.core.Gun;
import com.adrianoprezende.zombies.core.Monster;
import com.adrianoprezende.zombies.main.R;
import com.adrianoprezende.zombies.main.SoundManager;
import com.adrianoprezende.zombies.weapons.Rocket;
import com.adrianoprezende.zombies.weapons.ShotGun;

/**
 * Werewolf class.
 * @author Adriano Pereira Rezende
 */
public class Werewolf extends Monster {

	private static final int WEREWOLF = R.drawable.werewolf;

	@SuppressWarnings("unused")
	private static final int WEREWOLF_HURT = R.drawable.werewolf_hurt;

	private static final String MONSTER_NOISE_ID = SoundManager.MONSTER_HURTED_FX;
	private static final int DEFAULT_HEALTH = 6;
	private static final float WEREWOLF_SPEED = 10;
	private static final int WEREWOLF_ATACK_POWER = 1;
	private static final int WEREWOLF_SCORE_POINTS = 2;

	/**
	 * Method constructor, that returns a new instance of a Werewolf
	 * @param res
	 * @param keyMap
	 * @param yRange
	 * @param gun
	 */
	public Werewolf(Resources res, String keyMap, float yRange, Gun gun) {
		super(res, keyMap, yRange, WEREWOLF, MONSTER_NOISE_ID, DEFAULT_HEALTH,
				WEREWOLF_SPEED, WEREWOLF_ATACK_POWER, WEREWOLF_SCORE_POINTS);
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private static int adjustHealth(Gun gun) {
		int health = DEFAULT_HEALTH;
		if(gun instanceof ShotGun) {
			health = 10;
		} else if(gun instanceof Rocket) {
			health = 20;
		}
		
		return health;
		
	}

}

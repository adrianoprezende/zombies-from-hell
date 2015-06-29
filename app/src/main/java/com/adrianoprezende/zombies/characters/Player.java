package com.adrianoprezende.zombies.characters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.adrianoprezende.zombies.core.InteractiveSpriteObject;
import com.adrianoprezende.zombies.core.Monster;
import com.adrianoprezende.zombies.main.R;

/**
 * Player class.
 * @author Adriano Pereira Rezende
 */
public class Player extends InteractiveSpriteObject {
	
	private int health;
	
	private static final int MAX_HEALTH = 6;
	
	private Bitmap brain0;
	private Bitmap brain1;
	private Bitmap brain2;
	private Bitmap brain3;
	private Bitmap brain4;
	private Bitmap brain5;
	private Bitmap brain6;
	
	private static final int SCORE_DECREMENT = 10;
	
	private int comboKillsCount;
	private int comboMultiplier;
	private boolean gotCombo1 = false;
	private boolean gotCombo2 = false;
	private boolean gotCombo3 = false;
	
	private int score;
	private int zombieKills;
	private int frankeinsteinKills;
	private int vampireKills;
	private int mummyKills;
	private int werewolfKills;
	private int innocentKills;
	private int playTime;
	
	private int zombieKills_OLD;
	private int frankeinsteinKills_OLD;
	private int vampireKills_OLD;
	private int mummyKills_OLD;
	private int werewolfKills_OLD;
	private int innocentKills_OLD;
	
	private boolean gotZombieKills = false;
	private boolean gotFrankeinsteinKills = false;
	private boolean gotVampireKills = false;
	private boolean gotMummyKills = false;
	private boolean gotWerewolfKills = false;
	private boolean gotPlayTimeAchievement = false;
	
	
	/**
	 * The default constructor, that returns a new instance of a human.
	 * @param res
	 */
	public Player(Resources res) {
		super();
		health = MAX_HEALTH;
		score = 0;
		zombieKills = 0;
		frankeinsteinKills = 0;
		vampireKills = 0;
		mummyKills = 0;
		werewolfKills = 0;
		innocentKills = 0;
		comboKillsCount = 0;
		comboMultiplier = 1;
		brain0 = BitmapFactory.decodeResource(res, R.drawable.brain0);
		brain1 = BitmapFactory.decodeResource(res, R.drawable.brain1);
		brain2 = BitmapFactory.decodeResource(res, R.drawable.brain2);
		brain3 = BitmapFactory.decodeResource(res, R.drawable.brain3);
		brain4 = BitmapFactory.decodeResource(res, R.drawable.brain4);
		brain5 = BitmapFactory.decodeResource(res, R.drawable.brain5);
		brain6 = BitmapFactory.decodeResource(res, R.drawable.brain6);
		setFrameVector(new Bitmap[]{brain0, brain1, brain2, brain3, brain4, brain5, brain6});
	}
	
	
	/**
	 * Sets a damage on the player, decrementing his health according with the monster attack power
	 */
	public void getHurt(Monster monster) {
		if(health > 0 && getCurrentFrame() < getFrameVector().length) {
			health -= monster.getAtackPower();
			incrementCurrentFrame();
		}
	}

	/**
	 * Gets the player's health.
	 * @return health
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Sets the player's health.
	 * @param health
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * Increments the player's health.
	 * @param value
	 */
	public void incrementHealth(int value) {
		if(health < MAX_HEALTH && getCurrentFrame() > 0) {
			for(int x=0; x < value; x++) {
				health++;
				decrementCurrentFrame();
				if(health == MAX_HEALTH || getCurrentFrame() == 0) {
					break;
				}
			}
			
		}
	}

	/**
	 * Gets the player's score.
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Increments the player's score, according with the monster value points and accumulated combo.
	 * @param score the score to set
	 */
	public void incrementScore(Monster monster) {
		this.score += (monster.getValueScorePoints() * comboMultiplier);
		
		if(monster instanceof Zombie) {
			this.zombieKills++;
		} else if(monster instanceof Werewolf) {
			this.werewolfKills++;
		} else if(monster instanceof Frankeinstein) {
			this.frankeinsteinKills++;
		} else if(monster instanceof Mummy) {
			this.mummyKills++;
		} else if(monster instanceof Vampire) {
			this.vampireKills++;
		}
		
		//increments the combo count when player kills a monster
		this.comboKillsCount++;
		
	}
	
	/**
	 * Dercrements the player's score.
	 */
	public void decrementScore() {
		if((score - SCORE_DECREMENT) < 0) {
			score = 0;
		} else {
			score -= SCORE_DECREMENT;
		}
		this.innocentKills++;
		
		//sets combo count to 0
		setComboKillsCount(0);
		setGotCombo1(false);
		setGotCombo2(false);
		setGotCombo3(false);
	}
	
	/*
	 * GETTERS 
	 * 			AND 
	 * 				SETTERS
	 */
	
	public int getZombieKills() {
		return zombieKills;
	}

	public int getFrankeinsteinKills() {
		return frankeinsteinKills;
	}

	public int getVampireKills() {
		return vampireKills;
	}

	public int getMummyKills() {
		return mummyKills;
	}

	public int getWerewolfKills() {
		return werewolfKills;
	}
	
	public int getInnocentKills() {
		return innocentKills;
	}

	public boolean isGotZombieKills() {
		return gotZombieKills;
	}

	public void setGotZombieKills(boolean gotZombieKills) {
		this.gotZombieKills = gotZombieKills;
	}

	public boolean isGotFrankeinsteinKills() {
		return gotFrankeinsteinKills;
	}

	public void setGotFrankeinsteinKills(boolean gotFrankeinsteinKills) {
		this.gotFrankeinsteinKills = gotFrankeinsteinKills;
	}

	public boolean isGotVampireKills() {
		return gotVampireKills;
	}

	public void setGotVampireKills(boolean gotVampireKills) {
		this.gotVampireKills = gotVampireKills;
	}

	public boolean isGotMummyKills() {
		return gotMummyKills;
	}

	public void setGotMummyKills(boolean gotMummyKills) {
		this.gotMummyKills = gotMummyKills;
	}

	public boolean isGotWerewolfKills() {
		return gotWerewolfKills;
	}

	public void setGotWerewolfKills(boolean gotWerewolfKills) {
		this.gotWerewolfKills = gotWerewolfKills;
	}

	public int getComboKillsCount() {
		return comboKillsCount;
	}

	public void setComboKillsCount(int comboKillsCount) {
		this.comboKillsCount = comboKillsCount;
	}

	public boolean isGotCombo1() {
		return gotCombo1;
	}

	public void setGotCombo1(boolean gotCombo1) {
		this.gotCombo1 = gotCombo1;
	}

	public boolean isGotCombo2() {
		return gotCombo2;
	}

	public void setGotCombo2(boolean gotCombo2) {
		this.gotCombo2 = gotCombo2;
	}

	public boolean isGotCombo3() {
		return gotCombo3;
	}

	public void setGotCombo3(boolean gotCombo3) {
		this.gotCombo3 = gotCombo3;
	}

	public boolean isGotPlayTimeAchievement() {
		return gotPlayTimeAchievement;
	}

	public void setGotPlayTimeAchievement(boolean gotPlayTimeAchievement) {
		this.gotPlayTimeAchievement = gotPlayTimeAchievement;
	}

	public int getComboMultiplier() {
		return comboMultiplier;
	}

	public void setComboMultiplier(int comboMultiplier) {
		this.comboMultiplier = comboMultiplier;
	}

	public void setZombieKills(int zombieKills) {
		this.zombieKills = zombieKills;
	}

	public void setFrankeinsteinKills(int frankeinsteinKills) {
		this.frankeinsteinKills = frankeinsteinKills;
	}

	public void setVampireKills(int vampireKills) {
		this.vampireKills = vampireKills;
	}

	public void setMummyKills(int mummyKills) {
		this.mummyKills = mummyKills;
	}

	public void setWerewolfKills(int werewolfKills) {
		this.werewolfKills = werewolfKills;
	}

	public void setInnocentKills(int innocentKills) {
		this.innocentKills = innocentKills;
	}

	public int getZombieKills_OLD() {
		return zombieKills_OLD;
	}

	public void setZombieKills_OLD(int zombieKills_OLD) {
		this.zombieKills_OLD = zombieKills_OLD;
	}

	public int getFrankeinsteinKills_OLD() {
		return frankeinsteinKills_OLD;
	}

	public void setFrankeinsteinKills_OLD(int frankeinsteinKills_OLD) {
		this.frankeinsteinKills_OLD = frankeinsteinKills_OLD;
	}

	public int getVampireKills_OLD() {
		return vampireKills_OLD;
	}

	public void setVampireKills_OLD(int vampireKills_OLD) {
		this.vampireKills_OLD = vampireKills_OLD;
	}

	public int getMummyKills_OLD() {
		return mummyKills_OLD;
	}

	public void setMummyKills_OLD(int mummyKills_OLD) {
		this.mummyKills_OLD = mummyKills_OLD;
	}

	public int getWerewolfKills_OLD() {
		return werewolfKills_OLD;
	}

	public void setWerewolfKills_OLD(int werewolfKills_OLD) {
		this.werewolfKills_OLD = werewolfKills_OLD;
	}

	public int getInnocentKills_OLD() {
		return innocentKills_OLD;
	}

	public void setInnocentKills_OLD(int innocentKills_OLD) {
		this.innocentKills_OLD = innocentKills_OLD;
	}
	
	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	@Override
	@Deprecated
	public void animate() {
	}
	
}

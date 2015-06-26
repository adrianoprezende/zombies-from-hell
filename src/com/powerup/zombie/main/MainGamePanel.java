package com.powerup.zombie.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerup.zombie.characters.Frankeinstein;
import com.powerup.zombie.characters.Humans;
import com.powerup.zombie.characters.Mummy;
import com.powerup.zombie.characters.Player;
import com.powerup.zombie.characters.Vampire;
import com.powerup.zombie.characters.Werewolf;
import com.powerup.zombie.characters.Zombie;
import com.powerup.zombie.core.Gun;
import com.powerup.zombie.core.InteractiveObject;
import com.powerup.zombie.core.Item;
import com.powerup.zombie.core.Monster;
import com.powerup.zombie.itens.Grenade;
import com.powerup.zombie.itens.MediKit;
import com.powerup.zombie.providers.helpers.Score;
import com.powerup.zombie.weapons.Magnum;
import com.powerup.zombie.weapons.Rocket;
import com.powerup.zombie.weapons.ShotGun;

/**
 * MainGamePanel class contains all In-Game logic, after it starts.
 * @author Adriano Pereira Rezende
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
	
	private boolean touchLocked = false;
	
	/**
     * Current mode of application: READY to run, RUNNING, or you have already
     * lost. static final ints are used instead of an enum for performance
     * reasons.
     */
    public static final int PAUSE = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int STOP = 3;
    private static int mMode = READY;
	
	// Constants
    private static final int RANGE_PERCENT = 5;
	private static final int MAX_NUM_MONSTERS = 3;
	
	// The difficulty level (Initially 1)
	private int difficultyLevel = 1;
	
	private MainThread thread;
	private Typeface font;
	
	private Paint debug = new Paint();
	
	// the fps to be displayed
	private String avgFps;

	private Bitmap bgImage;
	private final static int BGSTAGE_1 = R.drawable.bg_stage1;
	private final static int BGSTAGE_2 = R.drawable.bg_stage2;
	private final static int BGSTAGE_3 = R.drawable.bg_stage3;
	
	private long seed  = System.currentTimeMillis();
	Random rand = new Random(seed);
	
	private Timer timerBeholder = new Timer(true);
	private long beholderDelay = 1000;
	private long beholderPeriod = 2000;
	private Timer timerDifficulty = new Timer(true);
	
	private List<Monster> listMonsters = new ArrayList<Monster>();
	private HashMap<String, InteractiveObject> screenPositionMap = new HashMap<String, InteractiveObject>();
	
	private Magnum magnum;
	private ShotGun shotGun;
	private Rocket rocket;
	private List<Gun> gunList = new ArrayList<Gun>();
	private int currentGun;
	
	public static final int MAGNUM = 0;
	public static final int SHOTGUN = 1;
	public static final int ROCKET = 2;
	
	private Humans people;
	
	private Player player;
	private MediKit mediKit;
	private Grenade grenade;
	
	private boolean grenadeTriggered = false;
	
	private final Dialog achievementDialog = new Dialog((Activity)getContext());
	
	private SensorManager sensorManager;
	
	/**
	 * The default method constructor
	 * @param context
	 */
	public MainGamePanel(Context context) {
		super(context);
		 // adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		// create the game loop thread
		thread = new MainThread(this);
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		// set the font type
		font = Typeface.createFromAsset(context.getAssets(), "ZOMBIE.TTF");
		// Loads the background image
		bgImage = BitmapFactory.decodeResource(getResources(), raffleScenario());
		magnum = new Magnum(getResources());
		shotGun = new ShotGun(getResources());
		rocket = new Rocket(getResources());
		currentGun = 0;
		populateGunList();
		
		player = new Player(getResources());
		
		checksAchievements(player);
		
		mediKit = new MediKit(getResources());
		grenade = new Grenade(getResources());
		setMainMode(RUNNING);
		debug.setColor(Color.WHITE);
		
		sensorManager = (SensorManager)this.getContext().getSystemService(Context.SENSOR_SERVICE);
		

		/*	More sensor speeds (taken from api docs)
		    SENSOR_DELAY_FASTEST get sensor data as fast as possible
		    SENSOR_DELAY_GAME	rate suitable for games
		 	SENSOR_DELAY_NORMAL	rate (default) suitable for screen orientation changes
		*/
		
	}
	
	/**
	 * Selects Randomly the background image of stage
	 * @return
	 */
	private int raffleScenario() {
		switch(rand.nextInt(3)) {
			case 0 : return BGSTAGE_1;
			case 1 : return BGSTAGE_2;
			case 2 : return BGSTAGE_3;
			default : return BGSTAGE_1;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.SurfaceView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//setMeasuredDimension(getWidth(), getHeight());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//sets the magnum coordinates
		magnum.setCoordinates(getWidth()/6 *5, getHeight()/10 *9);
		magnum.setScreenMeasures(getWidth(), getHeight());
		shotGun.setCoordinates(getWidth()/6 *5, getHeight()/9 *8);
		shotGun.setScreenMeasures(getWidth(), getHeight());
		rocket.setCoordinates(getWidth()/6 *5, getHeight()/9 *8);
		rocket.setScreenMeasures(getWidth(), getHeight());
		
		//sets the playerCoordinates
		player.setCoordinates((getWidth()/10) * 9, (getHeight()/100) * 7);
		
		HeadUpDisplay.getInstance();
		HeadUpDisplay.initHeadUpDisplay(getWidth(), getHeight(), this.font);
		
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		
		//starts the game loop
		thread.setRunning(true);
		thread.start();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(thread.isAlive()) {
			thread.setRunning(false);
			boolean retry = true;
			while (retry) {
		        try {
		            thread.join();
		            retry = false;
		            releaseTimers();
		            sensorManager.unregisterListener(this);
		            sensorManager = null;
		        } catch (InterruptedException e) {
		            // we will try it again and again...
		        }
		    }
		}
		
	}
	
	
	/**
	 * Draws all In-Game objects
	 * @param canvas
	 * @param elapsedTime
	 */
	public void onDraw(Canvas canvas, long elapsedTime) {
		//draw the BackGround Image
		canvas.drawBitmap(bgImage, 0, 0, null);
		
		people.draw(canvas);

		synchronized (listMonsters) {
			for(Monster monster : listMonsters) {
				monster.draw(canvas);
			}
			
		}
		
		gunList.get(currentGun).draw(canvas);
		player.draw(canvas);
		mediKit.draw(canvas);
		grenade.draw(canvas);
		HeadUpDisplay.draw(canvas, elapsedTime, player.getScore());
		
	}	
	
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean getGrenade = false;
		
		if(!touchLocked) {	
			switch(event.getAction()) {
			
			//************CASE BEGIN***********************************************************
			case MotionEvent.ACTION_DOWN : 
				if(gunList.get(currentGun).getBullets() > 0) {
					people.handleActionDown((int)event.getX(), (int)event.getY());
					if(people.isTouched()) {
						player.decrementScore();
						people.setTouched(false);
						//If was the first innocent killed
						if(player.getInnocentKills() == 1) {
							achievementWindow(R.drawable.lady, "If you do, you will lose points and accumulated combos.", "Don't kill innocent people!");
						}
					}
					
					synchronized (listMonsters) {
						for(Monster monster : listMonsters) {
							monster.handleActionDown((int)event.getX(), (int)event.getY(),gunList.get(currentGun));
							// check if the zombie was touched, and increase the player`s score
							if (monster.isTouched()) {
								player.incrementScore(monster);
							}
						}
						
					}
				}
				
				mediKit.handleActionDown(player, (int)event.getX(), (int)event.getY());
				getGrenade = grenade.handleActionDown(player, (int)event.getX(), (int)event.getY());
				if(getGrenade) {
					touchLocked = true;
					grenadeTriggered = true;
					gunList.get(currentGun).reload();
				}
				gunList.get(currentGun).handleActionDown((int)event.getX(), (int)event.getY());
				if(currentGun == SHOTGUN) {
					touchLocked = true;
					
					Handler handler = null;
			        handler = new Handler(); 
			        handler.postDelayed(new Runnable(){ 
			             public void run(){
			            	 touchLocked = false;
			             }
			        }, 100);
				}
				break;
			//*************CASE END**********************************************************
			
			} // end switch
		} // end if
		
		return true;
		
	}
	
	
	/**
	 * Animates all In-Game Objects
	 */
	private void animate() {
		
		people.animate();
		
		synchronized (listMonsters) {
			for(Monster monster : listMonsters) {
				// Check if the zombie attacked or not
				monster.animate();
				if(monster.isToHurt()) {
					player.getHurt(monster);
					monster.setToHurt(false);
				}
			}
		}
		mediKit.animate();
		grenade.animate();
		gunList.get(currentGun).animate();
	}
	
	
	/**
	 * Starts the game, firing all thread timers
	 */
	public void startGame() {
		beholder();
		chooseWeapon(player);
		increaseDifficulty();
	}

	/**
	 * Method responsible to update the game state, before drawing.
	 */
	public void updateGame() {
		if(getMainMode() == RUNNING) {
			showPeople();
			killZombie();
			showItem();
			animate();
			isAchievementUnlocked(player);
			chooseWeapon(player);
		} else if(getMainMode() == PAUSE) {
			try {
				pauseMonsters();
				synchronized (this) {
					this.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if(getMainMode() == STOP) {
			//do nothing
		}  
		
	}
	

	/**
	 * Method that remove a monster that was touched
	 */
	private void killZombie() {
		int x;
		
		synchronized (listMonsters) {	
			for(x = 0; x < listMonsters.size(); x++) {
				if(listMonsters.get(x).isTouched()) {
					synchronized (screenPositionMap) {
						screenPositionMap.remove((listMonsters.get(x).getKey()));
					}
					listMonsters.remove(x);
				}
			}
				
		} // end synchronize
		
	}
	
	/**
	 * Game Over
	 */
	public void gameOver() {
		if(player != null) {
			if(player.getHealth() <= 0) {
				player.setHealth(6);
				player.setCurrentFrame(0);
				thread.setRunning(false);
				sensorManager.unregisterListener(this);
				releaseTimers();
				Intent EndGameIntent = new Intent((Activity)getContext(), EndGameView.class);
				((Activity)getContext()).setResult(1, EndGameIntent);
				recordScore();
				((Activity)getContext()).finish();
			}
		}
	}
	
	/**
	 * Insert a new record in the content provider
     */
	 protected void recordScore() {
		 int time = (int)thread.getElapsedTime();
		 ContentValues values = new ContentValues();
		 values.put(Score.TOTAL, player.getScore());
		 values.put(Score.TIME, time);
		 
		 values.put(Score.ZOMBIE, player.getZombieKills() - player.getZombieKills_OLD());
		 values.put(Score.WEREWOLF, player.getWerewolfKills() - player.getWerewolfKills_OLD());
		 values.put(Score.FRANKEINSTEIN, player.getFrankeinsteinKills() - player.getFrankeinsteinKills_OLD());
		 values.put(Score.MUMMY, player.getMummyKills() - player.getMummyKills_OLD());
		 values.put(Score.VAMPIRE, player.getVampireKills() - player.getVampireKills_OLD());
		 values.put(Score.INNOCENT, player.getInnocentKills() - player.getInnocentKills_OLD());

		 ((Activity)getContext()).getContentResolver().insert(
		      Score.CONTENT_URI, values);
	 } 
	
	
	/**
	 * Create a monster and add it to HashMap using
	 * new coordinates, if another has killed by touching the screen
	 * and past 2 seconds.
	 */
	private void beholder() {
		
		if(timerBeholder != null) {
			//starts the timer
			timerBeholder.scheduleAtFixedRate(new BeholderRoutine(), beholderDelay, beholderPeriod);
		}
			
	}
	
	//InnerClass Beholder Routine
	class BeholderRoutine extends TimerTask {

		@Override
		public void run() {
			
			synchronized (listMonsters) {
				for(int i=0; i < difficultyLevel; i++) {
					if(listMonsters.size() < MAX_NUM_MONSTERS) {
						Monster monster = chooseMonster();
						synchronized (screenPositionMap) {
							screenPositionMap.put(monster.getKey(), monster);
						}
						listMonsters.add(monster);
					}
				}
				
			}
			
		} // end run
		
	} // end InnerClass
	
	/**
	 * Cancel, purge and renew the timerBeholder thread
	 */
	private void renewTimerBeholder() {
		timerBeholder.cancel();
		timerBeholder.purge();
		timerBeholder = new Timer(true);
	}
	
	/**
	 * Increases game difficulty at each 60 seconds.
	 */
	private void increaseDifficulty() {
		
		if(timerDifficulty != null) {
			//starts the timer
			timerDifficulty.scheduleAtFixedRate(new IncreaseDifficultyRoutine(), 60000, 60000);
		}
			
	}
	
	//InnerClass IncreaseDifficulty Routine
	class IncreaseDifficultyRoutine extends TimerTask {

		@Override
		public void run() {
			
			synchronized (timerBeholder) {
				if(beholderDelay > 500) {
					beholderDelay -= 250;
				}
				if(beholderPeriod > 500) {
					beholderPeriod -= 250;
				}
				//Increases the difficulty level
				if(difficultyLevel < MAX_NUM_MONSTERS) {
					difficultyLevel++;
				}
				renewTimerBeholder();
				beholder();
			}
			
		} // end run
		
	} // end InnerClass
	
	
	/**
	 * Reset the action time of the monsters
	 */
	private void pauseMonsters() {
		synchronized (listMonsters) {
			for(Monster monster : listMonsters) {
				monster.resetTimer();
			}
		}
	}
	
	/**
	 * Returns a random monster to be placed on game. 
	 * @return Monster
	 */
	private Monster chooseMonster() {
		
		if(currentGun != MAGNUM) {
			int i = rand.nextInt(500);
			
			if(i > 350 && i <= 430) {
				Werewolf werewolf = new Werewolf(getResources(), getCoordinates(), calcRangeHeight(), gunList.get(currentGun));
				return werewolf;
			} else if(i > 430 && i <= 470) {
				Mummy mummy = new Mummy(getResources(), getCoordinates(), calcRangeHeight(), gunList.get(currentGun));
				return mummy;
			} else if(i > 470 && i <= 490) {
				Frankeinstein frank = new Frankeinstein(getResources(), getCoordinates(), calcRangeHeight(), gunList.get(currentGun));
				return frank;
			} else if(i > 490 && i <= 500) {
				Vampire vampire = new Vampire(getResources(), getCoordinates(), calcRangeHeight(), gunList.get(currentGun));
				return vampire;
			}
			
		}
		
		Zombie zombie = new Zombie(getResources(), getCoordinates(), calcRangeHeight());
		return zombie;
		
	}
	
	
	/**
	 * Build an dialog window with the achievement message and image
	 * @param resId
	 * @param achievName
	 */
	private void achievementWindow(int rId, String text, String title) {
		
			final int resId = rId;
			final String achievBody = text;
			final String achievHeader = title;
			
			((Activity)getContext()).runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					synchronized (achievementDialog) {
						SoundManager.pauseMusic();
						SoundManager.playSound(SoundManager.FANFARE);
						
						achievementDialog.setContentView(R.layout.custom_dialog);
						achievementDialog.setTitle(achievHeader);
						
						TextView text = (TextView) achievementDialog.findViewById(R.id.PauseText);
						text.setText(achievBody);
						ImageView image = (ImageView) achievementDialog.findViewById(R.id.PauseImage);
						image.setImageResource(resId);
						achievementDialog.setCancelable(false);
						achievementDialog.show();
						pauseMonsters();
						MainGamePanel.setMainMode(MainGamePanel.PAUSE);
					}
					new Timer(true).schedule(new TimerTask() {
						
						@Override
						public void run() {
							achievementDialog.cancel();
							MainGamePanel.setMainMode(RUNNING);
							synchronized (MainGamePanel.this) {
								MainGamePanel.this.notifyAll();
							}
							renewTimerBeholder();
							beholder();
							SoundManager.resumeMusic();
						}
					}, 3000);
					
				}
			});
			
	}
	
	/**
	 * Method that checks if the player had unlocked an achievement
	 * @param player
	 */
	private void isAchievementUnlocked(Player player) {
		if(player.getZombieKills() == AchievementsRules.ZOMBIE_NUM_KILLS && !player.isGotZombieKills()) {
			achievementWindow(R.drawable.trophy_necklace_of_ears, "Necklace of Zombie Ears","ACHIEVEMENT UNLOCKED!");
			player.setGotZombieKills(true);
		}
		
		if(player.getMummyKills() == AchievementsRules.MUMMY_NUM_KILLS && !player.isGotMummyKills()) {
			achievementWindow(R.drawable.trophy_mummy, "Mummy Head","ACHIEVEMENT UNLOCKED!");
			player.setGotMummyKills(true);
		}
		
		if(player.getFrankeinsteinKills() == AchievementsRules.FRANKEINSTEIN_NUM_KILLS && !player.isGotFrankeinsteinKills()) {
			achievementWindow(R.drawable.trophy_monster_bust, "Frankeinstein Bust","ACHIEVEMENT UNLOCKED!");
			player.setGotFrankeinsteinKills(true);
		}
		
		if(player.getWerewolfKills() == AchievementsRules.WEREWOLF_NUM_KILLS && !player.isGotWerewolfKills()) {
			achievementWindow(R.drawable.trophy_werewolf_claws, "Werewolf Claws","ACHIEVEMENT UNLOCKED!");
			player.setGotWerewolfKills(true);
		}
		
		if(player.getVampireKills() == AchievementsRules.VAMPIRE_NUM_KILLS && !player.isGotVampireKills()) {
			achievementWindow(R.drawable.trophy_vampire_coffin, "Vampire Coffin","ACHIEVEMENT UNLOCKED!");
			player.setGotVampireKills(true);
		}
		
		if(player.getPlayTime() == AchievementsRules.PLAY_TIME && !player.isGotPlayTimeAchievement()) {
			achievementWindow(R.drawable.trophy_medal_of_honor, "Medal of Honor","ACHIEVEMENT UNLOCKED!");
			player.setGotPlayTimeAchievement(true);
		}
		
		
	}
	
	/**
	 * At each combo achievement, the current weapon changes
	 */
	private void chooseWeapon(Player player) {
		
		for(int i=0; i <= player.getComboKillsCount(); i++) {
			
			if(currentGun != MAGNUM
					&& player.getComboKillsCount() == AchievementsRules.COMBO_0) {
				player.setComboMultiplier(AchievementsRules.COMBO_MULTIPLIER_1);
				achievementWindow(R.drawable.revolver_icon, "Weapon Downgrade!","LOOSE");
				changeWeapon(MAGNUM);
				break;
			}
			
			if(!player.isGotCombo1()) {
				if(player.getComboKillsCount() == AchievementsRules.COMBO_1) {
					player.setComboMultiplier(AchievementsRules.COMBO_MULTIPLIER_2);
					player.setGotCombo1(true);
					achievementWindow(R.drawable.x2, "Points x2","COMBO");
					break;
				}
			}else if(!player.isGotCombo2()) {
						if(player.getComboKillsCount() == AchievementsRules.COMBO_2) {
							player.setGotCombo2(true);
							achievementWindow(R.drawable.shotgun_icon,"Weapon Upgrade!","COMBO 2X");
							changeWeapon(SHOTGUN);
							break;
						}
			}else if(!player.isGotCombo3()) {
						if(player.getComboKillsCount() == AchievementsRules.COMBO_3) {
							player.setGotCombo3(true);
							achievementWindow(R.drawable.rocket_icon,"Weapon Upgrade!", "COMBO 3X");
							changeWeapon(ROCKET);
							break;
						}
			}
			
		}
		
	}
	
	/**
	 * Changes the weapon, according to the nextWeapon parameter
	 * @param nextWeapon
	 */
	private void changeWeapon(int nextWeapon) {

				switch(nextWeapon) {
					case MAGNUM : // 0
						currentGun = MAGNUM;
						magnum.setActive(true);
						shotGun.setActive(false);
						rocket.setActive(false);
						break;
					case SHOTGUN : // 1
						currentGun = SHOTGUN;
						magnum.setActive(false);
						shotGun.setActive(true);
						rocket.setActive(false);
						break;
					case ROCKET : // 2
						currentGun = ROCKET;
						magnum.setActive(false);
						shotGun.setActive(false);
						rocket.setActive(true);
						break;
				}
				
			
		}
	
	/**
	 * Release all In-Game timer threads
	 */
	private void releaseTimers() {
		if(timerBeholder != null) {
			timerBeholder.cancel();
			timerBeholder.purge();
			timerBeholder = null;
		}
		if(timerDifficulty != null) {
			timerDifficulty.cancel();
			timerDifficulty.purge();
			timerDifficulty = null;
		}
	}
		
	/**
	 * Populates the gun list
	 */
	private void populateGunList() {
		gunList.add(this.magnum);
		gunList.add(this.shotGun);
		gunList.add(this.rocket);
	}
	
	/**
	 * Controls the innocent people appearance in-game
	 */
	private void showPeople() {
		//Instancia um humano e adiciona-o ao mapa de coordenadas da tela
		if(people == null) {
			people = new Humans(getResources(), getCoordinates());
			synchronized (screenPositionMap) {
				screenPositionMap.put(people.getKey(), people);
			}
			
		} else	if(!people.isActive()) {
			switch(rand.nextInt(200)) {
				case 3 : 
					synchronized (screenPositionMap) {
						if(screenPositionMap.containsKey(people.getKey())) {
							screenPositionMap.remove((people.getKey()));
						}
						
						people = new Humans(getResources(), getCoordinates());
						screenPositionMap.put(people.getKey(), people);
						people.setActive(true);
					}
			}
		} 
	}
	
	/**
	 * Shows or not, a random item in-game
	 */
	private void showItem() {
		
		switch(rand.nextInt(10)) {
			case 0 : showItemAux(mediKit); break;
			case 1 : showItemAux(grenade); break;
		}	
		
	}
	
	/**
	 * showItem() helper method
	 */
	private void showItemAux(Item item) {
		
		if(!item.isShow()) {
			
			if(rand.nextInt(100) == 4) {
				int x = 0;
				int y = 0;
				
				switch (rand.nextInt(2)) {
					case 0 : x = getWidth()/6; break;
					case 1 : x = (getWidth()/3) * 2; break;
				}
				
				switch (rand.nextInt(3)) {
					case 0 : y = getHeight()/4; break;
					case 1 : y = (getHeight()/4) * 2; break;
					case 2 : y = (getHeight()/4) * 3; break;
				}
				
				item.setCoordinates(x, y);
				item.setShow(true);
				item.setTouched(false);
			}
			
		}
	}
	
	/**
	 * Method to return the key to a new position of the HashMap for a monster.
	 * @return String
	 */
	private String getCoordinates() {
		String key = coordinatesAux();
		
		if(!screenPositionMap.isEmpty()) {
			while(screenPositionMap.containsKey(key)) {
				key = coordinatesAux();
			}
		}
		
		return key;
	}
	
	/**
	 * Method that randomly select two value based on screen and concatenates in a String to create a key
	 * To know the character position in the screen, just add the value Y + bitmap height + displacement(YLimit)
	 * @return String key
	 */
	private String coordinatesAux() {
		String x = String.valueOf(getWidth()/3);
		String y = String.valueOf(getHeight()/3 * 2);
		String key;
		
		switch (rand.nextInt(2)) {
			case 0 : x = String.valueOf((getWidth()/6)); break; // 100
			case 1 : x = String.valueOf((getWidth()/3) * 2); break; //350
		}
		
		switch (rand.nextInt(3)) {
			case 0 : y = String.valueOf((getHeight()/4)); break; //150
			case 1 : y = String.valueOf((getHeight()/4) * 2); break; //400
			case 2 : y = String.valueOf((getHeight()/4) * 3); break; //650
		}
		
		if(x.length() < 3) x = "0"+x;
		if(y.length() < 3) y = "0"+y;
		
		key = x.concat(y);
		
		return key;
	}
	
	/**
	 * Calculate the movement field that a monster will be, when animated
	 * @return the Range Height
	 */
	private float calcRangeHeight() {
		return (getHeight()/100) * RANGE_PERCENT;
	}
	
	/**
	 * Draws the FPS passed by parameter in the screen (Just to rate the FPS - DEBUG MODE)
	 * @param canvas
	 * @param fps
	 */
	@SuppressWarnings("unused")
	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			canvas.drawText(fps, (getWidth()/2) - (debug.getTextSize()), ((getHeight()/100) * 15.5f), debug);
		}
	}
	
	/**
	 * Sets the FPS Average (Just to rate the FPS - DEBUG MODE)
	 * @param avgFps
	 */
	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}

	/**
	 * Return the actual Main Game Mode (RUNNING, PAUSE or STOP)
	 * @return the mMode
	 */
	public int getMainMode() {
		return mMode;
	}

	/**
	 * Sets the Main Game Mode (RUNNING, PAUSE or STOP)
	 * @param mMode the mMode to set
	 */
	public static void setMainMode(int mMode) {
			MainGamePanel.mMode = mMode;
	}
	
	/**
	 * Method that generates the grenade`s explosion effect
	 */
	private void explosionEffect() {
		
		SoundManager.playSound(SoundManager.GRENADE_EXPLOSION_FX);
		SoundManager.playSound(SoundManager.MONSTER_HURTED_FX);
		//Clear the screen
		synchronized (listMonsters) {	
			for(int x = 0; x < listMonsters.size(); x++) {
				synchronized (screenPositionMap) {
					screenPositionMap.remove((listMonsters.get(x).getKey()));
					player.incrementScore(listMonsters.get(x));
				}
			}
			listMonsters.clear();
				
		}
		grenade.throwGrenade();
	  
	}
	
	/**
	 * Method that implements the interface SensorEventListener (Accelerometer)
	 */
	@Override
	@Deprecated
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	
	/**
	 * Method that implements the interface SensorEventListener (Accelerometer)
	 * If the shotgun has been active and selected -> if the player shake the device, reloads
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		synchronized (this) {
			
			if(grenadeTriggered) {
				
				if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
					// assign directions
					float x=event.values[0];
					//float y=event.values[1];
					//float z=event.values[2];
					
					if(x > 5.0f) {
						grenadeTriggered = false;
						touchLocked = false;
						explosionEffect();
					}else if(x < -5.0f) {
						grenadeTriggered = false;
						touchLocked = false;
						explosionEffect();
					}
					
				}
				
			} else if(currentGun == SHOTGUN && gunList.get(currentGun).isActive()) {
				
				if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
					// assign directions
					//float x=event.values[0];
					float y=event.values[1];
					//float z=event.values[2];
					
					if(y > 5.0f) {
						shotGun.setTopLimitAcc(true);
					}else if(y < -5.0f) {
						shotGun.setBottomLimitAcc(true);
					}
					if(shotGun.isTopLimitAcc() && shotGun.isBottomLimitAcc()) {
						shotGun.reload();
					}
					
				}
				
			}
			
		}// end synchronized
		
	}
	
	/**
	 * Method that checks if any achievements were got
	 * @param player
	 */
	private void checksAchievements(Player player) {
		Cursor mCursorScore = ((Activity)getContext()).getContentResolver().
                query(Score.CONTENT_URI, null, null, null, null);

    	int x = mCursorScore.getCount();
		int zombies = 0;
		int werewolf = 0;
		int mummy = 0;
		int frankeinstein = 0;
		int vampire = 0;
		int playTime = 0;
  
		mCursorScore.moveToFirst();
		
		for(int i = 0; i < x; i++) {
			zombies += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.ZOMBIE));
			werewolf += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.WEREWOLF));
			mummy += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.MUMMY));
			frankeinstein += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.FRANKEINSTEIN));
			vampire += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.VAMPIRE));
			playTime += mCursorScore.getInt(mCursorScore.getColumnIndex(Score.TIME));
			mCursorScore.moveToNext();
		}
		
      	mCursorScore.close();
      	
      	
      	//Tests the rules of game
      	if(zombies >= AchievementsRules.ZOMBIE_NUM_KILLS) {
      		player.setGotZombieKills(true);
      	}
      	
      	if(werewolf >= AchievementsRules.WEREWOLF_NUM_KILLS) {
      		player.setGotWerewolfKills(true);
      	}
      	
      	if(mummy >= AchievementsRules.MUMMY_NUM_KILLS) {
      		player.setGotMummyKills(true);
      	}
      	
      	if(frankeinstein >= AchievementsRules.FRANKEINSTEIN_NUM_KILLS) {
      		player.setGotFrankeinsteinKills(true);
      	}
      	
      	if(vampire >= AchievementsRules.VAMPIRE_NUM_KILLS) {
      		player.setGotVampireKills(true);
      	}
      	
      	if(playTime >= AchievementsRules.PLAY_TIME) {
      		player.setGotPlayTimeAchievement(true);
      	}
      	
      	player.setPlayTime(playTime);
      	
      	player.setZombieKills(zombies);
      	player.setWerewolfKills(werewolf);
      	player.setVampireKills(vampire);
      	player.setMummyKills(mummy);
      	player.setFrankeinsteinKills(frankeinstein);
      	
      	player.setZombieKills_OLD(zombies);
      	player.setWerewolfKills_OLD(werewolf);
      	player.setVampireKills_OLD(vampire);
      	player.setMummyKills_OLD(mummy);
      	player.setFrankeinsteinKills_OLD(frankeinstein);
      	
	}
	
}

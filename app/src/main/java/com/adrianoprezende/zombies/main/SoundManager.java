package com.adrianoprezende.zombies.main;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;

/**
 * SoundManager class manages all sounds and musics of the game.
 * @author Adriano Pereira Rezende
 */
public class SoundManager {
	private static SoundManager _instance;
	private static SoundPool mSoundPool;
	private static HashMap<String, Integer> mSoundPoolMap;
	private static AudioManager mAudioManager;
	private static Context mContext;
	
	// Variables used to manages the music execution on game
	private static MediaPlayer player;
	private static Uri musicURI;
	
	// Constants to Resource ID of every music in game
	public static final int MAIN_MUSIC_BGM = R.raw.mainmusic;
	public static final int MAIN_MUSIC_BGM_2 = R.raw.mainmusic2;
	public static final int INTRO_MUSIC_BGM = R.raw.intro;
	public static final int GAMEOVER_MUSIC_BGM = R.raw.gameover;
	public static final int INSTRUCTIONS_MUSIC_BGM = R.raw.going_to_battle;
	public static final int ACHIEVEMENTS_MUSIC_BGM = R.raw.temple;
	
	// Constant value to speed of MediaPlayer play
	private static final float SPEED = 1f;
	
	// Constants Strings to the sounds
	public static final String SHOOT_GUN_FX = "shootSE";
	public static final String RELOAD_GUN_FX = "reloadSE";
	public static final String EMPTY_GUN_FX = "emptyGunSE";
	public static final String SHOOT_SHOTGUN_FX = "shotgunFireSE";
	public static final String RELOAD_SHOTGUN_FX = "reloadShotgunSE";
	public static final String CLAW_FX = "clawSE";
	public static final String MONSTER_HURTED_FX = "monsterHurtedSE";
	public static final String WOMAN_SHOUT_FX = "womanShoutSE";
	public static final String MAN_SHOUT_FX = "manShoutSE";
	public static final String GAMEOVER_SHOUT_FX = "gameOverShoutSE";
	public static final String GRENADE_TRIGGER_FX = "grenadeTriggerSE";
	public static final String GRENADE_EXPLOSION_FX = "grenadeExplosionSE";
	public static final String FANFARE = "fanfare";
	public static final String SHOOT_ROCKET_FX = "shotRocketSE";
	public static final String RELOAD_ROCKET_FX = "reloadRocketSE";
	
	private SoundManager() {
		
	}
	
	/**
	 * Requests the instance of the Sound Manager and creates it
	 * if it does not exist.
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public SoundManager getInstance() {
		if(_instance == null) {
			_instance = new SoundManager();	
		}
		return _instance;
	}
	
	
	/**
	 * Initializes the storage for the sounds
	 * @param context The Application context
	 */
	public static void initSounds(Context context) {
		mContext = context;
		mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap = new HashMap<String, Integer>();
		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		player = new MediaPlayer();
	}
	
	
	/**
	 * Add a new Sound to the SoundPool
	 * @param soundName - The Sound Name for Retrieval
	 * @param soundID - The Android ID for the Sound asset.
	 */
	public static void addSound(String soundName, int soundID ) {
		mSoundPoolMap.put(soundName, mSoundPool.load(mContext, soundID, 1));
	}
	
	
	/**
	 * Plays a Sound
	 * @param soundName - The Name of the Sound to be played
	 */
	public static void playSound(String soundName) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundPoolMap.get(soundName), streamVolume, streamVolume, 1, 0, SPEED);
	}
	
	/**
	 * Plays a Looped Sound
	 * @param soundName - The Name of the Sound to be played
	 */
	public static int playLoopedSound(String soundName) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		return mSoundPool.play(mSoundPoolMap.get(soundName), streamVolume, streamVolume, 1, -1, SPEED);
	}
	
	/**
	 * Load all sounds FX of the game
	 */
	public static void loadSounds() {
		//Carrega os sons para o cilindro
		SoundManager.addSound(EMPTY_GUN_FX, R.raw.emptygun);
		SoundManager.addSound(RELOAD_GUN_FX, R.raw.reloadgun);
		SoundManager.addSound(SHOOT_GUN_FX, R.raw.shoot);
		SoundManager.addSound(SHOOT_SHOTGUN_FX, R.raw.shotgunfire);
		SoundManager.addSound(RELOAD_SHOTGUN_FX, R.raw.shotgunreload);
		SoundManager.addSound(CLAW_FX, R.raw.claw);
		SoundManager.addSound(MONSTER_HURTED_FX, R.raw.monsterhurted);
		SoundManager.addSound(WOMAN_SHOUT_FX, R.raw.womanshout);
		SoundManager.addSound(MAN_SHOUT_FX, R.raw.manshout);
		SoundManager.addSound(GAMEOVER_SHOUT_FX, R.raw.gameover_scream);
		SoundManager.addSound(GRENADE_TRIGGER_FX, R.raw.grenadetrigger);
		SoundManager.addSound(GRENADE_EXPLOSION_FX, R.raw.grenadeexplosion);
		SoundManager.addSound(FANFARE, R.raw.fanfare);
		SoundManager.addSound(SHOOT_ROCKET_FX, R.raw.rocketfire);
		SoundManager.addSound(RELOAD_ROCKET_FX, R.raw.rocketreload);
	}
	
	/**
	 * Play a Music - 
	 * This method uses the MediaPlayer to execute the musics of the game
	 * @param soundID
	 */
	public static void playMusicBG(int soundID, boolean loop) {
		try {
			if(player == null) {
				player = MediaPlayer.create(mContext, soundID);
			} else {
				//player.stop();
				player.reset();
				musicURI = Uri.parse("android.resource://com.adrianoprezende.zombies.main/" + soundID);
				player.setDataSource(mContext, musicURI);
			}

			player.prepare();
			player.setLooping(loop);
			
			float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			
			player.setVolume(streamVolume, streamVolume);
			player.start();
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Stop a Sound specified by the currentStreamID. 
	 * @param currentStreamID is the value returned by the play() function.
	 */
	public static void stopSound(int currentStreamID) {
		mSoundPool.stop(currentStreamID);
	}
	
	
	/**
	 * Stop the Music that is in execution
	 */
	public static void stopMusic() {
		if(player != null) {
			player.stop();
		}
	}
	
	/**
	 * Pause the Music that is in execution
	 */
	public static void pauseMusic() {
		if(player != null) {
			player.pause();
		}
	}
	
	/**
	 * Resume the Music that is in execution
	 */
	public static void resumeMusic() {
		if(player != null) {
			player.start();
		}
	}
	
	
	/**
	 * Deallocates the resources and Instance of SoundManager and Media Player
	 */
	public static void cleanup() {
			player.stop();
			player.release();
			player = null;
			mSoundPool.release();
			mSoundPool = null;
			mSoundPoolMap.clear();
			mAudioManager.unloadSoundEffects();
			_instance = null;
	}
	
	
}


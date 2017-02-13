package uq.deco2800.duxcom.sound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.async.MusicAsyncProcess;
import uq.deco2800.duxcom.async.SoundFxAsyncProcess;

/**
 * Created by elliot on 25/09/16.
 *
 * SoundPlayer class is a static wrapper for the MusicAsyncProcess. Is used to
 * control the sound that is playing throughout the game.
 */

public class SoundPlayer {

	/**
	 * MusicAsyncProcess used throughout the game. Is turned on and off through
	 * this class.
	 */
	private static boolean mute = false;

	/**
	 * Mute the game if Unmuted, vice-versa.
	 */
	public static void toggleMute() {
		if (mute) {
			mute = false;
		} else {
			mute = true;
			stopMusic();
		}
	}

	/**
	 * Checks if the audio is muted.
	 * 
	 * @return returns true if audio is muted, false if not
	 */
	public static boolean isMuted() {
		return mute;
	}

	/**
	 * MusicAsyncProcess used throughout the game. Is turned on and off through
	 * this class.
	 */
	private static MusicAsyncProcess musicAsyncProcess = new MusicAsyncProcess();
	private static SoundFxAsyncProcess soundFxAsyncProcess = new SoundFxAsyncProcess();
	private static Logger logger = LoggerFactory.getLogger(SoundPlayer.class);
	private static boolean soundEnabled = true;

	/**
	 * Initialises the music and soundfx player and starts it's thread.
	 */
	public static void initPlayer() {
		logger.info("Initialising Music Player");
		musicAsyncProcess.init();
		musicAsyncProcess.start();
		logger.info("Initialising SoundFx Player");
		soundFxAsyncProcess.init();
		soundFxAsyncProcess.start();
	}

	/**
	 * Toggle the music Mute
	 */
	public static void toggleMusicMute() {
		if (soundEnabled) {
			musicAsyncProcess.message("mute_music");
		}
	}

	/**
	 * Toggle the soundfx Mute
	 */
	public static void toggleSoundFxMute() {
		if (soundEnabled) {
			soundFxAsyncProcess.message("mute_soundfx");
		}
	}

	/**
	 * Plays menu music through the MusicAsyncProcess
	 */
	public static void playMenuMusic() {
		if (soundEnabled) {
			musicAsyncProcess.message("play_menu_screen");
		}
	}

	public static void playEndTurn() {
		if (soundEnabled) {
			soundFxAsyncProcess.message("play_end_turn");
			soundFxAsyncProcess.resetCount();
		}
	}

	/**
	 * Stops the menu music
	 */
	public static void stopMusic() {
		musicAsyncProcess.message("stop");
		soundFxAsyncProcess.message("stop");
	}

	/**
	 * Plays the overworld music
	 */
	public static void playOverworldMusic() {
		if (soundEnabled) {
			musicAsyncProcess.message("play_overworld");
		}
	}

	public static void playGameMusic() {
		if (soundEnabled) {
			musicAsyncProcess.message("play_game_music");
		}
	}

	/**
	 * Stops and destroys the MusicAsyncProcess and SoundFxAsyncProcess
	 */
	public static void stopSoundPlayer() {
		logger.info("Stopping Sound Player");
		musicAsyncProcess.stop();
		soundFxAsyncProcess.stop();
	}

	/**
	 * Plays a sound whenever a weapon, armour, or shield is equipped through
	 * the hero's inventory.
	 */
	public static void playEquip() {
		logger.info("Playing equip");
		musicAsyncProcess.message("equip");
	}

	/**
	 * Plays a sound when the magical electric staff is used for a attack. This
	 * sound effect used was obtained under a royalty free website. This piece
	 * uses sounds from freesound: ELECTRIC_ZAP_001.Wav by user JoelAudio( A
	 * reference to the site is provided. JoelAudio. (2011, December 4).
	 * ELECTRIC_ZAP_001.Wav. [.wav]. Retrieved October 16, 2016, from
	 * freesound.org, https://www.freesound.org/people/JoelAudio/sounds/136542/)
	 */
	public static void playElectricity() {
		logger.info("Playing magic staff lighning");
		musicAsyncProcess.message("electricity");
	}

	/**
	 * Plays a sound when the magical water staff is used for a attack.
	 */
	public static void playWater() {
		logger.info("Playing magic staff water");
		musicAsyncProcess.message("water");
	}

	/**
	 * Plays a sound when the magical fire staff is used for a attack. The sound
	 * used was obtained under a royalty free website. This piece uses sounds
	 * from freesound: Fireball cast 1 by user LiamG_SFX( LiamG_SFX. (2016,
	 * January 29). Fireball cast 1. [sound effect]. Retrieved October 16, 2016,
	 * from freesound.org,
	 * https://www.freesound.org/people/LiamG_SFX/sounds/334234/)
	 */
	public static void playFire() {
		logger.info("Playing magic staff fire");
		musicAsyncProcess.message("fire");
	}

	/**
	 * Plays a sound when a player wants to attack a enemy with a basic
	 * weapon(sword, hammer, axe, dagger)
	 */
	public static void playAttack() {
		logger.info("Playing magic staff attack");
		musicAsyncProcess.message("attack");
	}

	public static void playGameCountdown() {
		if (soundEnabled) {
			musicAsyncProcess.message("play_countdown");
		}
	}
}

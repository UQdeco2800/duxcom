package uq.deco2800.duxcom.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.sound.Sound;
import uq.deco2800.duxcom.sound.SoundType;

import javax.sound.sampled.BooleanControl;

/**
 * Created by Elliot on 10/14/2016.
 */
public class SoundFxAsyncProcess extends AbstractAsyncProcess {
	Sound s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.END_TURN);
	private static Logger logger = LoggerFactory.getLogger(SoundFxAsyncProcess.class);
	private static final String PLAYING_SOUND = "Playing Sound: ";
	// The amount of times a sound effect has been played.
	int playCount = 0;

	@Override
	/**
	 * Method for executing action based on message sent to process.
	 */
	protected void execute(String message) {
		if ("play_end_turn".equals(message)) {
			s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.END_TURN);
			if (playCount == 0) {
				s.playSoundFx();
			}
			playCount += 1;
		} else if ("stop".equals(message)) {
			s.stopSound();
		} else if ("equip".equals(message)) {
			s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.EQUIP);
			s.playSoundFx();
			logger.info(PLAYING_SOUND + SoundType.EQUIP);
		} else if ("electricity".equals(message)) {
			s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.ELECTRICITY);
			s.playSoundFx();
			logger.info(PLAYING_SOUND + SoundType.ELECTRICITY);
		} else if ("mute_soundfx".equals(message)) {
			BooleanControl muteControl = (BooleanControl) s.getClip().getControl(BooleanControl.Type.MUTE);
			logger.info("SoundFx Clip: " + s.getClip().toString());
			if (muteControl.getValue()) {
				muteControl.setValue(false);
			} else {
				muteControl.setValue(true);
			}
		} else if ("attack".equals(message)) {
			s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.ATTACK);
			s.playSoundFx();
			logger.info(PLAYING_SOUND + SoundType.ATTACK);
		} else if ("fire".equals(message)) {
			s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.FIRE);
			s.playSoundFx();
			logger.info(PLAYING_SOUND + SoundType.FIRE);
		} else if ("water".equals(message)) {
			s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.WATER);
			s.playSoundFx();
			logger.info(PLAYING_SOUND + SoundType.WATER);
		} else {
			logger.warn("Unrecognised SoundFxAsyncProcess");
		}
	}

	/**
	 * Resets the play count for soundfx.
	 */
	public void resetCount() {
		this.playCount = 0;
	}

}

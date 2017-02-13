package uq.deco2800.duxcom.sound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.dataregisters.AbstractDataRegister;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Created by elliot on 21/09/16.
 *
 * SoundRegister is used to match a soundfile with a SoundType.
 */
public class SoundRegister extends AbstractDataRegister {


	private static Logger logger = LoggerFactory.getLogger(SoundRegister.class);

	public SoundRegister() {
		super();

		try {
			Clip c = AudioSystem.getClip();
			c.close();
		} catch (Exception e) {
			logger.error("Unable to get clip, mocking sound", e);
			// mock each sound type
			Sound.setMocked();
		}

		linkDataToType(SoundType.MENU_MUSIC, Sound.getSound("/sound/menu.wav"));
		linkDataToType(SoundType.END_TURN, Sound.getSound("/sound/endturn.wav"));
		linkDataToType(SoundType.EQUIP, Sound.getSound("/sound/equip.wav"));
		linkDataToType(SoundType.OVERWORLD, Sound.getSound("/sound/overworld.wav"));
		linkDataToType(SoundType.GAME_MUSIC, Sound.getSound("/sound/gamemusic.wav"));
		linkDataToType(SoundType.ELECTRICITY, Sound.getSound("/sound/electricity.wav"));
		linkDataToType(SoundType.MULTIPLAYER_COUNTDOWN, Sound.getSound("/sound/gamecountdown.wav"));
		linkDataToType(SoundType.ATTACK, Sound.getSound("/sound/attack.wav"));
		linkDataToType(SoundType.FIRE, Sound.getSound("/sound/fire.wav"));
		linkDataToType(SoundType.WATER, Sound.getSound("/sound/water.wav"));

	}

}

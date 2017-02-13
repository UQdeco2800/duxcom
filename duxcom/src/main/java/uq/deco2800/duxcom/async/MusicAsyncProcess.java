package uq.deco2800.duxcom.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.BooleanControl;

import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.sound.Sound;
import uq.deco2800.duxcom.sound.SoundType;


/**
 * Created by elliot on 21/09/16.
 */
public class MusicAsyncProcess extends AbstractAsyncProcess {
    private static Logger logger = LoggerFactory.getLogger(MusicAsyncProcess.class);
    private Sound s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.MENU_MUSIC);

    @Override
    /**
     * Method for executing action based on message.
     */
    protected void execute(String message) {
        if("play_menu_screen".equals(message)){
            s.stopSound();
            s.playSound(true);
        } else if("stop".equals(message)){
            s.stopSound();
            logger.info("Stopping Sound");
        } else if ("play_overworld".equals(message)) {
            s.stopSound();
            s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.OVERWORLD);
            s.playSound(false);
        } else if("play_game_music".equals(message)) {
            s.stopSound();
            s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.GAME_MUSIC);
            s.playSound(false);
        } else if("play_open".equals(message)) {
            s.stopSound();
            s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.OPEN);
            s.playSound(false);
        } else if("play_closed".equals(message)) {
            s.stopSound();
            s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.CLOSED);
            s.playSound(true);
        } else if ("mute_music".equals(message)) {
            BooleanControl muteControl = (BooleanControl) s.getClip().getControl(BooleanControl.Type.MUTE);
            if (muteControl.getValue()) {
                logger.info("Music Clip: "+s.getClip().toString());
                muteControl.setValue(false);
            } else {
                muteControl.setValue(true);
            }
        } else if("play_countdown".equals(message)){
            s.stopSound();
            s = (Sound) DataRegisterManager.getSoundRegister().getData(SoundType.MULTIPLAYER_COUNTDOWN);
            s.playSound(false);
        }
        else {
            logger.warn("Unrecognised MusicAsyncProcess message");
        }
    }
}

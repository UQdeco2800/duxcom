package uq.deco2800.duxcom.sound;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.dataregisters.AbstractDataClass;

import javax.sound.sampled.*;

/**
 * Created by elliot on 21/09/16.
 *
 * Sound class abstracts music or sound effect. Can be played through the SoundPlayer.
 */
public abstract class Sound implements AbstractDataClass {
    private static boolean isMocked = false;
    public static void setMocked(){
        isMocked = true;
    }

    public static Sound getSound(String path){
        if(isMocked){
            return new MockSound(path);
        }
        return new RealSound(path);
    }

    protected static Logger logger = LoggerFactory.getLogger(Sound.class);
    public abstract void playSound(boolean withLoop);
    public abstract void stopSound();
    public abstract void playSoundFx();
    public abstract Clip getClip();
}

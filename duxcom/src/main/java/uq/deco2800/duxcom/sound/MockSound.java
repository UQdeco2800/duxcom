package uq.deco2800.duxcom.sound;

import javax.sound.sampled.Clip;

/**
 * Used to mock sounds
 *
 * Created by liamdm on 27/09/2016.
 */
public class MockSound extends Sound {
    public MockSound(String path) {

    }

    @Override
    public void playSound(boolean withLoop) {
        // do nothing
    }

    @Override
    public void stopSound() {
        // do nothing
    }

    @Override
    public void playSoundFx() {
        // do nothing
    }

    @Override
    public Clip getClip() {
        return null;
    }
}

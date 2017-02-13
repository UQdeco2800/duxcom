package uq.deco2800.duxcom.sound;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Used to play real sounds
 *
 * Created by liamdm on 27/09/2016.
 */
public class RealSound extends Sound {
    private Clip clip;
    private AudioInputStream inputStream;

    /**
     * Create a new sound object with a clip and a SoundType. The type of sound is dependent of the
     * sound type.
     * @param pathToFile - Path to the sound file.
     */
    public RealSound(String pathToFile) {
        try{
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(ClassLoader.class.
                    getResourceAsStream(pathToFile));
        } catch(LineUnavailableException e) {
            logger.error("LineUnavailableException while constructing Sound object - " +
                    "no playback device", e);
        } catch(IOException e) {
            logger.error("IOException thrown while constructing Sound object", e);
        } catch (UnsupportedAudioFileException e) {
            logger.error("UnsupportedAudioFileException thrown while constructing " +
                    "Sound object - make sure file is .wav", e);
            logger.info(e.getCause().toString());
        } catch (NullPointerException e) {
            logger.error("NullPointerException thrown - cannot find file", e);
        }

    }

    /**
     * Play the sound from the sound clip through the audio stream.
     */
    public void playSound(boolean withLoop) {
        try {
            if (!clip.isOpen()) {
                clip.open(inputStream);
            }
            clip.setMicrosecondPosition(0);
            if (withLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        } catch(IOException e) {
            logPlayingIOException(e);
        } catch (LineUnavailableException e) {
            logPlayingLineUnavailableException(e);
        } catch (IllegalStateException e) {
            logPlayingIllegalStateException(e);
        }
    }

    public void playSoundFx() {
        try {
            if (!clip.isOpen()) {
                clip.open(inputStream);
            }
            clip.setMicrosecondPosition(0);
            clip.start();
            while(clip.getMicrosecondLength() !=  clip.getMicrosecondPosition()) { }
            clip.stop();
        } catch(IOException e) {
            logPlayingIOException(e);
        } catch (LineUnavailableException e) {
            logPlayingLineUnavailableException(e);
        } catch (IllegalStateException e) {
            logPlayingIllegalStateException(e);
        }
    }

    private void logPlayingIllegalStateException(IllegalStateException e) {
        logger.error("IllegalStateException - Clip is already open", e);
    }

    private void logPlayingLineUnavailableException(LineUnavailableException e) {
        logger.error("LineUnavailableException while trying to play sound - " +
                "no playback device", e);
    }

    private void logPlayingIOException(IOException e) {
        logger.error("IOException while trying to play sound", e);
    }


    /**
     * Stops the playback of sound.
     */
    public void stopSound() {
        clip.stop();
        clip.flush();
        clip.close();
    }

    public Clip getClip() {
        return this.clip;
    }

}

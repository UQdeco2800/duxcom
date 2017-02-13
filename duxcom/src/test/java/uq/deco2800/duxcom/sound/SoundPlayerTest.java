package uq.deco2800.duxcom.sound;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by tiget on 10/20/16.
 */
public class SoundPlayerTest {
    @Test
    public void testToggleMute() {
        SoundPlayer player = new SoundPlayer();

        // ensure mute is false
        assertFalse(player.isMuted());
        player.toggleMute();
        assertTrue(player.isMuted());
    }
}

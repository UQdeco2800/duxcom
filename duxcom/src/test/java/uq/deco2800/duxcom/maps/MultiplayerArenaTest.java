package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests Multiplayer Arena
 *
 * Created by samuelthomas on 23/10/16.
 */
public class MultiplayerArenaTest {

    private MultiplayerArena map;

    @Test
    public void testCreation() {
        map = new MultiplayerArena();
        assertNotNull(map);
    }
}

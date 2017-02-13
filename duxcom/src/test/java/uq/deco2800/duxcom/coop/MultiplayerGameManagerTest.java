package uq.deco2800.duxcom.coop;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by liamdm on 21/10/2016.
 */
public class MultiplayerGameManagerTest {
    @Test
    public void showFlashbang() throws Exception {
        MultiplayerGameManager.showFlashbang("test");
        assertEquals("test", MultiplayerGameManager.getFlashbang());
    }

    @Test
    public void enableTemporaryBlock() throws Exception {
        MultiplayerGameManager gameManager = new MultiplayerGameManager(null, false);
        // not MP shouldn't block
        gameManager.enableTemporaryBlock();
        assertFalse(gameManager.isBlocking());
        gameManager.disableTemporaryBlock();
        assertFalse(gameManager.isBlocking());
    }

    @Test
    public void sneakyTransferPlayerOrder() throws Exception {
        // test this successed
        MultiplayerGameManager.sneakyTransferPlayerOrder(null);
    }

}
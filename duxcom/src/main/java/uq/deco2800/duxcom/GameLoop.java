package uq.deco2800.duxcom;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Responsible for updating the DemoMap and all entities in the game.
 * <p>
 * Created by woody on 30-Jul-16.
 */
public class GameLoop implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(GameLoop.class);

    private int tick;
    private AtomicBoolean quit;

    private static GameManager currentGameManager;

    /**
     * Returns the current game manager that started the game loop
     */
    public static GameManager getCurrentGameManager() {
        return currentGameManager;
    }
    
    /**
     * Destroys the current game manager
     */
    public static void destroyCurrentGameManager() {
        currentGameManager = null;
    }

    /**
     * GameLoop constructor
     *
     * @param tick        tick count
     * @param quit        quit status
     * @param gameManager the game manager
     */
    public GameLoop(int tick, AtomicBoolean quit, GameManager gameManager) {
        GameLoop.currentGameManager = gameManager;
        this.tick = tick;
        this.quit = quit;
    }

    @Override
    public void run() {
        while (!quit.get()) {

            try {
                Thread.sleep(tick);
            } catch (InterruptedException e) {
                logger.error("Game loop fatally interrupted");
                Platform.exit();
                Thread.currentThread().interrupt();
            }
        }

    }
}

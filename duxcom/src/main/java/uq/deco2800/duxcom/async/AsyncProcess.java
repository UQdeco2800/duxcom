package uq.deco2800.duxcom.async;

/**
 * Handles asynchronous process in the game.
 *
 * For documentation see <a href="https://github.com/UQdeco2800/deco2800-2016-duxcom/wiki/Asynchronous-Handling">here</a>
 *
 * Created by liamdm on 20/08/2016.
 */
public interface AsyncProcess {
    /**
     * The state of the async process.
     */
    enum ProcessState {
        STOPPED, INITIALISED, RUNNING, PAUSED
    }

    ProcessState getState();

    /**
     * Create a thread ready to handle the messages
     */
    void init();

    /**
     * Destroy the thread handling the messages
     */
    void stop();

    /**
     * Blocks the current thread till the async process has stopped.
     * Usefull for final teardown.
     */
    void blockTillStopped();

    /**
     * Pause the thread but do not block the message queue
     */
    void pause();

    /**
     * Start the thread processing the message queue
     */
    void start();

    /**
     * Resume the thread processing the message queue from pause state
     */
    void resume();

    /**
     * Send a message to the thread
     * @param message the message to send
     * @return true iff message sent
     */
    boolean message(String message);

    /**
     * Remove a message from the outgoing queue and return it
     * @return recieved string
     */
    String receive();

}

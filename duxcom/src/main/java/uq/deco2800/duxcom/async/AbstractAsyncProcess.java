package uq.deco2800.duxcom.async;

import uq.deco2800.duxcom.annotation.MethodContract;
import uq.deco2800.duxcom.exceptions.AsyncDuplicateProcessException;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Handles a queue for a async process to pull from.
 *
 * For documentation see <a href="https://github.com/UQdeco2800/deco2800-2016-duxcom/wiki/Asynchronous-Handling">here</a>
 *
 * Created by liamdm on 20/08/2016.
 */
public abstract class AbstractAsyncProcess implements AsyncProcess {

    /**
     * Gets the state of the current async process
     * @return the relevant ProcessState
     */
    @Override
    public ProcessState getState() {
        if(!initialised.get()){
            return ProcessState.STOPPED;
        }

        if(waiting.get()){
            return ProcessState.INITIALISED;
        }

        if(paused.get()){
            return ProcessState.PAUSED;
        }

        return ProcessState.RUNNING;
    }

    /**
     * User code to handle messages goes here
     * @param message the message to handle
     */
    protected abstract void execute(String message);

    /**
     * The southbound message queue
     */
    private final ConcurrentLinkedQueue<String> messageQue = new ConcurrentLinkedQueue<>();

    /**
     * The northbound message queue
     */
    private final ConcurrentLinkedQueue<String> outgoingQue = new ConcurrentLinkedQueue<>();

    /**
     * If the thread has been started
     */
    private final AtomicBoolean initialised = new AtomicBoolean(false);

    /**
     * If the thread is waiting for start
     */
    private final AtomicBoolean waiting = new AtomicBoolean(true);

    /**
     * If the thread is running.
     */
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * If the thread is paused
     */
    private final AtomicBoolean paused = new AtomicBoolean(false);

    /**
     * Mutual exclusion, only one thread per instance
     */
    private Semaphore semaphore = new Semaphore(1);

    /**
     * Safe sleep
     */
    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Called once to intialise, and once after each call to stop();
     * Used to start the thread
     *
     * @throws AsyncDuplicateProcessException thrown if init() called twice
     */
    @MethodContract(precondition = {"semaphore.availablePermits() > 1"})
    @Override
    public void init() {
        if(initialised.get() || !semaphore.tryAcquire()){
            throw new AsyncDuplicateProcessException();
        }
        // Assume starting will occur (may be unreasonable)
        initialised.set(true);

        Thread t = new Thread(() -> {
            // Waiting for start, queue will not grow
            while(waiting.get()){
                sleep(100);
            }

            while(running.get()) {
                while(paused.get()){
                    sleep(100);
                }
                if (!messageQue.isEmpty()) {
                    execute(messageQue.remove());
                }
                sleep(300);
            }

            // Stopped (only 1 thread can ever be here at a time)
            waiting.set(true);
            initialised.set(false);

            semaphore.release();
        });
        t.setDaemon(true);
        t.start();
    }

    /**
     * Destroys the current thread and clears the queue. Prevents messages
     * being added to the queue
     */
    @MethodContract(precondition = {"this.getState() == ProcessState.RUNNING"})
    @Override
    public void stop() {
        messageQue.clear();
        running.set(false);
    }

    /**
     * Blocks till the thread is stopped
     */
    @MethodContract(precondition = {"this.getState() == ProcessState.RUNNING"})
    public void blockTillStopped(){
        messageQue.clear();
        running.set(false);

        // Wait till thread releases
        semaphore.acquireUninterruptibly();

        // Thread is stopped, release hold
        semaphore.release();
    }

    /**
     * Pause parsing the current queue, does not prevent messages
     * being added
     */
    @MethodContract(precondition = {"this.getState() == ProcessState.RUNNING"})
    @Override
    public void pause() {
        paused.set(true);
    }

    /**
     * Resumes parsing in the thread from a paused state
     */
    @MethodContract(precondition = {"this.getState() == ProcessState.PAUSED"})
    @Override
    public void resume() {
        paused.set(false);
    }

    /**
     * Starts parsing the current queue
     */
    @MethodContract(precondition = {"this.getState() == ProcessState.INITIALISED"})
    @Override
    public void start() {
    	running.set(true);
        waiting.set(false);
    }

    /**
     * Adds a message to the current queue. Returns true if the message
     * could be added.
     *
     * @param message the message to send to the thread
     */
    @MethodContract(precondition = {"message != null"})
    @Override
    public boolean message(String message) {
        if(running.get() && !waiting.get()) {
            messageQue.add(message);
            return true;
        }

        return false;
    }

    /**
     * Removes the earliest message from the outgoing thread
     */
    @Override
    public String receive() {
        return outgoingQue.poll();
    }

    /**
     * Sends a message on the northbound interface to be recieved
     *
     * @param message the message to send
     */
    @MethodContract(precondition = {"message != null", "this.getState() == ProcessState.RUNNING"})
    protected void sendOutgoing(String message) {
        outgoingQue.add(message);
    }
}

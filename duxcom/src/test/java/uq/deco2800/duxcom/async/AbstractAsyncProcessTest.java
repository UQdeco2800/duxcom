package uq.deco2800.duxcom.async;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the abstract async process.
 *
 * Created by liamdm on 21/08/2016.
 */
public class AbstractAsyncProcessTest {
    /**
     * Test the starting and stopping of threads
     */
    protected class TestAsyncProcess extends AbstractAsyncProcess {

        @Override
        protected void execute(String message) {
            if(message.equals("test")){
                sendOutgoing("test_start");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                sendOutgoing("test_end");
            }
        }
    }

    @Test
    /**
     * Test it is actually asyncronous
     */
    public void asyncProcessTestAsynchronisity() throws InterruptedException {
        TestAsyncProcess testAsyncProcess = new TestAsyncProcess();

        // Not running shouldn't accept
        assertEquals(AsyncProcess.ProcessState.STOPPED, testAsyncProcess.getState());
        assertFalse(testAsyncProcess.message("test"));

        // Initialise
        testAsyncProcess.init();

        // Not running shouldn't accept
        assertEquals(AsyncProcess.ProcessState.INITIALISED, testAsyncProcess.getState());
        assertFalse(testAsyncProcess.message("test"));

        // Start
        testAsyncProcess.start();

        // Should accept now
        assertEquals(AsyncProcess.ProcessState.RUNNING, testAsyncProcess.getState());
        assertTrue(testAsyncProcess.message("test"));

        // Should not have blocked
        gotResponse:
        {
            for (int i = 0; i < 1000; ++i) {
                String recieved = testAsyncProcess.receive();
                if(recieved != null && recieved.equals("test_start")) {
                    break gotResponse;
                }
                // Wait up to 1 second for inital response
                Thread.sleep(1);
            }

            // failed to get response
            assertFalse("Failed to get a response from thread outgoing queue!", true);
        }

        // Try and get second response
        gotResponse:
        {
            for (int i = 0; i < 1500; ++i) {
                String recieved = testAsyncProcess.receive();
                if(recieved != null && recieved.equals("test_end")) {
                    break gotResponse;
                }

                // Wait up to 1.5 second for second response
                Thread.sleep(1);
            }

            // failed to get response
            assertFalse("Failed to get a response from thread outgoing queue!", true);
        }
    }

    @Test
    /**
     * Test pausing and resuming
     */
    public void testPauseResume() throws InterruptedException {
        TestAsyncProcess testAsyncProcess = new TestAsyncProcess();
        testAsyncProcess.init();

        // Start and pause preventing any messages from being polled
        testAsyncProcess.start();
        testAsyncProcess.pause();

        assertTrue(testAsyncProcess.message("test"));

        // Should not get a message till resume
        // Try and get second response
        for (int i = 0; i < 1500; ++i) {
            String recieved = testAsyncProcess.receive();
            if(recieved != null) {
                // failed
                assertFalse("AsyncProcess failed to pause!", true);
            }

            // Wait up to 1.5 second for NO response
            Thread.sleep(1);
        }

        // Resume
        testAsyncProcess.resume();

        // Try and get response
        gotResponse:
        {
            for (int i = 0; i < 1500; ++i) {
                String recieved = testAsyncProcess.receive();
                if(recieved != null && !recieved.equals("test_start")) {
                    break gotResponse;
                }

                // Wait up to 1.5 second for second response
                Thread.sleep(1);
            }

            // failed to get response
            assertFalse("Failed to get a response from thread outgoing queue, failed to resume!", true);
        }

    }

    @Test
    /**
     * Test re-creating from stopped state
     */
    public void testRecreate(){
        // Test re-creating after stopping
        TestAsyncProcess testAsyncProcess = new TestAsyncProcess();
        testAsyncProcess.init();
        testAsyncProcess.start();

        assertEquals(AsyncProcess.ProcessState.RUNNING, testAsyncProcess.getState());

        // Try stopping
        testAsyncProcess.blockTillStopped();
        assertEquals(AsyncProcess.ProcessState.STOPPED, testAsyncProcess.getState());

        // Try re-initialising
        testAsyncProcess.init();
        assertEquals(AsyncProcess.ProcessState.INITIALISED, testAsyncProcess.getState());

        // Try re-starting
        testAsyncProcess.start();
        assertEquals(AsyncProcess.ProcessState.RUNNING, testAsyncProcess.getState());
    }
}
package uq.deco2800.duxcom.coop.exceptions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the excetpion
 *
 * Created by liamdm on 21/10/2016.
 */
public class IncorrectGameStateTest {

    @Test
    public void testException(){
        try {
            throw new IncorrectGameState("testmessage");
        } catch (IncorrectGameState incorrectGameState) {
            assertEquals("testmessage", incorrectGameState.getMessage());
        }
    }
}
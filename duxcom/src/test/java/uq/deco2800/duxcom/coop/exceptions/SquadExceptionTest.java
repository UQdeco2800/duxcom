package uq.deco2800.duxcom.coop.exceptions;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Tests the squad exception
 *
 * Created by liamdm on 21/10/2016.
 */
public class SquadExceptionTest {
    @Test
    public void testException(){
        try {
            throw new SquadException("testmessage");
        } catch (SquadException squadException) {
            assertEquals("testmessage", squadException.getMessage());
        }
    }


}
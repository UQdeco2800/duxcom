package uq.deco2800.duxcom.coop.exceptions;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Tests the multiplayer exception
 *
 * Created by liamdm on 21/10/2016.
 */
public class MultiplayerExceptionTest {

    @Test
    public void testException(){
        try {
            throw new MultiplayerException(MultiplayerException.ErrorCode.CLIENT_CREATION_FAILURE);
        } catch (MultiplayerException e) {
            assertEquals(MultiplayerException.ErrorCode.CLIENT_CREATION_FAILURE, e.getErrorCode());
        }
    }


    // tig wrote this
    @Test
    public void enumContainsAllValuesTest() {
        /* tdd up in this kinda sorta */
        Set<String> expected = new HashSet<>(Arrays.asList("NO_CREDENTIALS", "REST_CLIENT_FAILED", "CREDENTIAL_INVALID",
                "CLIENT_CREATION_FAILURE", "NO_SESSION"));
        Set<String> result = new HashSet<>();

        for (MultiplayerException.ErrorCode code : MultiplayerException.ErrorCode.values()) {
            result.add(code.name());
        }

        assertEquals(expected, result);
    }
}
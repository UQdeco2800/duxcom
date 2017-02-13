package uq.deco2800.duxcom.buffs;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tiget on 10/20/16.
 */
public class BuffTypeTest {

    @Test
    public void enumContainsAllValuesTest () {
        /* get some TDD in ya */
        Set<String> expected = new HashSet<> (Arrays.asList("ACTIONPOINTS", "HEALTH", "STAT"));
        Set<String> result = new HashSet<>();

        for (BuffType type : BuffType.values()) {
            result.add(type.name());
        }

        assertEquals(expected, result);
    }

}

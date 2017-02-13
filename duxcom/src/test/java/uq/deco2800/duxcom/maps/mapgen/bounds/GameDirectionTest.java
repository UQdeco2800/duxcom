package uq.deco2800.duxcom.maps.mapgen.bounds;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by liamdm on 25/08/2016.
 */
public class GameDirectionTest {
    @Test
    public void translateToUniversalFormat() throws Exception {
        assertEquals(Direction.UP, GameDirection.translateToUniversalFormat(Direction.TEN_OCLOCK));
        assertEquals(Direction.RIGHT, GameDirection.translateToUniversalFormat(Direction.TWO_OCLOCK));
        assertEquals(Direction.DOWN, GameDirection.translateToUniversalFormat(Direction.FOUR_OCLOCK));
        assertEquals(Direction.LEFT, GameDirection.translateToUniversalFormat(Direction.SEVEN_OCLOCK));
    }

}
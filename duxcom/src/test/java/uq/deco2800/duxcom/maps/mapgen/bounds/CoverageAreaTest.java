package uq.deco2800.duxcom.maps.mapgen.bounds;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for coverage area
 *
 * Created by liamdm on 25/08/2016.
 */
public class CoverageAreaTest {

    @Test
    public void coverageAreaTests(){
        AreaBound areaBound1 = new AreaBound(0, 0 , 5, 5);
        AreaBound areaBound2 = new AreaBound(5, 0, 10, 5);

        CoverageArea coverageArea = new CoverageArea();

        assertFalse(coverageArea.contains(0,0));

        coverageArea.addBound(areaBound1);

        assertTrue(coverageArea.contains(0, 0));
        assertTrue(coverageArea.contains(4, 4));

        assertFalse(coverageArea.contains(5, 5));
        assertFalse(coverageArea.contains(5, 0));

        coverageArea.addBound(areaBound2);

        assertFalse(coverageArea.contains(5, 5));

        assertTrue(coverageArea.contains(5, 0));


        AreaBound bound = new AreaBound(15, 0, 20, 5);

        assertEquals(5, coverageArea.minimumDistanceToCoveredArea(bound));
    }
}
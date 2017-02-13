package uq.deco2800.duxcom.maps.mapgen.bounds;

import org.junit.Ignore;
import org.junit.Test;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import static org.junit.Assert.*;

/**
 * Tests to ensure block group is functional
 *
 * Created by liamdm on 25/08/2016.
 */
@Ignore
public class BlockGroupTest {

    @Test
    public void getGroupBound() throws Exception {
        BlockGroup testGroup = new BlockGroup();
        testGroup.addBlock(new Coordinate(0, 0));

        assertEquals(MapAssembly.getBlockSize(), testGroup.getGroupBound().getWidth());
        assertEquals(MapAssembly.getBlockSize(), testGroup.getGroupBound().getHeight());

    }

    @Test
    public void addBlock() throws Exception {

        BlockGroup testGroup = new BlockGroup();
        testGroup.addBlock(new Coordinate(0, 0));

        assertEquals(MapAssembly.getBlockSize(), testGroup.getGroupBound().getWidth());
        assertEquals(MapAssembly.getBlockSize(), testGroup.getGroupBound().getHeight());

        testGroup.addBlock(new Coordinate(0, 1));

        assertEquals(MapAssembly.getBlockSize(), testGroup.getGroupBound().getWidth());
        assertEquals(MapAssembly.getBlockSize() * 2, testGroup.getGroupBound().getHeight());

        testGroup.addBlock(new Coordinate(1, 1));

        assertEquals(MapAssembly.getBlockSize() * 2, testGroup.getGroupBound().getWidth());
        assertEquals(MapAssembly.getBlockSize() * 2, testGroup.getGroupBound().getHeight());

    }

    @Test
    public void getBlockLocations() throws Exception {

        BlockGroup testGroup = new BlockGroup();
        testGroup.addBlock(new Coordinate(0, 0));

        assertEquals(1, testGroup.getBlockLocations().size());
        assertEquals(MapAssembly.getBlockSize(), testGroup.getBlockLocations().get(0).getWidth());
    }

    @Test
    public void contains() throws Exception {

        BlockGroup testGroup = new BlockGroup();
        testGroup.addBlock(new Coordinate(0, 0));

        assertTrue(testGroup.contains(1, 1));
        assertFalse(testGroup.contains(MapAssembly.getBlockSize(), 0));
        assertFalse(testGroup.contains(0, MapAssembly.getBlockSize()));

        testGroup.addBlock(new Coordinate(0, 1));
        testGroup.addBlock(new Coordinate(1, 0));

        assertTrue(testGroup.contains(MapAssembly.getBlockSize(), 0));
        assertTrue(testGroup.contains(0, MapAssembly.getBlockSize()));

    }

}
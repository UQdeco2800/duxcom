package uq.deco2800.duxcom.maps.mapgen.bounds;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by liamdm on 25/08/2016.
 */
@Ignore
public class BlockPointMapperTest {
    @Before
    public void setUp() throws Exception {
        // Ensure the map is generated
        BlockPointMapper.init();
    }

    @Test
    public void blockPointToWorldPoint() throws Exception {
        assertEquals(0, BlockPointMapper.blockPointToWorldPoint(0));
        assertEquals(MapAssembly.getBlockSize(), BlockPointMapper.blockPointToWorldPoint(1));
        assertEquals(2 * MapAssembly.getBlockSize(), BlockPointMapper.blockPointToWorldPoint(2));
    }

    // This was done by ImComingHarambe, we have talked to the creators of this class
    // and they have ok'd us ignoring this test for the time being
	@Ignore
    @Test
    public void getInRangeBlocks() throws Exception {
        List<Coordinate> inRange = BlockPointMapper.getInRangeBlocks(0, 0, 20);
        assertTrue(inRange.contains(new Coordinate(-1, 0)));
        assertTrue(inRange.contains(new Coordinate(0, -1)));
        assertTrue(inRange.contains(new Coordinate(0, 1)));
        assertTrue(inRange.contains(new Coordinate(1, 0)));
    }

    @Test
    public void coordinateWithinBlock() throws Exception {
        assertEquals(0, BlockPointMapper.coordinateWithinBlock(0));
        assertEquals(0, BlockPointMapper.coordinateWithinBlock(MapAssembly.getBlockSize()));
        if(MapAssembly.getBlockSize() > 2) {
            assertEquals(2, BlockPointMapper.coordinateWithinBlock(MapAssembly.getBlockSize() + 2));
        }
        }

    @Test
    public void getBlockNumber() throws Exception {
        assertEquals(0, BlockPointMapper.getBlockNumber(new Coordinate(0, 0)));
        assertEquals(1, BlockPointMapper.getBlockNumber(new Coordinate(0, 1)));
        assertEquals(2, BlockPointMapper.getBlockNumber(new Coordinate(1, 1)));
    }

    @Test
    public void tryConvertNumberToCoordinate() throws Exception {
        assertEquals(new Coordinate(0, 0), BlockPointMapper.tryConvertNumberToCoordinate(0));
        assertEquals(new Coordinate(0, 1), BlockPointMapper.tryConvertNumberToCoordinate(1));
        assertEquals(new Coordinate(1, 1), BlockPointMapper.tryConvertNumberToCoordinate(2));
    }

    @Test
    public void getNextBlock() throws Exception {
        assertEquals(1, BlockPointMapper.getNextBlock(0, Direction.UP));
        assertEquals(2, BlockPointMapper.getNextBlock(1, Direction.RIGHT));
    }

    @Test
    public void getBlockPoint() throws Exception {
        assertEquals(new Coordinate(0, 0), BlockPointMapper.getBlockPoint(0, 0));
        assertEquals(new Coordinate(0, 0), BlockPointMapper.getBlockPoint(1, 1));
        assertEquals(new Coordinate(1, 1), BlockPointMapper.getBlockPoint(MapAssembly.getBlockSize() + 1, MapAssembly.getBlockSize() + 1));
    }

}
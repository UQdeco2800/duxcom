package uq.deco2800.duxcom.entities.scenery;

import org.junit.Test;
import org.mockito.Mock;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.maps.DynamicEntityTestMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import static org.junit.Assert.assertTrue;

/**
 * Created by jay-grant on 9/10/2016.
 */
public class AbstractSceneryTest {

    @Mock
    Entity entityMock;

    @Test
    /* This seems like a trivial test, but I am paranoid about someone breaking toString()
     * for these. Same for typeTest and imageName.
     */
    public void sceneryStringTest() {
        AbstractScenery tower = new AbstractScenery(SceneryType.WALL_STONE_TOWER, 0, 0);
        assertTrue(tower.getSceneryString() == "wall_stone_tower");
    }

    @Test
    public void sceneryTypeTest() {
        AbstractScenery tower = new AbstractScenery(SceneryType.WALL_STONE_TOWER, 0, 0);
        assertTrue(tower.getSceneryType() == SceneryType.WALL_STONE_TOWER);

    }

    @Test
    public void getImageName() {
        AbstractScenery tower = new AbstractScenery(SceneryType.WALL_STONE_TOWER, 0, 0);
        assertTrue(tower.getSceneryString() == "wall_stone_tower");
    }
}

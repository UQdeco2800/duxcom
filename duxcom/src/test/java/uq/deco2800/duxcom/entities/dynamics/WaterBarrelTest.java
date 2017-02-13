package uq.deco2800.duxcom.entities.dynamics;

import org.junit.Test;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.entities.DynamicsManager;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.maps.DynamicEntityTestMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by tiget on 10/18/16.
 */
public class WaterBarrelTest {
    @Test
    public void syncStringTest() {
        WaterBarrel barrel = new WaterBarrel(1, 2);
        assertTrue(barrel.getSyncString() == "WATER_BARREL");
    }

    @Test
    public void tickIntervalTest() {
        WaterBarrel barrel = new WaterBarrel(1, 2);
        assertTrue(barrel.getTickInterval() == 500L);
    }

    @Test
    public void animationTickTest() {
        WaterBarrel barrel = new WaterBarrel(1, 2);
        List<Phase> phases = barrel.getPhases();
        assertTrue(phases.indexOf(barrel.getCurrentPhase()) == 0);

        barrel.animationTick();
        assertTrue(phases.indexOf(barrel.getCurrentPhase()) == 1);
    }

    @Test
    public void turnTickTest() {
        WaterBarrel barrel = new WaterBarrel(1, 2);
        List<Phase> phases = barrel.getPhases();
        assertTrue(phases.indexOf(barrel.getCurrentPhase()) == 0);

        barrel.turnTick();
        assertTrue(phases.indexOf(barrel.getCurrentPhase()) == 1);
    }

    @Test
    public void onChangeHealthDestroyTest() {
        MapAssembly map = new MapAssembly(new DynamicEntityTestMap("test"));
        DynamicsManager manager = map.getDynamicsManager();

        WaterBarrel waterBarrel = new WaterBarrel(0, 0);
        map.addEntity(waterBarrel);

        waterBarrel.changeHealth(1.0, DamageType.NORMAL);

        List<Entity> entities = map.getEntities(0, 0);
        assertTrue(entities.get(0) instanceof Water);
    }
}

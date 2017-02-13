package uq.deco2800.duxcom.abilities;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * 
 * @author spress11
 *
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CallToArmsTest extends ApplicationTest{
	@Mock
	private GameManager gameManagerMock;
	@Mock
	private MapAssembly mapMock;
	
	private class ConcreteHero extends AbstractHero{
		public ConcreteHero(int x, int y) {
			super(EntityType.KNIGHT, x, y);
			// TODO Auto-generated constructor stub
		}
	}

	@Test
	public void useOnPoint() throws Exception {
		AbstractHero hero = new ConcreteHero(1, 1);
		AbstractHero hero1 = new ConcreteHero(1,2);
		AbstractHero hero2 = new ConcreteHero(1,3);
		
		List<Tile> tiles = new ArrayList<>();
		Tile tile0 = new Tile(TileType.DT_GRASS_DARK_1);
		Tile tile1 = new Tile(TileType.DT_GRASS_DARK_1);
		Tile tile2 = new Tile(TileType.DT_GRASS_DARK_1);
		Tile tile3 = new Tile(TileType.DT_GRASS_DARK_1);
		tile0.addEntity(hero);
		tile1.addEntity(hero1);
		tile2.addEntity(hero2);
		tiles.add(tile0);
		tiles.add(tile1);
		tiles.add(tile2);
		tiles.add(tile3);
		
		when(gameManagerMock.getMap()).thenReturn(mapMock);
        when(mapMock.getTilesAroundPoint(anyInt(), anyInt(), anyInt())).thenReturn(tiles);
		
        double heroDamage = hero.getDamage();
		double hero1Damage = hero1.getDamage();
		double hero2Damage = hero2.getDamage();
		
		
		AbstractAbility ability = new CallToArms();
		ability.useOnPoint(hero, 0, 0, 0);
		assertTrue(heroDamage*1.5 == hero.getDamage());
		assertTrue(hero1Damage*1.5 == hero1.getDamage());
		assertTrue(hero2Damage*1.5 == hero2.getDamage());
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		new GameLoop(10, new AtomicBoolean(false), gameManagerMock);
	}
	
	@AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }

}

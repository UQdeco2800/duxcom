package uq.deco2800.duxcom.abilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.AfterClass;
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
import uq.deco2800.duxcom.entities.heros.Cavalier;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * 
 * @author spress11
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GroundhogDayTest{

	@Test
	public void useAbility() throws Exception {
		AbstractHero hero = new Cavalier(1, 1);
		AbstractAbility ability = new GroundhogDay();
		ability.useOnFriend(hero, hero);
		hero.setX(10);
		hero.setY(5);
		assertEquals(hero.getX(), 10);
		assertEquals(hero.getY(), 5);
		hero.onEndTurn();
		assertEquals(hero.getX(), 1);
		assertEquals(hero.getY(), 1);
		
		
	}

}

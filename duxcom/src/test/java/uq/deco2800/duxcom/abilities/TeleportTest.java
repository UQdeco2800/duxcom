package uq.deco2800.duxcom.abilities;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.GameManager;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

/**
 * Created by lars06 on 7/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TeleportTest {
	@Mock
	private GameManager game;
	@Mock
	private MapAssembly map;
	
	@Ignore
	@Test
	public void useOnHero() throws Exception {
		AbstractHero hero = new Knight(1, 1);
		AbstractAbility teleport = new Teleport();
		when(game.getMap()).thenReturn(map);
		teleport.useOnPoint(hero, 3, 3, 1);
		assertEquals(3, hero.getX());
		assertEquals(3, hero.getY());
	}

}
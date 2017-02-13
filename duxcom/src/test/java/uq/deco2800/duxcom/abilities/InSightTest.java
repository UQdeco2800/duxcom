package uq.deco2800.duxcom.abilities;

import org.junit.Test;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Cavalier;
import uq.deco2800.duxcom.entities.heros.Priest;

import static org.junit.Assert.assertEquals;

/**
 * Created by spress11 on 03/09/2016.
 */
public class InSightTest {
	@Test
	public void useOnHero() throws Exception {
		AbstractHero hero = new Priest(1, 1);
		AbstractHero target = new Cavalier(1,2);
		AbstractAbility insight = new InSight();
		int expected = target.getVisibilityRange() + 3;
		insight.useOnFriend(hero, target);
		assertEquals(expected, target.getVisibilityRange());
	}

}
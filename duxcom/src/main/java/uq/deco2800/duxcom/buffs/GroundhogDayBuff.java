package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11.
 *
 */
public class GroundhogDayBuff extends AbstractBuff{
	private static final String NAME = "Ground Hog Day";
	private static final String DESCRIPTION = "Will return to the point you cast Groundhog Day at the end of your turn";
	private static final HeroStat STATAFFECTED = HeroStat.DAMAGE;
	private static int heroX;
	private static int heroY;

	public GroundhogDayBuff(AbstractHero hero, double strength, int duration) {
		super(strength, duration);
		heroX = hero.getX();
		heroY = hero.getY();
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}
	
	@Override
	public void onEndTurn(AbstractCharacter character) {
		if (!(character instanceof AbstractHero)) {
			return;
		}
		character.setX(heroX);
		character.setY(heroY);
	}
	
	
}

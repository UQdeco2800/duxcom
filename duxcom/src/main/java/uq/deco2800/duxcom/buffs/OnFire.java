package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.tiles.LiveTileType;

/**
 * 
 * Created by spress11 on 30-Aug-16.
 *
 */
public class OnFire extends AbstractBuff{
	private static final String NAME = "On Fire";
	private static final String DESCRIPTION = "You are on fire, losing health every turn";
	private static final HeroStat STATAFFECTED = HeroStat.HEALTH;
	
	public OnFire(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.ON_FIRE;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
		linkLiveTile(LiveTileType.FLAME);
	}

	@Override
	public void onTurn(AbstractCharacter character) {
		character.changeHealth(-strength);
	}

	@Override
	public String getIconTextureName() {
		return "onfire_icon";
	}
}
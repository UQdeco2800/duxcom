package uq.deco2800.duxcom.abilities;

import java.util.List;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.buffs.CallToArmsBuff;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.tiles.Tile;

/**
 * Created by lars06
 */
public class CallToArms extends AbstractAbility {
	
	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CallToArms.class);

	/**
	 * Attributes of the ability 'Call To Arms'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.CALLTOARMS);

	public CallToArms() {
		setStats(abilityDataClass);
	}
	
	@Override
	public boolean useOnFriend(AbstractCharacter origin, Targetable target) {
		List<Tile> tiles = GameLoop.getCurrentGameManager().getMap().getTilesAroundPoint(
					origin.getX(), origin.getY(), this.getAoeRange());
		for(Tile tile : tiles) {
			if (tile.isOccupied() && tile.getMovableEntity() instanceof AbstractHero) {
				logger.info("Adding buff to "+tile.getMovableEntity().getImageName());
				((AbstractHero)tile.getMovableEntity()).addBuff(new CallToArmsBuff(1.5, 2));
			}
		}
		this.setCooldownTurnsLeft(getCooldownTurns());
		return true;
	}
}

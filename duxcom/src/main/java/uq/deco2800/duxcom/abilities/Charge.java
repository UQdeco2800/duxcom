package uq.deco2800.duxcom.abilities;


import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;

/**
 * Created by spress11
 */
public class Charge extends AbstractAbility {

	/**
	 * Attributes of the ability 'Charge'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.CHARGE);

	public Charge() {
		setStats(abilityDataClass);
	}

    @Override
    public boolean areaOfEffect(int x, int y, int distance, MapAssembly map) {
    	setCooldownTurnsLeft(getCooldownTurns());
    	for (int i = y; i <= y + distance; i++) {
			Tile tile = map.getTile(x, i);
			if (!tile.isOccupied()) {
	    		continue;
	    	}
			Entity entity = tile.getMovableEntity();
			if (entity instanceof AbstractHero) {
				((AbstractHero) entity).changeHealth(getDamage() * getTemporaryDamageMultiplier());
			}
			else if (entity instanceof AbstractEnemy) {
				((AbstractEnemy) entity).changeHealth(getDamage() * getTemporaryDamageMultiplier());
			}
    	}
    	resetTemporaryDamageMultiplier();
    	return true;
    }
}

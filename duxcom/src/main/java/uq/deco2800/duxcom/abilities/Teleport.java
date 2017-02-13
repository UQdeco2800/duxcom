package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;

/**
 * Created by lars06
 */
public class Teleport extends AbstractAbility {

	/**
	 * Attributes of the ability 'Teleport'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.TELEPORT);

	public Teleport() {
		setStats(abilityDataClass);
	}

    @Override
    public boolean useOnFoe(AbstractCharacter origin, Targetable target) {
    	return false;
    }
    
    @Override
    public boolean useOnFriend(AbstractCharacter origin, Targetable target) {
    	return false;
    }
    
    @Override
    public boolean useOnPoint(AbstractCharacter origin, int x, int y, int elevation) {
    	if (!inRange(origin.getX(), origin.getY(), x, y) || this.onCooldown()) {
    		return false;
    	}
    	this.setCooldownTurnsLeft(getCooldownTurns());
    	GameManager game = GameLoop.getCurrentGameManager();
    	MapAssembly map = game.getMap();
    	Tile tile = map.getTile(x, y);

    	if (!tile.isOccupied()) {
    		//if tile is not occupied and target is Targetable, use ability
    		Entity entity = tile.getMovableEntity();
    		if (!(entity instanceof Targetable)) {
    			Entity castEntity = (Entity) origin;
    			castEntity.setX(x);
				castEntity.setY(y);
    			return true;
    		}
    	}
		return false;
    }
}

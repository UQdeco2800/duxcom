package uq.deco2800.duxcom.abilities;

import java.util.ArrayList;
import java.util.List;

import uq.deco2800.duxcom.buffs.OnFire;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;

import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;

/**
 * Created by spress11
 */
public class Fireball extends AbstractAbility {

	/**
	 * Attributes of the ability 'Fireball'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.FIREBALL);

	public Fireball() {
		setStats(abilityDataClass);
	}
	
	@Override
    public boolean areaOfEffect(int x, int y, int radius, MapAssembly map) {
		boolean status = super.areaOfEffect(x, y, radius, map);
		List<Tile> tiles = map.getTilesAroundPoint(x, y, radius);
		for (Tile tile : tiles) {

			if(tile.isOccupied() && tile.getMovableEntity() instanceof AbstractCharacter
					&& !(tile.hasLiveTile() && tile.getLiveTile().hasInstanceOfAppliedEffect(OnFire.class))) {
				((AbstractCharacter)tile.getMovableEntity()).addBuff(new OnFire(15, 2));
			}
		}
		return status;
	}

}
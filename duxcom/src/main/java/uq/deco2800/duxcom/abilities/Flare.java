package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

/**
 * Created by spress11
 */
public class Flare extends AbstractAbility {

	/**
	 * Attributes of the ability 'Flare'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.FLARE);

	public Flare() {
		setStats(abilityDataClass);
	}

    @Override
    public boolean areaOfEffect(int x, int y, int radius, MapAssembly map) {
    	GameManager gameManager = GameLoop.getCurrentGameManager();
    	gameManager.setTemporaryVisiblePoint(x, y, radius, 5);
    	this.setCooldownTurnsLeft(getCooldownTurns());
    	return true;
	}

}
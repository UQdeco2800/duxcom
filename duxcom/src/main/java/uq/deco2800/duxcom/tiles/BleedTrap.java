package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.Bleeding;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * LiveTile for spike traps that are intended to cause a bleeding effect
 * <p>
 * Created by jay-grant on 20/10/2016.
 */
public class BleedTrap extends LiveTile implements Targetable {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.BLEED_TRAP);

    public BleedTrap(int x, int y) {
        super(LiveTileType.BLEED_TRAP, x, y);
        setupLiveTile(liveTileDataClass);

        addEffect(new Bleeding(10, 3));
    }

    @Override
    public String getSyncString() {
        return "BLEED";
    }

    @Override
    public void changeHealth(double damage, DamageType damageType) {
        destroy();
    }
}

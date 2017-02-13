package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.OnFire;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * Wavy bundle of Long Grass
 * <p>
 * Created by jay-grant on 15/10/2016.
 */

public class LongGrassTile extends LiveTile implements Targetable {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.LONG_GRASS);

    public LongGrassTile(int x, int y) {
        super(LiveTileType.LONG_GRASS, x, y);
        setupLiveTile(liveTileDataClass);
    }

    @Override
    public String getSyncString() {
        return "LONG_GRASS";
    }

    @Override
    public void changeHealth(double damage, DamageType damageType) {
        destroy();
        switch (damageType) {
            case FIRE:
            case ELECTRIC:
            case EXPLOSIVE:
                applyEffect(new OnFire(20, 6));
                break;
            default:
                break;
        }
    }
}

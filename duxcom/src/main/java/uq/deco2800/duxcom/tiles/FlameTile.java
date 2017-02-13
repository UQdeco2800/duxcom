package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.OnFire;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * Flame tile intended for application to other tiles to give the appearance
 * of being on fire and apply OnFire to characters
 * <p>
 * Created by jay-grant on 20/10/2016.
 */
public class FlameTile extends LiveTile implements Targetable {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.FLAME);

    public FlameTile(int x, int y) {
        super(LiveTileType.FLAME, x, y);
        setupLiveTile(liveTileDataClass);

        addEffect(new OnFire(15, 3));
    }

    @Override
    public void changeHealth(double damage, DamageType damageType) {
        switch (damageType) {
            case WATER:
                destroy();
                break;
            case FIRE:
            case ELECTRIC:
            case EXPLOSIVE:
                break;
            default:
                destroy();
                break;
        }
    }

    @Override
    public String getSyncString() {
        return "FLAME";
    }
}

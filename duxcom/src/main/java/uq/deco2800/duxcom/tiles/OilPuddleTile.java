package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.buffs.OnFire;
import uq.deco2800.duxcom.buffs.Wet;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.List;

/**
 * If found please return to BP
 *
 * Created by jay-grant on 23/10/2016.
 */
public class OilPuddleTile extends LiveTile implements Targetable {

    boolean burning = false;

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.OIL);

    public OilPuddleTile(int x, int y) {
        super(LiveTileType.OIL, x, y);
        setupLiveTile(liveTileDataClass);
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

    @Override
    public void applyEffect(AbstractBuff effect) {
        if (effect instanceof OnFire) {
            burning = true;
            super.applyEffect(effect);
        }
    }

    @Override
    public void animationTick() {
        if ((burning && hasAppliedEffect()) || (!burning && !hasAppliedEffect())) {
            super.animationTick();
        } else {
            GameLoop.getCurrentGameManager().getMap().getTile(super.x, super.y).removeLiveTile();
        }

    }

    @Override
    public void changeHealth(double damage, DamageType damageType) {
        if (damageType == DamageType.ELECTRIC || damageType == DamageType.EXPLOSIVE || damageType == DamageType.FIRE) {
            List<Coordinate> surrounds = GameLoop.getCurrentGameManager().getMap()
                    .getSurroundingLiveTileCoordinates(LiveTileType.OIL, super.x, super.y, 7);
            for (Coordinate coord :
                    surrounds) {
                GameLoop.getCurrentGameManager().getMap().getLiveTile(coord.x, coord.y).applyEffect(new OnFire(15, 5));
            }
        }
    }

    @Override
    public String getSyncString() {
        return "OIL";
    }
}

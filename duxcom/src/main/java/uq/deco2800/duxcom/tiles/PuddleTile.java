package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.Wet;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.List;

/**
 * Shimmery puddle of water
 * <p>
 * Created by jay-grant on 17/10/2016.
 */
public class PuddleTile extends LiveTile implements Targetable {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.PUDDLE);

    public PuddleTile(int x, int y) {
        super(LiveTileType.PUDDLE, x, y);
        setupLiveTile(liveTileDataClass);

        addEffect(new Wet(5, 5));
    }

    @Override
    public void changeHealth(double damage, DamageType damageType) {
        if (damageType == DamageType.ELECTRIC) {
            AbstractCharacter target;
            List<Coordinate> surrounds = GameLoop.getCurrentGameManager().getMap()
                    .getSurroundingLiveTileCoordinates(LiveTileType.PUDDLE, super.x, super.y, 5);
            for (Coordinate coord :
                    surrounds) {
                if (GameLoop.getCurrentGameManager().getMap().getTile(coord.x, coord.y).hasCharacter()) {
                    target = (AbstractCharacter) GameLoop.getCurrentGameManager().getMap()
                             .getTile(coord.x, coord.y).getEntities().get(0);
                    target.changeHealth(damage);
                }
            }

        }
    }

    @Override
    public String getSyncString() {
        return "PUDDLE";
    }
}

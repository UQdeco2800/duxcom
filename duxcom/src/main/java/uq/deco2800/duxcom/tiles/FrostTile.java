package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.buffs.Slow;
import uq.deco2800.duxcom.buffs.Wet;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * Frost tile make it too hold to go out without sogs on ur feet
 * <p>
 * Created by jay-grant on 20/10/2016.
 */
public class FrostTile extends LiveTile {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.FROST);

    public FrostTile(int x, int y) {
        super(LiveTileType.FROST, x, y);
        setupLiveTile(liveTileDataClass);

        addEffect(new Wet(10, 3));
        addEffect(new Slow(10, 3));
    }

    @Override
    public String getSyncString() {
        return "FROST";
    }
}
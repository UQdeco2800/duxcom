package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.buffs.Poisoned;
import uq.deco2800.duxcom.buffs.Slow;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * Shrek, a green ogre who loves the solitude in his swamp, finds his life
 * interrupted when many fairytale characters are exiled there by order of
 * the fairytale-hating Lord Farquaad. Shrek tells them that he will go ask
 * Farquaad to send them back. He brings along a talking Donkey who is the only
 * fairytale creature who knows the way to Duloc.
 * <p>
 * Created by jay-grant on 20/10/2016.
 */
public class SwampTile extends LiveTile {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.SWAMP);

    public SwampTile(int x, int y) {
        super(LiveTileType.SWAMP, x, y);
        setupLiveTile(liveTileDataClass);

        addEffect(new Poisoned(10, 3));
        addEffect(new Slow(10, 3));
    }

    @Override
    public String getSyncString() {
        return "SWAMP";
    }
}

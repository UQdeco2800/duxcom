package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.buffs.Poisoned;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * Sulfur mustard, commonly known as mustard gas, is a cytotoxic and vesicant
 * chemical warfare agent with the ability to form large blisters on the
 * exposed skin and in the lungs. Related chemical compounds with similar
 * chemical structure and similar properties form a class of compounds known
 * collectively as sulfur mustards or mustard agents. Pure sulfur mustards are
 * colorless, viscous liquids at room temperature. When used in impure form,
 * such as warfare agents, they are usually yellow-brown in color and have an
 * odor resembling mustard plants, garlic, or horseradish, hence the name.
 * Sulfur mustard was originally assigned the name LOST, after the scientists
 * Wilhelm Lommel and Wilhelm Steinkopf, who developed a method of large-scale
 * production for the Imperial German Army in 1916.
 * <p>
 * Mustard agents are regulated under the 1993 Chemical Weapons Convention
 * (C.W.C.). Three classes of chemicals are monitored under this Convention,
 * with sulfur and nitrogen mustard grouped in Schedule 1, as substances with
 * no use other than in chemical warfare. Mustard agents could be deployed on
 * the battlefield by means of artillery shells, aerial bombs, rockets, or by
 * spraying from warplanes.
 * <p>
 * Created by jay-grant on 20/10/2016.
 */
public class PoisonTile extends LiveTile {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.POISON_TRAP);

    public PoisonTile(int x, int y) {
        super(LiveTileType.POISON_TRAP, x, y);
        setupLiveTile(liveTileDataClass);

        addEffect(new Poisoned(10, 3));
    }

    @Override
    public String getSyncString() {
        return "POISON";
    }
}

package uq.deco2800.duxcom.passives;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.buffs.DefenderBuff;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;

import java.util.List;

/**
 * Created by shamous123
 */
public class Defender extends AbstractPassive{

	private static final String NAME = "Defender";
	private static final int ACTIVATIONTIMER = 1;

	public Defender(AbstractHero hero) {
		super.hero = hero;
		super.name = NAME;
		super.activationTimer = ACTIVATIONTIMER;
		super.description = "Give armour to nearby heroes";
	}

    @Override
    public void turnTick() {
        MapAssembly map = GameLoop.getCurrentGameManager().getMap();
        int x = this.hero.getX();
        int y = this.hero.getY();

        List<Tile> affectedTiles = map.getTilesAroundPoint(x,y,2);

        for(Tile tile : affectedTiles) {
            for(Entity entity : tile.getEntities()) {
                if(entity instanceof AbstractHero) {
                    AbstractHero hero = (AbstractHero) entity;
                    hero.addBuff(new DefenderBuff(5,1));
                }
            }
        }
    }

}

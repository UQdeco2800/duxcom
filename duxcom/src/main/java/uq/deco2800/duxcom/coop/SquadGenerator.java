package uq.deco2800.duxcom.coop;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.entities.heros.Cavalier;
import uq.deco2800.duxcom.entities.heros.Knight;

import java.util.UUID;

/**
 * The manager of in game squads.
 *
 * Created by liamdm on 19/10/2016.
 */
public class SquadGenerator {

    /**
     * Generate a squad for the given player of a given size
     * @param owner owner of the squad
     * @param size size of the squad
     * @return the squad generated
     */
    public static Squad generateSquad(String owner, int size, int spawnX, int spawnY){
        int cX = spawnX;
        int cY = spawnY;

        Squad s = new Squad(owner);
        for(int i = 0; i < size; ++i){
            int n = i % 3;

            AbstractHero toAdd;
            String heroName = String.valueOf(UUID.randomUUID());

            if(n == 0){
                toAdd = new Knight(cX, cY);
            } else if(n == 1){
                toAdd = new Archer(cX, cY);
            } else {
                toAdd = new Cavalier(cX, cY);
            }

            cX++;

            s.addHero(heroName, toAdd);
        }
        return s;
    }

}

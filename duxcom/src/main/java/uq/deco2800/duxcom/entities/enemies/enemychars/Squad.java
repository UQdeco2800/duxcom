package uq.deco2800.duxcom.entities.enemies.enemychars;

import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay-grant on 24/08/2016.
 * <p>
 * Container class for all pre-made enemies 'Squads'
 * To be accessed by EnemyManager
 * To be continued...
 */
public class Squad {

    private int x;
    private int y;
    ArrayList<AbstractEnemy> squadMembers;

    /**
     * SquadGenerator constructor
     */
    public Squad() {
        squadMembers = new ArrayList<>();
    }

    /**
     * Returns a squad given some creation parameters
     *
     * @param x    the x location of the squad
     * @param y    the y location of the squad
     * @param seed the seed to generate the squad
     * @return the list of squad members
     */
    public List<AbstractEnemy> getSquad(int x, int y, int seed) {
        this.x = x;
        this.y = y;
        squadMembers.clear();
        populateSquad(seed % 5);
        return squadMembers;
    }

    private void populateSquad(int seed) {
        generateFromSeed(seed);
    }

    /**
     * Generate an undefeatable babushka doll of enemies
     * (they are all inside of each other for now)
     **/
    private void generateFromSeed(int seed) {

        switch (seed) {
            case 0:
                squadMembers.add(new EnemyGrunt(x, y));
                squadMembers.add(new EnemyGrunt(x, y));
                squadMembers.add(new EnemyKnight(x, y));
                squadMembers.add(new EnemyKnight(x, y));
                break;
            case 1:
                squadMembers.add(new EnemyGrunt(x, y));
                squadMembers.add(new EnemyKnight(x, y));
                squadMembers.add(new EnemyBrute(x, y));
                squadMembers.add(new EnemyBrute(x, y));
                break;
            case 2:
                squadMembers.add(new EnemyKnight(x, y));
                squadMembers.add(new EnemyKnight(x, y));
                squadMembers.add(new EnemyDarkMage(x, y));
                squadMembers.add(new EnemyTank(x, y));
                break;
            case 3:
                squadMembers.add(new EnemyGrunt(x, y));
                squadMembers.add(new EnemyGrunt(x, y));
                squadMembers.add(new EnemyDarkMage(x, y));
                squadMembers.add(new EnemyArcher(x, y));
                break;
            default:
                squadMembers.add(new EnemyBrute(x, y));
                squadMembers.add(new EnemyDarkMage(x, y));
                squadMembers.add(new EnemyArcher(x, y));
                squadMembers.add(new EnemySupport(x, y));
                break;
        }
    }
}

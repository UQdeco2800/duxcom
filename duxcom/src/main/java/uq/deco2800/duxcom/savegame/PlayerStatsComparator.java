package uq.deco2800.duxcom.savegame;

import java.util.Comparator;

import uq.deco2800.singularity.common.representations.duxcom.PlayerStats;

public class PlayerStatsComparator implements Comparator<PlayerStats>{

    @Override
    public int compare(PlayerStats stats0, PlayerStats stats1) {
        return Integer.parseInt(stats1.getScore()) - Integer.parseInt(stats0.getScore());
    }

}

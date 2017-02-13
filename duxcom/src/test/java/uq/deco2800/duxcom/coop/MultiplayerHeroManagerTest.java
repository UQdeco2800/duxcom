package uq.deco2800.duxcom.coop;

import org.junit.BeforeClass;
import org.junit.Test;
import uq.deco2800.duxcom.auth.LoginManager;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liamdm on 21/10/2016.
 */
public class MultiplayerHeroManagerTest {
    private static List<String> players;

    @BeforeClass
    public static void setup(){
        players = new LinkedList<String>() {{
            add("ta");
            add("tb");
        }};
    }

    @Test
    public void killPlayer() throws Exception {
        MultiplayerHeroManager mhm = new MultiplayerHeroManager(new MapAssembly(new DemoMap("test")));
        mhm.generateHeroes(players);
        assertFalse(mhm.killPlayer("ta"));
    }

}
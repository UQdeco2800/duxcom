package uq.deco2800.duxcom.tiles;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by jay-grant on 23/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PuddleTileTest {

    @Test
    public void test() {
        PuddleTile puddleTile = new PuddleTile(1, 1);
    }
}


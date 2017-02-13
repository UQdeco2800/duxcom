package uq.deco2800.duxcom.maps.mapgen.textures;

import org.junit.Test;
import uq.deco2800.duxcom.tiles.TileType;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static uq.deco2800.duxcom.tiles.TileType.*;

/**
 * Test the TextureVariant class
 *
 * Created by Wai Min Khant on 21/10/16.
 */
public class TextureVariantTest {

    TextureVariant textureVariant = new TextureVariant(REAL_GRASS_1);

    @Test
    public void testBasicMethod() {
        assertEquals(REAL_GRASS_1, textureVariant.getOriginTexture());
    }

    @Test
    public void testGetVariant() {
        textureVariant.addVariant(REAL_GRASS_1);
        textureVariant.addVariant(REAL_GRASS_2);
        textureVariant.addVariant(WATER);
        Random random = new Random();
        TileType tile = textureVariant.getVariant(random);
        assertNotNull(tile);
    }
}

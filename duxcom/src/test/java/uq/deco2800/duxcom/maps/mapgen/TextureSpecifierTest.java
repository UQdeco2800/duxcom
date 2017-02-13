package uq.deco2800.duxcom.maps.mapgen;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the TextureSpecifier class
 *
 * @author Alex McLean
 */
public class TextureSpecifierTest {

    private TextureSpecifier textureSpecifier;

    @Test
    public void testBasicMethods() {
        textureSpecifier = new TextureSpecifier("textureName", "textureLocation", true);
        assertEquals("textureName", textureSpecifier.getTextureName());
        assertEquals("textureLocation", textureSpecifier.getTextureLocation());
        assertTrue(textureSpecifier.isOverridable());

        textureSpecifier.setTextureName("newTextureName");
        assertEquals("newTextureName", textureSpecifier.getTextureName());

        textureSpecifier.setTextureLocation("newTextureLocation");
        assertEquals("newTextureLocation", textureSpecifier.getTextureLocation());

        textureSpecifier.setCanOverride(false);
        assertFalse(textureSpecifier.isOverridable());

        textureSpecifier.setTileType(1);
        assertEquals(1, textureSpecifier.getTileType());
    }

    @Test
    public void testStringConstructor() {
        textureSpecifier = new TextureSpecifier(null);
        assertNull(textureSpecifier.getTextureName());

        textureSpecifier = new TextureSpecifier("");
        assertNull(textureSpecifier.getTextureName());

        textureSpecifier = new TextureSpecifier("no-commas");
        assertNull(textureSpecifier.getTextureName());

        boolean exceptionThrown = false;
        try {
            textureSpecifier = new TextureSpecifier("1,2,3");
        } catch (TextureSpecifier.TextureSpecifierRuntimeException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        textureSpecifier = new TextureSpecifier("textureName", "textureLocation", true);
        textureSpecifier.setTileType(0);
        String encoded = textureSpecifier.encode();
        textureSpecifier = new TextureSpecifier(encoded);

        assertEquals("textureName", textureSpecifier.getTextureName());
        assertEquals("textureLocation", textureSpecifier.getTextureLocation());
        assertTrue(textureSpecifier.isOverridable());
        assertEquals(0, textureSpecifier.getTileType());
        assertEquals(encoded, textureSpecifier.toString());
        assertEquals(encoded.hashCode(), textureSpecifier.hashCode());

        TextureSpecifier textureSpecifierDupe = new TextureSpecifier("textureName", "textureLocation", true);
        textureSpecifierDupe.setTileType(0);
        assertFalse(textureSpecifier.equals(1));
        assertTrue(textureSpecifier.equals(textureSpecifierDupe));
    }

}
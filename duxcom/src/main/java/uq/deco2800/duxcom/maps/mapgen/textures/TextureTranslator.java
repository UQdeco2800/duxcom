package uq.deco2800.duxcom.maps.mapgen.textures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.maps.mapgen.TextureSpecifier;

import java.util.HashMap;

/**
 * Overrides existing resources in either OldTileRegister or OldEntityRegister.
 *
 * Created by liamdm on 17/08/2016.
 */
public class TextureTranslator {

    private static Logger logger = LoggerFactory.getLogger(TextureSpecifier.class);

    /**
     * Stores the translations between the requested strings and the strings
     * in image register
     */
    private HashMap<String, String> translations = new HashMap<>();

    /**
     * Adds a translation between two texture names
     * @param oldTextureName the name of the texture to override
     * @param newTextureName the name of the new texture to use
     */
    public void addTranslation(String oldTextureName, String newTextureName){
        translations.put(oldTextureName, newTextureName);
    }


    /**
     * Translates a texture to the relevant texture pack texture
     *
     * @require texture != null && !texture.isEmpty()
     * @param texture
     */
    public String translate(String texture){
        // TODO ASSERT REMOVE ON RELEASE
        // <editor-fold desc="Assert statements here, do not remove till release!">
        if(texture == null || texture.isEmpty()){
            logger.error("Texture cannot be null or empty!");
        }
        // </editor-fold>

        if (translations.containsKey(texture)) {
            return translations.get(texture);
        }

        // Return the default texture
        return texture;
    }



}

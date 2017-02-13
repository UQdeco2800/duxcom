package uq.deco2800.duxcom.maps.mapgen;

import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stores universal texture data in a format that can be retrieved and overriden by
 * texture packs, does not replace *Register, but allows for the dynamic
 * loading and specifying of textures in the game designer
 * <p>
 * Created by liamdm on 16/08/2016.
 */
public class TextureSpecifier {

    public static class TextureSpecifierRuntimeException extends RuntimeException {

        /**
         * Exception to be thrown for errors due to TextureSpecifiers
         *
         * @param string the exception message
         */
        public TextureSpecifierRuntimeException(String string) {
            super(string);
        }

    }

    private static Logger logger = LoggerFactory.getLogger(TextureSpecifier.class);

    private static int tileTypeCurrent = 0;
    private String textureName;
    private String textureLocation;
    private boolean canOverride = false;
    private int tileType = 0;

    /**
     * Creates a new texture specifier
     *
     * @param textureName     the name of the texture
     * @param textureLocation the relative path to the texture
     * @param canOverride     if this can be overriden in a texture pack
     * @require width > 1 && height > 1 && !textureName.isEmpty() && tileType > 0 && !textureLocation.isEmpty()
     */
    public TextureSpecifier(String textureName, String textureLocation, Boolean canOverride) {

        this.tileType = tileTypeCurrent;
        ++tileTypeCurrent;

        this.textureName = textureName;
        this.textureLocation = textureLocation;
        this.canOverride = canOverride;
    }

    /**
     * Decodes a texture specifier back into its data
     *
     * @param encoded the encoded representation
     * @require encoded is a valid encoded string
     */
    public TextureSpecifier(String encoded) {

        // TODO ASSERT REMOVE ON RELEASE
        // <editor-fold desc="Assert statements here, do not remove till release!  ">
        if (encoded == null || encoded.isEmpty() || !encoded.contains(",")) {
            logger.error("Encoded texture specifier string is not valid: " + encoded);
            return;
        }
        // </editor-fold>

        String[] parts = encoded.split(",");
        if (parts.length != 4) {
            throw new TextureSpecifierRuntimeException("Encoded texture specifier string is not valid: " + encoded);
        }

        tileType = Integer.parseInt(Base64.decodeAsString(parts[0]));
        textureName = Base64.decodeAsString(parts[1]);
        textureLocation = Base64.decodeAsString(parts[2]);
        canOverride = Boolean.parseBoolean(parts[3]);
    }

    /**
     * Encodes a string representation of this specifier that can be used to re-generate it
     *
     * @return the encoded string
     */
    public String encode() {
        StringBuilder sb = new StringBuilder();
        sb.append(Base64.encodeAsString(String.valueOf(tileType).getBytes()));
        sb.append(",");
        sb.append(Base64.encodeAsString(textureName.getBytes()));
        sb.append(",");
        sb.append(Base64.encodeAsString(textureLocation.getBytes()));
        sb.append(",");
        sb.append(canOverride);
        return sb.toString();
    }

    @Override
    public String toString() {
        return encode();
    }

    @Override
    public int hashCode() {
        return encode().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TextureSpecifier)) {
            return false;
        } else {
            TextureSpecifier test = (TextureSpecifier) other;
            return this.textureName.equals(test.textureName) && this.textureLocation.equals(test.textureLocation) &&
                    this.canOverride == test.canOverride && this.tileType == test.tileType;
        }
    }

    /**
     * Gets the tile type this texture applies to
     *
     * @return the tile type
     */
    public int getTileType() {
        return tileType;
    }

    /**
     * Sets the tile type this texture applies to
     *
     * @param tileType
     */
    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    /**
     * Gets if the texture can be overridden
     *
     * @return true iff can be overridden
     */
    public boolean isOverridable() {
        return canOverride;
    }

    /**
     * Sets if the texture can be overridden in a texture pack
     *
     * @param canOverride
     */
    public void setCanOverride(boolean canOverride) {
        this.canOverride = canOverride;
    }

    /**
     * Gets the recommended location to the texture file
     *
     * @return the texture location
     */
    public String getTextureLocation() {
        return textureLocation;
    }

    /**
     * Sets the recommended location to the texture file
     *
     * @param textureLocation
     */
    public void setTextureLocation(String textureLocation) {
        this.textureLocation = textureLocation;
    }

    /**
     * Gets the name of the texture
     *
     * @return the name of the texture
     */
    public String getTextureName() {
        return textureName;
    }

    /**
     * Sets the name of the texture
     *
     * @param textureName
     */
    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }
}
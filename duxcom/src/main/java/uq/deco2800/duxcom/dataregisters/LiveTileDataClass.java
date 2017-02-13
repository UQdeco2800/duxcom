package uq.deco2800.duxcom.dataregisters;

import java.util.ArrayList;
import java.util.List;

/**
 * A data class for storing an abstract representation of data will is to be
 * transferred to every new instance of LiveTile
 *
 * Created by jay-grant on 13/10/2016.
 */

public class LiveTileDataClass extends TileDataClass {

    private int numberFrames;
    private int numberBaseFrames;
    private long tickInterval;
    boolean loop;
    boolean baseLoop;
    boolean destructible;

    /**
     * Class constructor to set all the required parameters
     *
     * @param tileTypeName          the name
     * @param tileTextureName       the foundation Texture String
     * @param minimapColorString    minimap colour
     * @param numberFrames          number of top layer frames
     * @param loop                  animation loop
     * @param numberBaseFrames      number of bottom layer frames
     * @param baseLoop              base animation loop
     * @param tickInterval          delay between frames
     */
    public LiveTileDataClass(String tileTypeName, String tileTextureName,
                             String minimapColorString, int numberFrames, boolean loop,
                             int numberBaseFrames, boolean baseLoop,
                             long tickInterval, boolean destructible) {
        super(tileTypeName, tileTextureName, minimapColorString);
        super.setTileTextureName(tileTextureName);
        this.numberFrames = numberFrames;
        this.numberBaseFrames = numberBaseFrames;
        this.loop = loop;
        this.baseLoop = baseLoop;
        this.tickInterval = tickInterval;
        this.destructible = destructible;
    }
    
    /**
     * Class constructor to set all the required parameters
     *
     * @param tileTypeName          the name
     * @param tileTextureName       the foundation Texture String
     * @param minimapColorString    minimap colour
     * @param numberFrames          number of top layer frames
     * @param loop                  animation loop
     * @param numberBaseFrames      number of bottom layer frames
     * @param baseLoop              base animation loop
     * @param tickInterval          delay between frames
     * @param movementModifier	An integer that multiplies the cost of movement
     */
    public LiveTileDataClass(String tileTypeName, String tileTextureName,
                             String minimapColorString, int numberFrames, boolean loop,
                             int numberBaseFrames, boolean baseLoop,
                             long tickInterval, boolean destructible, int movementModifier) {
        super(tileTypeName, tileTextureName, minimapColorString, movementModifier);
        super.setTileTextureName(tileTextureName);
        super.setMovementModifier(movementModifier);
        this.numberFrames = numberFrames;
        this.numberBaseFrames = numberBaseFrames;
        this.loop = loop;
        this.baseLoop = baseLoop;
        this.tickInterval = tickInterval;
        this.destructible = destructible;
    }

    /**
     * Private helper to automate the construction of the sequence of Texture
     * Strings based on the foundation Texture String for the Top Layer Frames.
     *
     * @return list of top layer frame strings.
     */
    private List<String> buildFrames() {
        List<String> frames = new ArrayList<>();
        if (numberFrames == 0) {
            frames.add("dead_pixel");
            return frames;
        }
        for (int i = 0; i < numberFrames; i++) {
            frames.add(i, super.getTileTextureName() + "_" + (i + 1));
        }
        return frames;
    }

    /**
     * same as buildFrames() but for the Bottom Layer Frames.
     *
     * @return list of bottm layer frame strings.
     */
    private List<String> buildBaseFrames() {
        List<String> frames = new ArrayList<>();
        if (numberBaseFrames == 0) {
            frames.add("dead_pixel");
            return frames;
        }
        for (int i = 0; i < numberBaseFrames; i++) {
            frames.add(i, super.getTileTextureName() + "_base_" + (i + 1));
        }
        return frames;
    }

    public String getDestroyedFrame() {
        if (destructible) {
            return super.getTileTextureName() + "_destroyed";
        } else {
            return "dead_pixel";
        }
    }

    /**
     * Gets the tickInterval of the data class.
     *
     * @return The data class' tickInterval.
     */
    public long getTickInterval() {
        return tickInterval;
    }

    /**
     * Gets the number of top layer frames of the data class.
     *
     * @return The data class' number of top layer frames.
     */
    public int getNumberFrames() {
        return numberFrames;
    }

    public void setNumberFrames(int numberFrames) {
        this.numberFrames = numberFrames;
    }

    /**
     * Gets the number of bottom layer frames of the data class.
     *
     * @return The data class' number of bottom layer frames.
     */
    public int getNumberBaseFrames() {
        return numberBaseFrames;
    }

    /**
     * Gets the list of top layer frame strings of the data class.
     *
     * @return The data class' list of top layer frame strings.
     */
    public List<String> getFrames() {
        return buildFrames();
    }

    /**
     * Gets the list of bottom layer frame strings of the data class.
     *
     * @return The data class' list of bottom layer frame strings.
     */
    public List<String> getBaseFrames() {
        return buildBaseFrames();
    }

    /**
     * Gets the top layer loop boolean of the data class.
     *
     * @return The data class' top layer loop boolean.
     */
    public boolean getLoop() {
        return loop;
    }

    /**
     * Gets the bottom layer loop boolean of the data class
     *
     * @return The data class' bottom layer loop boolean
     */
    public boolean getBaseLoop() {
        return baseLoop;
    }

    public boolean isDestructible() {
        return destructible;
    }
}

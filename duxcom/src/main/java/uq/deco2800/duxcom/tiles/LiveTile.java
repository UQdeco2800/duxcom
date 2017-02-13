package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.util.AnimationTickable;

import java.util.ArrayList;
import java.util.List;

/**
 * woody's sideshow alley
 * <p>
 * Created by jay-grant on 13/10/2016.
 */
public class LiveTile extends Tile implements AnimationTickable {

    private LiveTileType liveTileType;
    private boolean loop;
    private boolean reverse;
    private boolean baseLoop;
    private boolean baseReverse;
    int currentFrameIndex;
    int currentBaseFrameIndex;
    private int numberFrames;
    private int numberBaseFrames;
    private long tickInterval;
    private boolean destructible;

    private List<AbstractBuff> passiveEffects;
    private List<AbstractBuff> currentEffects;
    private List<LiveTile> currentLiveTiles;
    private List<String> frames;
    private List<String> bases;
    private String destroyedFrame;
    private boolean destroyed = false;

    int x;
    int y;
    double elevation;

    /**
     * Initialises the LiveTile given a LiveTileType and position x,y
     */
    public LiveTile(LiveTileType liveTileType, int x, int y) {
        super(TileType.LT);
        this.liveTileType = liveTileType;
        this.currentFrameIndex = 0;
        this.currentBaseFrameIndex = 0;
        passiveEffects = new ArrayList<>();
        currentEffects = new ArrayList<>();
        currentLiveTiles = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.register();
        this.elevation = 0;
    }

    public void setupLiveTile(LiveTileDataClass dataClass) {
        this.frames = dataClass.getFrames();
        this.bases = dataClass.getBaseFrames();
        this.numberFrames = dataClass.getNumberFrames();
        this.numberBaseFrames = dataClass.getNumberBaseFrames();
        this.loop = dataClass.getLoop();
        this.baseLoop = dataClass.getBaseLoop();
        this.tickInterval = dataClass.getTickInterval();
        this.destructible = dataClass.isDestructible();
        this.destroyedFrame = dataClass.getDestroyedFrame();
    }

    public String getCurrentFrameTexture() {
        if (destroyed || numberFrames == 0) {
            return "dead_pixel";
        }
        return frames.get(currentFrameIndex);
    }

    public String getCurrentBaseTexture() {
        if (destroyed) {
            return destroyedFrame;
        }
        if (numberBaseFrames == 0) {
            return "dead_pixel";
        }
        return bases.get(currentBaseFrameIndex);
    }

    @Override
    public String getSyncString() {
        return "";
    }


    @Override
    public long getTickInterval() {
        return this.tickInterval;
    }

    @Override
    public void animationTick() {
        nextFrame();
        nextBaseFrame();
    }

    @Override
    public void turnTick() {
        AbstractBuff.tickBuffs(currentEffects, this);
        animationTick();
    }

    /**
     * Set the frame index to point to the next frame in both the top layer and
     * bottom layer lists. If the LiveTile is set to loop then then the index
     * will restart after reaching the final frame else it will rewind.
     */
    private void nextFrame() {
        if (numberFrames == 0) {
            return;
        }

        if (!reverse) {
            currentFrameIndex++;
            if (currentFrameIndex >= numberFrames) {
                if (loop) {
                    currentFrameIndex = 0;
                } else {
                    currentFrameIndex--;
                    currentFrameIndex--;
                    reverse = true;
                }
            }
        } else {
            currentFrameIndex--;
            if (currentFrameIndex == 0) {
                reverse = false;
            }
        }
    }

    /**
     * Same as nextFrame() but on the BaseFrames (bottom layer) List.
     */
    private void nextBaseFrame() {
        if (numberBaseFrames == 0) {
            return;
        }

        if (!baseReverse) {
            currentBaseFrameIndex++;
            if (currentBaseFrameIndex >= numberBaseFrames) {
                if (baseLoop) {
                    currentBaseFrameIndex = 0;
                } else {
                    currentBaseFrameIndex--;
                    currentBaseFrameIndex--;
                    baseReverse = true;
                }
            }
        } else {
            currentBaseFrameIndex--;
            if (currentBaseFrameIndex == 0) {
                baseReverse = false;
            }
        }
    }

    /**
     * Gets the reverse status of the LiveTile animation
     *
     * @return true iff reversing
     */
    public boolean isReverse() {
        return reverse;
    }

    /**
     * Gets the reverse status of the LiveTile base animation
     *
     * @return true iff reversing base
     */
    public boolean isBaseReverse() {
        return baseReverse;
    }

    /**
     * Returns the x position at which the LiveTile is located.
     *
     * @return the LiveTile's x position.
     */
    public int getX() {
        return x;
    }

    /**
     * Updates the LiveTiles x position.
     *
     * @param x the new x position.
     */
    public void setX(int x) {
        if (x >= 0) {
            this.x = x;
        }
    }

    /**
     * Returns the x position at which the LiveTile is located.
     *
     * @return the LiveTile's x position.
     */
    public int getY() {
        return y;
    }

    /**
     * Updates the LiveTiles y position.
     *
     * @param y the new y position.
     */
    public void setY(int y) {
        if (y >= 0) {
            this.y = y;
        }
    }

    public void addEffect(AbstractBuff buff) {
        this.passiveEffects.add(buff);
    }

    /**
     * Returns a copy of the List off AbstractBuffs that the LiveTile
     * is expected to apply to characters that stand on it.
     *
     * @return List of Buffs that the LiveTile applies.
     */
    public List<AbstractBuff> getPassiveEffects() {
        return new ArrayList<>(passiveEffects);
    }

    /**
     * Returns the LiveTileType.
     *
     * @return the LiveTileType.
     */
    public LiveTileType getLiveTileType() {
        return liveTileType;
    }

    /**
     * Sets the LiveTileType.
     *
     * @param liveTileType the new LiveTileType.
     */
    public void setLiveTileType(LiveTileType liveTileType) {
        this.liveTileType = liveTileType;
    }

    /**
     * Returns true iff the LiveTile has Buffs to apply.
     *
     * @return true iff the LiveTile has Buffs to apply.
     */
    public boolean hasEffect() {
        return !this.passiveEffects.isEmpty();
    }

    /**
     * Returns the number of frames in the TOP layer of animation.
     *
     * @return number frames.
     */
    public int getNumberFrames() {
        return numberFrames;
    }

    /**
     * Returns the number of frames in the BOTTOM layer of animation.
     *
     * @return number frames.
     */
    public int getNumberBaseFrames() {
        return numberBaseFrames;
    }

    /**
     * Gets the elevation of the entity
     *
     * @return the elevation
     */
    public double getElevation() {
        return elevation;
    }

    /**
     * Sets the elevation of the entity
     *
     * @return the new elevation
     */
    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    /**
     * Returns a copy of the List off AbstractBuffs that the LiveTile
     * currently has applied to it.
     *
     * @return Buffs applied to tile.
     */
    public List<AbstractBuff> getAppliedEffects() {
        return new ArrayList<>(currentEffects);
    }

    /**
     * Returns true if the LiveTile has any effects applied to it
     *
     * @return true iff LiveTile has currentEffects
     */
    public boolean hasAppliedEffect() {
        return !currentEffects.isEmpty();
    }


    public boolean hasInstanceOfAppliedEffect(Class<?> c) {
        for (AbstractBuff buff :
                currentEffects) {
            if (c.equals(buff.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an AbstractBuff to the List of Buffs that the LiveTile currently
     * has applied to it.
     *
     * @param effect the buff for the LiveTile to have applied to it.
     */
    public void applyEffect(AbstractBuff effect) {
        currentEffects.add(effect);
        if (effect.hasAssociatedLiveTile()) {
            currentLiveTiles.add(getNewLiveTile(effect.getAssociatedLiveTileType(), x, y));
        }
    }

    public List<LiveTile> getEffectLiveTiles() {
        return new ArrayList<>(currentLiveTiles);
    }

    public List<AbstractBuff> getAllEffects() {
        List<AbstractBuff> ret = new ArrayList<>(passiveEffects);
        ret.addAll(currentEffects);
        return ret;
    }

    /**
     * Clears every effect (AbstractBuff) currently contained with the LiveTile
     */
    public void clearEffects() {
        passiveEffects.clear();
        currentEffects.clear();
        currentLiveTiles.clear();
    }

    public void setNumberFrames(int numberFrames) {
        this.numberFrames = numberFrames;
    }

    public void setNumberBaseFrames(int numberBaseFrames) {
        this.numberBaseFrames = numberBaseFrames;
    }

    public void destroy() {
        if (destructible) {
            passiveEffects.clear();
            currentEffects.clear();
            destroyed = true;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Static method for convering a LiveTileType to an actual instance of the
     * corresponding livetile.
     *
     * @param liveTileType the type of live tile
     * @param x            x position
     * @param y            y position
     * @return appropriate instance of LiveTile
     */
    public static LiveTile getNewLiveTile(LiveTileType liveTileType, int x, int y) {

        switch (liveTileType) {
            case FLAT_LAVA:
                return new FlatLava(x, y);
            case LAVA:
                return new LavaTile(x, y);
            case LONG_GRASS:
                return new LongGrassTile(x, y);
            case PUDDLE:
                return new PuddleTile(x, y);
            case CAVE_TWO:
                return new CaveTwo(x, y);
            case CAVE_THREE:
                return new CaveThree(x, y);
            case CAVE_FOUR:
                return new CaveFour(x, y);
            case FLAME:
                return new FlameTile(x, y);
            case BLEED_TRAP:
                return new BleedTrap(x, y);
            case POISON_TRAP:
                return new PoisonTile(x, y);
            case SWAMP:
                return new SwampTile(x, y);
            case FROST:
                return new FrostTile(x, y);
            case OIL:
                return new OilPuddleTile(x, y);
            default:
                return new FlameTile(x, y);
        }
    }
}

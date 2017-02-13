package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.annotation.UtilityConstructor;
import uq.deco2800.duxcom.sound.SoundRegister;

/**
 * Contains the set of data registers
 *
 * @author liamdm
 */
public class DataRegisterManager {

    // Initialises all of the game's data registers
    private static EntityDataRegister entityDataRegister = new EntityDataRegister();
    private static TileDataRegister tileDataRegister = new TileDataRegister();
    private static SelectionDataRegister selectionDataRegister = new SelectionDataRegister();
    private static EnemyDataRegister enemyDataRegister = new EnemyDataRegister();
    private static HeroDataRegister heroDataRegister = new HeroDataRegister();
    private static AbilityDataRegister abilityDataRegister = new AbilityDataRegister();
    private static SoundRegister soundRegister = new SoundRegister();
    private static LiveTileDataRegister liveTileRegister = new LiveTileDataRegister();

    /**
     * Gets the instance of EntityDataRegister
     * @return Current instance of EntityDataRegister
     */
    public static EntityDataRegister getEntityDataRegister() {
        return entityDataRegister;
    }

    /**
     * Gets the instance of EnemyDataRegister
     * @return Current instance of EnemyDataRegister
     */
    public static EnemyDataRegister getEnemyDataRegister() {
        return enemyDataRegister;
    }
    
    /**
     * Gets the instance of HeroDataRegister
     * @return Current instance of HeroDataRegister
     */
    public static HeroDataRegister getHeroDataRegister() {
        return heroDataRegister;
    }
    
    /**
     * Gets the instance of AbilityDataRegister
     * @return Current instance of AbilityDataRegister
     */
    public static AbilityDataRegister getAbilityDataRegister() {
        return abilityDataRegister;
    }

    /**
     * Gets the instance of TileDataRegister
     * @return Current instance of TileDataRegister
     */
    public static TileDataRegister getTileDataRegister() {
        return tileDataRegister;
    }

    /**
     * Gets the instance of SelectionDataRegister
     * @return Current instance of SelectionDataRegister
     */
    public static SelectionDataRegister getSelectionDataRegister() {
        return selectionDataRegister;
    }

    /**
     * Gets the instance of SoundRegister
     * @return Current instance of SoundRegister
     */
    public static SoundRegister getSoundRegister(){return soundRegister;}

    /**
     * Returns current instance of LiveTileDataRegister
     * @return current LiveTileDataRegister
     */
    public static LiveTileDataRegister getLiveTileRegister() {
        return liveTileRegister;
    }

    @UtilityConstructor
    private DataRegisterManager(){}
}


package uq.deco2800.duxcom.tiles;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.buffs.OnFire;
import uq.deco2800.duxcom.buffs.Wet;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.graphics.AnimationManager;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Tests for the LiveTile Class
 *
 * Created by jay-grant on 20/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LiveTileTest {

    private LiveTile liveTile;

    @Mock
    LiveTileDataClass dataClassMock;

    @Mock
    AbstractBuff buffMock;

    @Mock
    GameManager gameManagerMock;

    @Mock
    GameLoop gameLoopMock;

    @Mock
    MapAssembly mapMock;

    @Mock
    Tile tileMock;

    @Mock
    LiveTile liveTileMock;

    @Mock
    AbstractCharacter characterMock;

    @Test
    public void liveTileFramesLoopTest() {
        when(dataClassMock.getLoop()).thenReturn(true);
        when(dataClassMock.getBaseLoop()).thenReturn(true);
        when(dataClassMock.isDestructible()).thenReturn(true);
        when(dataClassMock.getDestroyedFrame()).thenReturn("destroyed_frame");
        liveTile.setupLiveTile(dataClassMock);

        assertFalse(liveTile.isReverse());
        assertEquals(liveTile.getCurrentFrameTexture(), "frame_1");
        liveTile.animationTick();
        liveTile.animationTick();
        assertFalse(liveTile.isReverse());
        assertEquals(liveTile.getCurrentFrameTexture(), "frame_3");
        liveTile.animationTick();
        assertFalse(liveTile.isReverse());
        assertEquals(liveTile.getCurrentFrameTexture(), "frame_1");

        liveTile.destroy();
        assertTrue(liveTile.isDestroyed());
        assertEquals(liveTile.getCurrentFrameTexture(), "dead_pixel");
    }

    @Test
    public void liveTileFramesNoLoopTest() {
        when(dataClassMock.getLoop()).thenReturn(false);
        when(dataClassMock.getBaseLoop()).thenReturn(false);
        liveTile.setupLiveTile(dataClassMock);

        assertFalse(liveTile.isReverse());
        assertEquals(liveTile.getCurrentFrameTexture(), "frame_1");
        liveTile.animationTick();
        liveTile.animationTick();
        assertFalse(liveTile.isReverse());
        assertEquals(liveTile.getCurrentFrameTexture(), "frame_3");
        liveTile.animationTick();
        assertTrue(liveTile.isReverse());
        assertEquals(liveTile.getCurrentFrameTexture(), "frame_2");
        liveTile.animationTick();
        assertEquals(liveTile.getCurrentFrameTexture(), "frame_1");
        liveTile.animationTick();
        assertFalse(liveTile.isReverse());
    }

    @Test
    public void liveTileBaseFramesLoopTest() {
        when(dataClassMock.getLoop()).thenReturn(true);
        when(dataClassMock.getBaseLoop()).thenReturn(true);
        when(dataClassMock.isDestructible()).thenReturn(true);
        when(dataClassMock.getDestroyedFrame()).thenReturn("destroyed_frame");
        liveTile.setupLiveTile(dataClassMock);

        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_1");
        liveTile.animationTick();
        liveTile.animationTick();
        liveTile.animationTick();
        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_4");
        liveTile.animationTick();
        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_1");

        liveTile.destroy();
        assertTrue(liveTile.isDestroyed());
        assertEquals(liveTile.getCurrentBaseTexture(), "destroyed_frame");
    }

    @Test
    public void liveTileBaseFramesNoLoopTest() {
        when(dataClassMock.getLoop()).thenReturn(false);
        when(dataClassMock.getBaseLoop()).thenReturn(false);
        liveTile.setupLiveTile(dataClassMock);

        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_1");
        liveTile.animationTick();
        liveTile.animationTick();
        liveTile.animationTick();
        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_4");
        liveTile.animationTick();
        assertTrue(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_3");
        liveTile.animationTick();
        liveTile.animationTick();
        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_1");
    }

    @Test
    public void destructibleFrames() {
        when(dataClassMock.getLoop()).thenReturn(true);
        when(dataClassMock.getBaseLoop()).thenReturn(true);
        when(dataClassMock.isDestructible()).thenReturn(true);
        when(dataClassMock.getDestroyedFrame()).thenReturn("destroyed_frame");
        liveTile.setupLiveTile(dataClassMock);

        liveTile.destroy();
        assertTrue(liveTile.isDestroyed());
        assertEquals(liveTile.getCurrentBaseTexture(), "destroyed_frame");
        assertEquals(liveTile.getCurrentFrameTexture(), "dead_pixel");
    }

    @Test
    public void nonDestructibleFrames() {
        when(dataClassMock.getLoop()).thenReturn(true);
        when(dataClassMock.getBaseLoop()).thenReturn(true);
        when(dataClassMock.isDestructible()).thenReturn(false);
        when(dataClassMock.getDestroyedFrame()).thenReturn("destroyed_frame");
        liveTile.setupLiveTile(dataClassMock);

        liveTile.destroy();
        assertFalse(liveTile.isDestroyed());
        assertNotEquals(liveTile.getCurrentBaseTexture(), "destroyed_frame");
        assertNotEquals(liveTile.getCurrentFrameTexture(), "dead_pixel");
    }

    @Test
    public void defaultSyncStringTest() {
        assertEquals(liveTile.getSyncString(), "");
    }

    @Test
    public void turnTickAndTickIntervalTest() {
        when(dataClassMock.getLoop()).thenReturn(true);
        when(dataClassMock.getBaseLoop()).thenReturn(true);
        when(dataClassMock.isDestructible()).thenReturn(true);
        when(dataClassMock.getDestroyedFrame()).thenReturn("destroyed_frame");
        liveTile.setupLiveTile(dataClassMock);

        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_1");
        liveTile.turnTick();
        liveTile.turnTick();
        liveTile.turnTick();
        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_4");
        liveTile.turnTick();
        assertFalse(liveTile.isBaseReverse());
        assertEquals(liveTile.getCurrentBaseTexture(), "frame_base_1");

        liveTile.destroy();
        assertTrue(liveTile.isDestroyed());
        assertEquals(liveTile.getCurrentBaseTexture(), "destroyed_frame");

        assertEquals(liveTile.getTickInterval(), 0L);
    }

    @Test
    public void zeroFramesTest() {
        LiveTile emptyLiveTile = new LiveTile(LiveTileType.LAVA, 1, 1);

        List<String> frames = new ArrayList<>(0);
        List<String> baseFrames = new ArrayList<>(0);
        when(dataClassMock.getFrames()).thenReturn(frames);
        when(dataClassMock.getBaseFrames()).thenReturn(baseFrames);
        when(dataClassMock.getNumberFrames()).thenReturn(1);
        when(dataClassMock.getNumberBaseFrames()).thenReturn(1);
        when(dataClassMock.getTickInterval()).thenReturn(0L);
        emptyLiveTile.setupLiveTile(dataClassMock);

        emptyLiveTile.setNumberFrames(0);
        emptyLiveTile.setNumberBaseFrames(0);

        assertEquals(emptyLiveTile.getNumberFrames(), 0);
        assertEquals(emptyLiveTile.getNumberBaseFrames(), 0);
        assertEquals(emptyLiveTile.getCurrentFrameTexture(), "dead_pixel");
        assertEquals(emptyLiveTile.getCurrentBaseTexture(), "dead_pixel");

        emptyLiveTile.animationTick();
        assertEquals(emptyLiveTile.getCurrentFrameTexture(), "dead_pixel");
        assertEquals(emptyLiveTile.getCurrentBaseTexture(), "dead_pixel");
    }

    @Test
    public void addingEffectsTest() {
        LiveTile emptyLiveTile = new LiveTile(LiveTileType.LAVA, 1, 1);

        assertFalse(emptyLiveTile.hasEffect());
        assertFalse(emptyLiveTile.hasAppliedEffect());
        assertTrue(emptyLiveTile.getEffectLiveTiles().isEmpty());

        when(buffMock.hasAssociatedLiveTile()).thenReturn(true);
        when(buffMock.getAssociatedLiveTileType()).thenReturn(LiveTileType.FLAME);

        emptyLiveTile.addEffect(buffMock);
        assertTrue(emptyLiveTile.hasEffect());
        assertFalse(emptyLiveTile.hasAppliedEffect());
        emptyLiveTile.applyEffect(buffMock);
        assertTrue(emptyLiveTile.hasAppliedEffect());
        assertFalse(emptyLiveTile.getEffectLiveTiles().isEmpty());

        emptyLiveTile.clearEffects();
        assertFalse(emptyLiveTile.hasEffect());
        assertFalse(emptyLiveTile.hasAppliedEffect());
        assertTrue(emptyLiveTile.getEffectLiveTiles().isEmpty());
    }

    @Test
    public void gettingEffectsTest() {
        LiveTile emptyLiveTile = new LiveTile(LiveTileType.LAVA, 1, 1);
        assertTrue(emptyLiveTile.getPassiveEffects().size() == 0);
        assertTrue(emptyLiveTile.getAppliedEffects().size() == 0);

        AbstractBuff onFire = new OnFire(0, 0);
        AbstractBuff wet = new Wet(0, 0);

        emptyLiveTile.addEffect(onFire);
        emptyLiveTile.applyEffect(wet);

        assertTrue(emptyLiveTile.getPassiveEffects().size() == 1);
        assertTrue(emptyLiveTile.getAppliedEffects().size() == 1);
        assertTrue(emptyLiveTile.getPassiveEffects().get(0) instanceof OnFire);
        assertTrue(emptyLiveTile.getAppliedEffects().get(0) instanceof Wet);

        assertTrue(emptyLiveTile.getAllEffects().size() == 2);
        assertTrue(emptyLiveTile.getAllEffects().contains(onFire));
        assertTrue(emptyLiveTile.getAllEffects().contains(wet));
    }

    @Test
    public void elevationTest() {
        LiveTile emptyLiveTile = new LiveTile(LiveTileType.LAVA, 1, 1);

        assertEquals((int) emptyLiveTile.getElevation(), 0);
        emptyLiveTile.setElevation(1.0);
        assertEquals((int) emptyLiveTile.getElevation(), 1);
    }

    @Test
    public void liveTileSetXTest() {
        assertEquals(liveTile.getX(), 1);
        liveTile.setX(5);
        assertEquals(liveTile.getX(), 5);
        liveTile.setX(-1);
        assertEquals(liveTile.getX(), 5);
    }

    @Test
    public void liveTileSetYTest() {
        assertEquals(liveTile.getY(), 1);
        liveTile.setY(5);
        assertEquals(liveTile.getY(), 5);
        liveTile.setY(-1);
        assertEquals(liveTile.getY(), 5);
    }

    @Test
    public void liveTilePassiveEffectsTest() {
        assertTrue(liveTile.getPassiveEffects().isEmpty());
        liveTile.addEffect(buffMock);
        assertFalse(liveTile.getPassiveEffects().isEmpty());
        assertEquals(liveTile.getPassiveEffects().get(0), buffMock);
    }

    @Test
    public void liveTileCurrentEffectsTest() {
        assertTrue(liveTile.getAppliedEffects().isEmpty());
        liveTile.applyEffect(buffMock);
        assertFalse(liveTile.getAppliedEffects().isEmpty());
        assertEquals(liveTile.getAppliedEffects().get(0), buffMock);
    }

    @Test
    public void liveTileNumberFramesTest() {
        when(dataClassMock.getLoop()).thenReturn(true);
        when(dataClassMock.getBaseLoop()).thenReturn(true);
        liveTile.setupLiveTile(dataClassMock);
        assertEquals(liveTile.getNumberFrames(), 3);
    }

    @Test
    public void liveTileNumberBaseFramesTest() {
        when(dataClassMock.getLoop()).thenReturn(true);
        when(dataClassMock.getBaseLoop()).thenReturn(true);
        liveTile.setupLiveTile(dataClassMock);
        assertEquals(liveTile.getNumberBaseFrames(), 4);
    }

    @Test
    public void liveTileTypeTest() {
        liveTile.setLiveTileType(LiveTileType.LAVA);
        assertEquals(liveTile.getLiveTileType(), LiveTileType.LAVA);
    }

    @Test
    public void getNewLiveTileTest() {
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.LAVA, 0, 0)
                instanceof LavaTile);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.LONG_GRASS, 0, 0)
                instanceof LongGrassTile);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.PUDDLE, 0, 0)
                instanceof PuddleTile);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.CAVE_TWO, 0, 0)
                instanceof CaveTwo);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.CAVE_THREE, 0, 0)
                instanceof CaveThree);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.CAVE_FOUR, 0, 0)
                instanceof CaveFour);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.FLAME, 0, 0)
                instanceof FlameTile);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.BLEED_TRAP, 0, 0)
                instanceof BleedTrap);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.POISON_TRAP, 0, 0)
                instanceof PoisonTile);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.SWAMP, 0, 0)
                instanceof SwampTile);
        assertTrue(LiveTile.getNewLiveTile(LiveTileType.FROST, 0, 0)
                instanceof FrostTile);
    }

    /**
     * Individual LiveTile Class Tests
     */
    @Test
    public void bleedTrapTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.BLEED_TRAP, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.BLEED_TRAP);
        assertEquals("BLEED", liveTile.getSyncString());

        BleedTrap bleedTile = new BleedTrap(0, 0);
        assertFalse(bleedTile.isDestroyed());
        bleedTile.changeHealth(0.0, DamageType.NORMAL);
        assertTrue(bleedTile.isDestroyed());
    }

    @Test
    public void caveFourTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.CAVE_FOUR, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.CAVE_FOUR);
        assertEquals("CAVE_FOUR", liveTile.getSyncString());
    }

    @Test
    public void caveThreeTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.CAVE_THREE, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.CAVE_THREE);
        assertEquals("CAVE_THREE", liveTile.getSyncString());
    }

    @Test
    public void caveTwoTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.CAVE_TWO, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.CAVE_TWO);
        assertEquals("CAVE_TWO", liveTile.getSyncString());
    }

    @Test
    public void flameTileTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.FLAME, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.FLAME);
        assertEquals("FLAME", liveTile.getSyncString());

        FlameTile flameTile = new FlameTile(1, 1);
        assertFalse(flameTile.isDestroyed());

        flameTile.changeHealth(0.0, DamageType.FIRE);
        assertFalse(flameTile.isDestroyed());

        flameTile.changeHealth(0.0, DamageType.ELECTRIC);
        assertFalse(flameTile.isDestroyed());

        flameTile.changeHealth(0.0, DamageType.EXPLOSIVE);
        assertFalse(flameTile.isDestroyed());

        flameTile.changeHealth(0.0, DamageType.NORMAL);
        assertTrue(flameTile.isDestroyed());

        flameTile.changeHealth(0.0, DamageType.WATER);
        assertTrue(flameTile.isDestroyed());

    }

    @Test
    public void frostTileTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.FROST, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.FROST);
        assertEquals("FROST", liveTile.getSyncString());
    }
    @Test
    public void lavaTileTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.LAVA, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.LAVA);
        assertEquals("LAVA", liveTile.getSyncString());
    }

    @Test
    public void longGrassTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.LONG_GRASS, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.LONG_GRASS);
        assertEquals("LONG_GRASS", liveTile.getSyncString());

        LongGrassTile longGrassTile = new LongGrassTile(1, 1);
        assertFalse(longGrassTile.hasAppliedEffect());
        assertFalse(longGrassTile.isDestroyed());

        longGrassTile.changeHealth(0.0, DamageType.NORMAL);
        assertTrue(longGrassTile.isDestroyed());
        assertFalse(longGrassTile.hasAppliedEffect());

        longGrassTile = new LongGrassTile(1, 1);
        assertFalse(longGrassTile.hasAppliedEffect());

        longGrassTile.changeHealth(0.0, DamageType.FIRE);
        assertTrue(longGrassTile.isDestroyed());
        assertTrue(longGrassTile.hasAppliedEffect());
    }
    @Test
    public void poisonTileTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.POISON_TRAP, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.POISON_TRAP);
        assertEquals("POISON", liveTile.getSyncString());
    }

    @Test
    public void puddleTileTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.PUDDLE, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.PUDDLE);
        assertEquals("PUDDLE", liveTile.getSyncString());


        List<Entity> characters = new ArrayList<>();
        characters.add(characterMock);
        List<Coordinate> surrounds = new ArrayList<>(2);
        surrounds.add(new Coordinate(0, 0));
        surrounds.add(new Coordinate(3, 3));
        when(tileMock.getEntities()).thenReturn(characters);
        when(mapMock.getSurroundingLiveTileCoordinates(LiveTileType.PUDDLE, 1, 1, 5)).thenReturn(surrounds);
        when(mapMock.getTile(0, 0)).thenReturn(tileMock);
        when(mapMock.getTile(3, 3)).thenReturn(tileMock);


        PuddleTile puddleTile = new PuddleTile(1, 1);
        puddleTile.changeHealth(0.0, DamageType.FIRE);
        verify(gameManagerMock, times(0)).getMap();
        verify(mapMock, times(0)).getSurroundingLiveTileCoordinates(LiveTileType.PUDDLE, 1, 1, 5);
        verify(mapMock, times(0)).getTile(0, 0);
        verify(mapMock, times(0)).getTile(3, 3);
        verify(characterMock, times(0)).changeHealth(0.0);

        when(tileMock.hasCharacter()).thenReturn(false);

        puddleTile.changeHealth(0.0, DamageType.ELECTRIC);
        verify(gameManagerMock, times(3)).getMap();
        verify(mapMock, times(1)).getSurroundingLiveTileCoordinates(LiveTileType.PUDDLE, 1, 1, 5);
        verify(mapMock, times(1)).getTile(0, 0);
        verify(mapMock, times(1)).getTile(3, 3);
        verify(characterMock, times(0)).changeHealth(0.0);
        assertFalse(puddleTile.isDestroyed());

        when(tileMock.hasCharacter()).thenReturn(true);

        puddleTile.changeHealth(0.0, DamageType.ELECTRIC);
        verify(gameManagerMock, times(8)).getMap();
        verify(mapMock, times(2)).getSurroundingLiveTileCoordinates(LiveTileType.PUDDLE, 1, 1, 5);
        verify(mapMock, times(3)).getTile(0, 0);
        verify(mapMock, times(3)).getTile(3, 3);
        verify(characterMock, times(2)).changeHealth(0.0);
        assertFalse(puddleTile.isDestroyed());
    }

    @Test
    public void swampTileTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.SWAMP, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.SWAMP);
        assertEquals("SWAMP", liveTile.getSyncString());
    }

    @Test
    public void oilTileTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.OIL, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.OIL);
        assertEquals("OIL", liveTile.getSyncString());

        OilPuddleTile oil = new OilPuddleTile(1, 1);
        OilPuddleTile oilOne = new OilPuddleTile(0, 0);
        OilPuddleTile oilTwo = new OilPuddleTile(3, 3);
        List<Coordinate> surrounds = new ArrayList<>(2);
        surrounds.add(new Coordinate(0, 0));
        surrounds.add(new Coordinate(1, 1));
        surrounds.add(new Coordinate(3, 3));
        when(mapMock.getSurroundingLiveTileCoordinates(LiveTileType.OIL, 1, 1, 7)).thenReturn(surrounds);
        when(mapMock.getLiveTile(0, 0)).thenReturn(oilOne);
        when(mapMock.getLiveTile(3, 3)).thenReturn(oilTwo);
        when(mapMock.getLiveTile(1, 1)).thenReturn(oil);

        assertFalse(oil.burning);
        oil.changeHealth(0.0, DamageType.NORMAL);
        assertFalse(oil.burning);
        assertTrue(oil.getAppliedEffects().isEmpty());
        assertFalse(oilOne.burning);
        assertTrue(oilOne.getAppliedEffects().isEmpty());
        assertFalse(oilTwo.burning);
        assertTrue(oilTwo.getAppliedEffects().isEmpty());


        oil.changeHealth(0.0, DamageType.FIRE);
        assertTrue(oil.burning);
        assertFalse(oil.getAppliedEffects().isEmpty());
        assertTrue(oil.getAppliedEffects().get(0) instanceof OnFire);
        assertTrue(oilOne.burning);
        assertFalse(oilOne.getAppliedEffects().isEmpty());
        assertTrue(oilOne.getAppliedEffects().get(0) instanceof OnFire);
        assertTrue(oilTwo.burning);
        assertFalse(oilTwo.getAppliedEffects().isEmpty());
        assertTrue(oilTwo.getAppliedEffects().get(0) instanceof OnFire);
    }

    @Test
    public void defaultFactoryTest() {
        liveTile = LiveTile.getNewLiveTile(LiveTileType.FACTORY_DEFAULT_TESTER, 1, 1);
        assertTrue(liveTile.getLiveTileType() == LiveTileType.FLAME);
        assertEquals("FLAME", liveTile.getSyncString());
    }

    @Before
    public void beforeTesting() {
        AtomicBoolean thisTestIsBs = new AtomicBoolean(false);
        GameLoop gameLoop = new GameLoop(0, thisTestIsBs, gameManagerMock);
        when(gameManagerMock.getMap()).thenReturn(mapMock);

        liveTile = new LiveTile(LiveTileType.LAVA, 1, 1);
        List<String> frames = new ArrayList<>(3);
        frames.add(0, "frame_1");
        frames.add(1, "frame_2");
        frames.add(2, "frame_3");
        List<String> baseFrames = new ArrayList<>(3);
        baseFrames.add(0, "frame_base_1");
        baseFrames.add(1, "frame_base_2");
        baseFrames.add(2, "frame_base_3");
        baseFrames.add(3, "frame_base_4");
        when(dataClassMock.getFrames()).thenReturn(frames);
        when(dataClassMock.getBaseFrames()).thenReturn(baseFrames);
        when(dataClassMock.getNumberFrames()).thenReturn(3);
        when(dataClassMock.getNumberBaseFrames()).thenReturn(4);
        when(dataClassMock.getTickInterval()).thenReturn(0L);
    }
}



package uq.deco2800.duxcom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.ShortRangeTestAbility;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.controllers.UserInterfaceController;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.BetaTester;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.maps.EmptyMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.time.DayNightClock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the GameManager class
 *
 * @author Alex McLean
 */
@RunWith(MockitoJUnitRunner.class)
public class GameManagerTest {

    private GameManager gameManager;

    @Mock
    private DuxComController duxComControllerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private AbstractHero abstractHeroMock;
    @Mock
    private BetaTester betaTesterMock;
    @Mock
    private UserInterfaceController uiControllerMock;
    @Mock
    private Tile tileMock;
    @Mock
    private Entity entityMock;
    @Mock
    private List<Entity> entities;

    @Test
    public void testBaseMethods() {
        gameManager = new GameManager();
        gameManager.setController(duxComControllerMock);
        gameManager.setMap(mapAssemblyMock);
        when(mapAssemblyMock.getTile(gameManager.getSelectionX(), gameManager.getSelectionY())).thenReturn(tileMock);
        when(tileMock.getEntities()).thenReturn(entities);
        when(entities.isEmpty()).thenReturn(true);
        when(tileMock.hasLiveTile()).thenReturn(false);

        Double prevXOffset = gameManager.getxOffset();
        Double prevYOffset = gameManager.getyOffset();
        gameManager.setReleased();
        assertTrue(Math.abs(prevXOffset - gameManager.getxOffset()) < 0.001d);
        assertTrue(Math.abs(prevYOffset - gameManager.getyOffset()) < 0.001d);
        gameManager.setDragged(-1, -1);
        assertTrue(Math.abs(prevXOffset - gameManager.getxOffset()) < 0.001d);
        assertTrue(Math.abs(prevYOffset - gameManager.getyOffset()) < 0.001d);
        gameManager.setDragged(10, 20);
        assertTrue(Math.abs(prevXOffset + 11 - gameManager.getxOffset()) < 0.001d);
        assertTrue(Math.abs(prevYOffset + 21 - gameManager.getyOffset()) < 0.001d);
        gameManager.setRightPressed(10, 10);
        gameManager.setDragged(0, 0);
        assertTrue(Math.abs(prevXOffset - 10 - gameManager.getxOffset()) < 0.001d);
        assertTrue(Math.abs(prevYOffset - 10 - gameManager.getyOffset()) < 0.001d);

        gameManager.zoomIn();
        assertTrue(Math.abs(0.33 - gameManager.getScale()) < 0.001d);
        gameManager.zoomOut();
        assertTrue(Math.abs(0.30 - gameManager.getScale()) < 0.001d);

        assertEquals(DayNightClock.class, gameManager.getDayNightClock().getClass());

        assertFalse(gameManager.isPlayerTurn());
        gameManager.setPlayerTurn(true);
        assertTrue(gameManager.isPlayerTurn());
        gameManager.setPlayerTurn(false);

        gameManager.setSelection(10, 20);
        assertEquals(10, gameManager.getSelectionX());
        assertEquals(20, gameManager.getSelectionY());

        when(duxComControllerMock.getUIController()).thenReturn(uiControllerMock);
        gameManager.writeToControllerGameLog("test");
        verify(duxComControllerMock.getUIController(), times(1)).writeToLogBox(any());
        
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(abstractHeroMock);
        List<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new ShortRangeTestAbility());
        abilities.add(new ShortRangeTestAbility());
        when(abstractHeroMock.getAbilities()).thenReturn(abilities);

        when(mapAssemblyMock.getTile(gameManager.getSelectionX(), gameManager.getSelectionY())).thenReturn(tileMock);
        when(tileMock.getEntities()).thenReturn(entities);
        when(entities.isEmpty()).thenReturn(true);
        when(tileMock.hasLiveTile()).thenReturn(false);
        
        gameManager.setAbilitySelected(AbilitySelected.ABILITY1);
        gameManager.useSelectedAbility();
        assertTrue(gameManager.isGameChanged());
        gameManager.setAbilitySelected(AbilitySelected.ABILITY2);
        gameManager.useSelectedAbility();
        assertTrue(gameManager.isGameChanged());
    }

    @Test
    public void testWithMockMap() {
        gameManager = new GameManager();
        gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(new EmptyMap("test")));
        gameManager.setController(duxComControllerMock);
        gameManager.setMap(mapAssemblyMock);
        when(mapAssemblyMock.getTile(gameManager.getSelectionX(), gameManager.getSelectionY())).thenReturn(tileMock);
        when(tileMock.getEntities()).thenReturn(entities);
        when(entities.isEmpty()).thenReturn(true);
        when(tileMock.hasLiveTile()).thenReturn(false);
        when(duxComControllerMock.getUIController()).thenReturn(uiControllerMock);

        assertEquals(mapAssemblyMock, gameManager.getMap());

        when(mapAssemblyMock.canSelectPoint(10, 11)).thenReturn(true);
        gameManager.setMouseLocation(10, 11);
        assertEquals(10, gameManager.getHoverX());
        assertEquals(11, gameManager.getHoverY());
        gameManager.setMouseLocation(10000, 11000);
        assertEquals(10, gameManager.getHoverX());
        assertEquals(11, gameManager.getHoverY());
        
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(abstractHeroMock);
        List<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new ShortRangeTestAbility());
        abilities.add(new ShortRangeTestAbility());
        when(abstractHeroMock.getAbilities()).thenReturn(abilities);

        gameManager.moveToSelected();
        gameManager.abilityOne();
        verify(mapAssemblyMock, never()).moveEntity(any(), anyInt(), anyInt());
        verify(mapAssemblyMock, times(2)).getCurrentTurnHero();
        verify(mapAssemblyMock, never()).getMovableEntity(anyInt(), anyInt());

        gameManager.setSelection(10, 10);

        gameManager.moveToSelected();
        //gameManager.abilityTwo();
        verify(mapAssemblyMock, times(1)).moveEntity(any(), anyInt(), anyInt());
        verify(mapAssemblyMock, times(3)).getCurrentTurnHero();
        //verify(mapAssemblyMock, times(1)).getEntity(anyInt(), anyInt());

        when(mapAssemblyMock.getMovableEntity(anyInt(), anyInt())).thenReturn(new EnemyArcher(10, 10));
        when(abstractHeroMock.useAbility(anyInt(), anyInt(), anyInt())).thenReturn(true);
        when(mapAssemblyMock.getTile(gameManager.getSelectionX(), gameManager.getSelectionY())).thenReturn(tileMock);
        when(tileMock.getEntities()).thenReturn(entities);
        when(entities.isEmpty()).thenReturn(true);
        when(tileMock.hasLiveTile()).thenReturn(false);
        gameManager.abilityTwo();

        when(mapAssemblyMock.enemyPerformingMove()).thenReturn(true);
        when(gameManager.getHeroManager()).thenReturn(new HeroManager());
        gameManager.nextTurn();
        verify(mapAssemblyMock, never()).turnTick();
        when(mapAssemblyMock.enemyPerformingMove()).thenReturn(false);
        when(gameManager.getHeroManager()).thenReturn(new HeroManager());
        gameManager.nextTurn();
        verify(mapAssemblyMock, times(1)).turnTick();
        gameManager.setPlayerTurn(false);
        when(gameManager.getHeroManager()).thenReturn(new HeroManager());
        gameManager.nextTurn();
        verify(mapAssemblyMock, times(2)).turnTick();

        gameManager.setViewPortHeight(1);
        gameManager.setViewPortWidth(1);
        when(mapAssemblyMock.getWidth()).thenReturn(20);
        when(mapAssemblyMock.getHeight()).thenReturn(20);

        gameManager.moveViewTo(10, 10);
        while (gameManager.getPanStatus()) {
            gameManager.panView();
        }
        assertTrue(Math.abs(-521 - gameManager.getxOffset()) < 0.001d);
        assertTrue(Math.abs(-299 - gameManager.getyOffset()) < 0.001d);

        gameManager.moveViewTo(5, 5);
        while (gameManager.doPan) {
            gameManager.panView();
        }
        when(abstractHeroMock.getX()).thenReturn(10);
        when(abstractHeroMock.getY()).thenReturn(10);
        gameManager.centerMapOnPoint(10, 10);
        while (gameManager.doPan) {
            gameManager.panView();
        }
        assertTrue(Math.abs(-521 - gameManager.getxOffset()) < 0.001d);
        assertTrue(Math.abs(-299 - gameManager.getyOffset()) < 0.001d);

        when(mapAssemblyMock.canSelectPoint(anyInt(), anyInt())).thenReturn(false);
        gameManager.setSelection(0, 0);
        gameManager.setLeftPressed(2000, 2000);

        when(mapAssemblyMock.canSelectPoint(anyInt(), anyInt())).thenReturn(true);
        when(mapAssemblyMock.getTile(anyInt(), anyInt())).thenReturn(tileMock);
        when(tileMock.isOccupied()).thenReturn(false);
        gameManager.setLeftPressed(10, 10);
        assertEquals(10, gameManager.getSelectionX());
        assertEquals(10, gameManager.getSelectionY());
        when(mapAssemblyMock.getMovableEntity(anyInt(), anyInt())).thenReturn(null);
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(betaTesterMock);
        gameManager.setLeftPressed(10, 10);
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(abstractHeroMock);
        gameManager.setLeftPressed(10, 10);
        verify(mapAssemblyMock, times(4)).turnTick();

        gameManager.setTemporaryVisiblePoint(1, 1, 1, 2);
        verify(mapAssemblyMock, times(1)).setPointRadiusVisible(1, 1, 1);
        when(gameManager.getHeroManager()).thenReturn(new HeroManager());
        gameManager.nextTurn();
        when(gameManager.getHeroManager()).thenReturn(new HeroManager());
        gameManager.nextTurn();
        verify(mapAssemblyMock, times(1)).setPointHidden(1, 1);
    }
    
    @Test
    public void boolTests() {
    	GameManager gameManager = new GameManager();
    	gameManager.setAbilitySelectedChanged(false);
    	assertFalse(gameManager.isAbilitySelectedChanged());
    	gameManager.setMovementChanged(false);
    	assertFalse(gameManager.isMovementChanged());
    	gameManager.setAbilitySelected(AbilitySelected.MOVE);
    	assertEquals(AbilitySelected.MOVE, gameManager.getAbilitySelected());

    	// mini map toggle
    	boolean miniMapVisible = gameManager.isMiniMapVisible();
    	gameManager.toggleMiniMap();
    	assertEquals(gameManager.isMiniMapVisible(), !miniMapVisible);

    	// chat toggle
    	boolean chatVisible = gameManager.isChatVisible();
    	gameManager.toggleChat();
    	assertEquals(gameManager.isChatVisible(), !chatVisible);

    	// mini health bars toggle
    	boolean miniHealthBarsVisible = gameManager.isMiniHealthBarVisible();
    	gameManager.toggleMiniHealthBars();
    	assertEquals(gameManager.isMiniHealthBarVisible(), !miniHealthBarsVisible);

        gameManager.setGameChanged(false);
        gameManager.setVisionChanged(false);
        gameManager.setBackgroundChanged(false);
        gameManager.setSelectionChanged(false);
        gameManager.setMovementChanged(false);
        gameManager.fullRenderRefresh();
        assertTrue(gameManager.isGameChanged());
        assertTrue(gameManager.isVisionChanged());
        assertTrue(gameManager.isSelectionChanged());
        assertTrue(gameManager.isMovementChanged());
        assertTrue(gameManager.isBackgroundChanged());
    }
}

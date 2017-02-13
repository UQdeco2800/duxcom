package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.abilities.Slash;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Test AbilityRangeGraphicsHandler based on MovementRangeGraphicsHandlerTest
 * by Alex McLean.
 *
 * @author jake-stevenson
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class AbilityRangeGraphicsHandlerTest extends ApplicationTest {

    private AbilityRangeGraphicsHandler abilityRangeGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private AbstractHero abstractHeroMock;
    @Mock
    private AbstractEnemy abstractEnemyMock;
    @Mock
    private AbstractAbility abilityMock;
    @Mock
    private Slash slashMock;
    @Mock
    private Heal healMock;

    @Test
    public void testUpdateGraphics() {
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(true);

        when(gameManagerMock.isPlayerTurn()).thenReturn(true);
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getCurrentTurnEntity(true)).thenReturn(abstractEnemyMock);
        abilityRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, never()).getScale();

        when(gameManagerMock.isPlayerTurn()).thenReturn(true);
        when(mapAssemblyMock.getCurrentTurnEntity(true)).thenReturn(abstractHeroMock);
        when(gameManagerMock.getAbilitySelected()).thenReturn(AbilitySelected.ABILITY1);
        abilityRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(2)).getAbilitySelected();
        
        when(gameManagerMock.isPlayerTurn()).thenReturn(true);
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(abstractHeroMock);
        when(gameManagerMock.getAbilitySelected()).thenReturn(AbilitySelected.MOVE);
        abilityRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, never()).setAbilitySelectedChanged(false);

        when(abstractHeroMock.getWeaponAbility()).thenReturn(abilityMock);
    	when(gameManagerMock.getAbilitySelected()).thenReturn(AbilitySelected.WEAPON);
    	abilityRangeGraphicsHandler.updateGraphics(graphicsContext);
    	verify(abstractHeroMock, never()).getX();
    	verify(abilityMock, never()).canUseOnFoe();

    	// Test: All 4 edges
    	when(gameManagerMock.getAbilitySelected()).thenReturn(AbilitySelected.ABILITY1);
    	when(abstractHeroMock.getSelectedAbility(gameManagerMock.getAbilitySelected())).thenReturn(slashMock);
    	when(slashMock.getRange()).thenReturn(0);
    	when(slashMock.inRange(0, 0, 0, 0)).thenReturn(true);
    	when(mapAssemblyMock.getWidth()).thenReturn(1);
    	when(mapAssemblyMock.getHeight()).thenReturn(1);
    	when(abstractHeroMock.getX()).thenReturn(0);
    	when(abstractHeroMock.getY()).thenReturn(0);
    	abilityRangeGraphicsHandler.updateGraphics(graphicsContext);
    	verify(abstractHeroMock, times(5)).getX();
    	verify(gameManagerMock, times(4)).getxOffset();
    	verify(slashMock, times(1)).canUseOnFriend();

    	// Test: Friendly ability
    	when(gameManagerMock.getAbilitySelected()).thenReturn(AbilitySelected.UTILITY);
    	when(abstractHeroMock.getSelectedAbility(gameManagerMock.getAbilitySelected())).thenReturn(healMock);
    	when(healMock.inRange(0, 0, 0, 0)).thenReturn(true);
    	abilityRangeGraphicsHandler.updateGraphics(graphicsContext);
    	verify(abstractHeroMock, times(10)).getX();
    	verify(gameManagerMock, times(8)).getxOffset();
    	verify(healMock, times(1)).canUseOnFriend();
    	
    	// Test: no edges touching sides
    	when(gameManagerMock.getAbilitySelected()).thenReturn(AbilitySelected.ABILITY1);
    	when(abstractHeroMock.getSelectedAbility(gameManagerMock.getAbilitySelected())).thenReturn(slashMock);
    	when(slashMock.inRange(1, 1, 1, 1)).thenReturn(true);
    	when(mapAssemblyMock.getWidth()).thenReturn(3);
    	when(mapAssemblyMock.getHeight()).thenReturn(3);
    	when(abstractHeroMock.getX()).thenReturn(1);
    	when(abstractHeroMock.getY()).thenReturn(1);
    	abilityRangeGraphicsHandler.updateGraphics(graphicsContext);
    	// Called 13 times (9x1 for each tile on map + 4 for each edge of range + 10 from previous tests
    	verify(abstractHeroMock, times(23)).getX();
    	verify(gameManagerMock, times(12)).getxOffset();
    	verify(slashMock, times(2)).canUseOnFriend();
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(false);
        assertFalse(abilityRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(false);
        assertTrue(abilityRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(true);
        assertTrue(abilityRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(true);
        assertTrue(abilityRangeGraphicsHandler.needsUpdating());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(canvas);
        Scene scene = new Scene(anchorPane, 1280, 720);
        stage.setScene(scene);
        stage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        new GameLoop(10, new AtomicBoolean(false), gameManagerMock);
        abilityRangeGraphicsHandler = new AbilityRangeGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}
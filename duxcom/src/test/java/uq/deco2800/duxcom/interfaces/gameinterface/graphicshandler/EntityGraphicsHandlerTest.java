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
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.WoodStack;
import uq.deco2800.duxcom.entities.dynamics.DynamicEntity;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.scenery.AbstractScenery;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Tests the EntityGraphicsHandler
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class EntityGraphicsHandlerTest extends ApplicationTest {

    private EntityGraphicsHandler entityGraphicsHandler;
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
    private DynamicEntity dynamicEntityMock;
    @Mock
    private AbstractScenery abstractSceneryMock;

    @Test
    public void testUpdateGraphics() {

        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(gameManagerMock.getxOffset()).thenReturn(10.0);
        when(gameManagerMock.getyOffset()).thenReturn(10.0);
        when(gameManagerMock.onScreen(anyInt(), anyInt())).thenReturn(true);
        when(gameManagerMock.onScreen(2, 2)).thenReturn(false);
        when(mapAssemblyMock.getWidth()).thenReturn(20);
        when(abstractHeroMock.isHidden()).thenReturn(false);
        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
        when(abstractHeroMock.getYLength()).thenReturn(1);
        when(abstractHeroMock.getRatioHealthRemaining()).thenReturn(0.1);
        when(abstractHeroMock.getEntityType()).thenReturn(EntityType.ARCHER);
        when(abstractHeroMock.getImageName()).thenReturn("real_qut");
        when(abstractEnemyMock.isHidden()).thenReturn(true);
        when(abstractEnemyMock.getX()).thenReturn(1);
        when(abstractEnemyMock.getY()).thenReturn(1);
        when(abstractEnemyMock.getYLength()).thenReturn(1);
        when(abstractEnemyMock.getEntityType()).thenReturn(EntityType.ENEMY_KNIGHT);
        when(abstractEnemyMock.getImageName()).thenReturn("real_qut");
        when(dynamicEntityMock.isHidden()).thenReturn(true);
        when(dynamicEntityMock.getX()).thenReturn(2);
        when(dynamicEntityMock.getY()).thenReturn(2);
        when(dynamicEntityMock.getYLength()).thenReturn(1);
        when(dynamicEntityMock.getEntityType()).thenReturn(EntityType.CAVALIER);
        when(dynamicEntityMock.getImageName()).thenReturn("real_qut");
        when(abstractSceneryMock.isHidden()).thenReturn(true);
        when(abstractSceneryMock.getX()).thenReturn(3);
        when(abstractSceneryMock.getY()).thenReturn(3);
        when(abstractSceneryMock.getYLength()).thenReturn(1);
        when(abstractSceneryMock.getEntityType()).thenReturn(EntityType.CAVALIER);
        when(abstractSceneryMock.getImageName()).thenReturn("real_qut");

        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(abstractHeroMock);
        entities.add(abstractEnemyMock);
        entities.add(dynamicEntityMock);
        entities.add(abstractSceneryMock);
        entities.add(new WoodStack(4,4));
        when(mapAssemblyMock.getEntities()).thenReturn(entities);

        entityGraphicsHandler.updateGraphics(graphicsContext);

        verify(gameManagerMock, atLeastOnce()).getMap();

    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getEntities()).thenReturn(new ArrayList<>());
        assertFalse(entityGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        assertTrue(entityGraphicsHandler.needsUpdating());

		ArrayList<Entity> entities = new ArrayList<>();
        entities.add(abstractHeroMock);
        entities.add(abstractEnemyMock);
        entities.add(dynamicEntityMock);
        when(mapAssemblyMock.getEntities()).thenReturn(entities);
        when(gameManagerMock.onScreen(anyInt(), anyInt())).thenReturn(true);
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        assertTrue(entityGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        assertTrue(entityGraphicsHandler.needsUpdating());
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
        entityGraphicsHandler = new EntityGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}
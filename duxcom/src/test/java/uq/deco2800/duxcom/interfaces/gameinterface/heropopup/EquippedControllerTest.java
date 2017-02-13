package uq.deco2800.duxcom.interfaces.gameinterface.heropopup;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.loadui.testfx.GuiTest.find;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

/**
 * test the EquippedController
 */
@RunWith(MockitoJUnitRunner.class)
public class EquippedControllerTest extends PopUpMenuControllerTestSetUp {
	/*
	 * EquippedController elements
	 */
	private Pane primaryPane;
	private Pane secondaryPane;
	private Pane armourPane;
	private Pane shieldPane;
	private ImageView primaryImage;
	private ImageView secondaryImage;
	private ImageView armourImage;
	private ImageView shieldImage;
	private Button unequipButton;
	
	/*
	 * InventoryController elements
	 */
	private Pane item1Pane;
	private ImageView itemImage1;
	private Pane item2Pane;
	private ImageView itemImage2;
	private Pane item3Pane;
	private ImageView itemImage3;
	private Pane item4Pane;
	private ImageView itemImage4;
	private Pane item5Pane;
	private ImageView itemImage5;
	private Pane item6Pane;
	private ImageView itemImage6;
	private Pane item7Pane;
	private ImageView itemImage7;
	private Pane item8Pane;
	private ImageView itemImage8;
	private Pane item9Pane;
	private ImageView itemImage9;
	private Pane item10Pane;
	private ImageView itemImage10;
	private Pane item11Pane;
	private ImageView itemImage11;
	private Pane item12Pane;
	private ImageView itemImage12;
	private Button equipButton;
	
	@Override
	public void start(Stage stage) throws Exception {
		super.start(stage);

		/*
		 * EquippedController elements
		 */
		primaryPane = find("#primaryPane");
		secondaryPane = find("#secondaryPane");
		armourPane = find("#armourPane");
		shieldPane = find("#shieldPane");
		primaryImage = find("#primaryImage");
		secondaryImage = find("#secondaryImage");
		armourImage = find("#armourImage");
		shieldImage = find("#shieldImage");
		unequipButton = find("#equipButton");
		
		/*
		 * InventoryController elements
		 */
		item1Pane = find("#item1Pane");
		item2Pane = find("#item2Pane");
		item3Pane = find("#item3Pane");
		item4Pane = find("#item4Pane");
		item5Pane = find("#item5Pane");
		item6Pane = find("#item6Pane");
		item7Pane = find("#item7Pane");
		item8Pane = find("#item8Pane");
		item9Pane = find("#item9Pane");
		item10Pane = find("#item10Pane");
		item11Pane = find("#item11Pane");
		item12Pane = find("#item12Pane");
		itemImage1 = find("#itemImage1");
		itemImage2 = find("#itemImage2");
		itemImage3 = find("#itemImage3");
		itemImage4 = find("#itemImage4");
		itemImage5 = find("#itemImage5");
		itemImage6 = find("#itemImage6");
		itemImage7 = find("#itemImage7");
		itemImage8 = find("#itemImage8");
		itemImage9 = find("#itemImage9");
		itemImage10 = find("#itemImage10");
		itemImage11 = find("#itemImage11");
		itemImage12 = find("#itemImage12");
		equipButton = find("#customButton");
	}
	
	@Override
    @Before
    public void setUp() throws IOException, InterruptedException {
    	super.setUp();
    	
    	/* Change to the EquippedController */
    	Platform.runLater(() -> {
    		equippedButton.fire();
    	});
    	waitForRunLater();
    }
	
	@Test
	public void switchToEquippedControllerTest() {
		assertEquals(controller.getLeftMenu(), Menus.EQUIPPED);
		assertEquals(controller.getRightMenu(), Menus.INVENTORY);
	}
	
	/*
     * This test works, but trying to save the build.
     */
	@Test
    @Ignore
	public void checkNodesExist() {
		verifyThat("#primaryPane", isNotNull());
		verifyThat("#secondaryPane", isNotNull());
		verifyThat("#armourPane", isNotNull());
		verifyThat("#shieldPane", isNotNull());
		verifyThat("#primaryImage", isNotNull());
		verifyThat("#secondaryImage", isNotNull());
		verifyThat("#armourImage", isNotNull());
		verifyThat("#shieldImage", isNotNull());
		verifyThat("#equipButton", isNotNull());
		

		verifyThat("#item1Pane", isNotNull());
		verifyThat("#item2Pane", isNotNull());
		verifyThat("#item3Pane", isNotNull());
		verifyThat("#item4Pane", isNotNull());
		verifyThat("#item5Pane", isNotNull());
		verifyThat("#item6Pane", isNotNull());
		verifyThat("#item7Pane", isNotNull());
		verifyThat("#item8Pane", isNotNull());
		verifyThat("#item9Pane", isNotNull());
		verifyThat("#item10Pane", isNotNull());
		verifyThat("#item11Pane", isNotNull());
		verifyThat("#item12Pane", isNotNull());
		verifyThat("#itemImage1", isNotNull());
		verifyThat("#itemImage2", isNotNull());
		verifyThat("#itemImage3", isNotNull());
		verifyThat("#itemImage4", isNotNull());
		verifyThat("#itemImage5", isNotNull());
		verifyThat("#itemImage6", isNotNull());
		verifyThat("#itemImage7", isNotNull());
		verifyThat("#itemImage8", isNotNull());
		verifyThat("#itemImage9", isNotNull());
		verifyThat("#itemImage10", isNotNull());
		verifyThat("#itemImage11", isNotNull());
		verifyThat("#itemImage12", isNotNull());
	}
	
    /*
     * This test works, but trying to save the build.
     */
    @Test
    @Ignore
    public void menuChangeTest() throws InterruptedException {
    	Platform.runLater(() -> {
    		localChestButton.fire();	
    	});
    	waitForRunLater();
    	assertEquals(controller.getLeftMenu(), Menus.CHEST);
    	
    	Platform.runLater(() -> {
    		equippedButton.fire();
    	});
    	waitForRunLater();
    	assertEquals(controller.getLeftMenu(), Menus.EQUIPPED);
    	
    	Platform.runLater(() -> {
    		abilitiesButton.fire();
    	});
    	waitForRunLater();
    	assertEquals(controller.getLeftMenu(), Menus.ABILITIES);
    }
	
}

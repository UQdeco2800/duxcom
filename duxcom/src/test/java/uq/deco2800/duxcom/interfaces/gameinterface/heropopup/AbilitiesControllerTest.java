package uq.deco2800.duxcom.interfaces.gameinterface.heropopup;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

@RunWith(MockitoJUnitRunner.class)
public class AbilitiesControllerTest extends PopUpMenuControllerTestSetUp {
	
	@Override
	public void start(Stage stage) throws Exception {
		super.start(stage);
	}
	
	@Override
    @Before
    public void setUp() throws IOException, InterruptedException {
    	super.setUp();
    	
    	/* Change to the EquippedController */
    	MouseEvent clicked = new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null);
    	
    	Platform.runLater(() -> {
    		Event.fireEvent(equippedButton, clicked);
    	});
    	waitForRunLater();
    }
	
	@Test
	public void testTest() {
		
	}
}

package uq.deco2800.duxcom.handlers.keyboard;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.controllers.UserInterfaceController;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

/**
 * Tests for keyboard shortcuts in DuxComKeyHandler
 * 
 * @author jake-stevenson
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DuxComKeyHandlerTest {

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private InterfaceManager interfaceManagerMock;
    @Mock
    private DuxComController controllerMock;
    @Mock
    private UserInterfaceController uiControllerMock;

	private DuxComKeyHandler keyHandler;
	
	@Test
	public void testKeyShortcuts() {
		keyHandler = new DuxComKeyHandler(interfaceManagerMock, gameManagerMock, controllerMock);

		// Set up new KeyEvents to simulate each shortcut key press
		KeyEvent m = new KeyEvent(KeyEvent.KEY_PRESSED, "m", "m", KeyCode.M, false, false, false, false);
		KeyEvent c = new KeyEvent(KeyEvent.KEY_PRESSED, "c", "c", KeyCode.C, false, false, false, false);
		KeyEvent h = new KeyEvent(KeyEvent.KEY_PRESSED, "h", "h", KeyCode.H, false, false, false, false);
                KeyEvent s = new KeyEvent(KeyEvent.KEY_PRESSED, "h", "h", KeyCode.S, false, false, false, false);
		KeyEvent o = new KeyEvent(KeyEvent.KEY_PRESSED, "o", "o", KeyCode.O, false, false, false, false);
		KeyEvent d = new KeyEvent(KeyEvent.KEY_PRESSED, "d", "d", KeyCode.D, false, false, false, false);
		KeyEvent equals = new KeyEvent(KeyEvent.KEY_PRESSED, "=", "=", KeyCode.EQUALS, false, false, false, false);
		KeyEvent plus = new KeyEvent(KeyEvent.KEY_PRESSED, "+", "+", KeyCode.PLUS, false, false, false, false);
		KeyEvent minus = new KeyEvent(KeyEvent.KEY_PRESSED, "-", "-", KeyCode.MINUS, false, false, false, false);
		KeyEvent left = new KeyEvent(KeyEvent.KEY_PRESSED, "left", "left", KeyCode.LEFT, false, false, false, false);
		KeyEvent right = new KeyEvent(KeyEvent.KEY_PRESSED, "right", "right", KeyCode.RIGHT, false, false, false, false);
		KeyEvent up = new KeyEvent(KeyEvent.KEY_PRESSED, "up", "up", KeyCode.UP, false, false, false, false);
		KeyEvent down = new KeyEvent(KeyEvent.KEY_PRESSED, "down", "down", KeyCode.DOWN, false, false, false, false);
		KeyEvent one = new KeyEvent(KeyEvent.KEY_PRESSED, "1", "1", KeyCode.DIGIT1, false, false, false, false);
		KeyEvent two = new KeyEvent(KeyEvent.KEY_PRESSED, "2", "2", KeyCode.DIGIT2, false, false, false, false);
		KeyEvent three = new KeyEvent(KeyEvent.KEY_PRESSED, "3", "3", KeyCode.DIGIT3, false, false, false, false);
		KeyEvent four = new KeyEvent(KeyEvent.KEY_PRESSED, "4", "4", KeyCode.DIGIT4, false, false, false, false);
		KeyEvent five = new KeyEvent(KeyEvent.KEY_PRESSED, "5", "5", KeyCode.DIGIT5, false, false, false, false);
		KeyEvent six = new KeyEvent(KeyEvent.KEY_PRESSED, "6", "6", KeyCode.DIGIT6, false, false, false, false);
		KeyEvent seven = new KeyEvent(KeyEvent.KEY_PRESSED, "7", "7", KeyCode.DIGIT7, false, false, false, false);
		KeyEvent tab = new KeyEvent(KeyEvent.KEY_PRESSED, "tab", "tab", KeyCode.TAB, false, false, false, false);
		KeyEvent space = new KeyEvent(KeyEvent.KEY_PRESSED, " ", " ", KeyCode.SPACE, false, false, false, false);
		KeyEvent enter = new KeyEvent(KeyEvent.KEY_PRESSED, "enter", "enter", KeyCode.ENTER, false, false, false, false);
		KeyEvent t = new KeyEvent(KeyEvent.KEY_PRESSED, "t", "t", KeyCode.T, false, false, false, false);

		when(controllerMock.getUIController()).thenReturn(uiControllerMock);

		keyHandler.handle(m);
		verify(gameManagerMock, times(1)).toggleMiniMap();

		keyHandler.handle(c);
		verify(gameManagerMock, times(1)).toggleChat();

		keyHandler.handle(h);
		verify(uiControllerMock, times(1)).openHeroPopUp();
                
                keyHandler.handle(s);
                verify(uiControllerMock, times(1)).openShop();

		keyHandler.handle(o);
		verify(uiControllerMock, times(1)).toggleObjectives();

		keyHandler.handle(d);
		verify(gameManagerMock, times(1)).setDisplayDebug(!gameManagerMock.isDisplayDebug());

		keyHandler.handle(equals);
		keyHandler.handle(plus);
		verify(gameManagerMock, times(2)).zoomIn();

		keyHandler.handle(minus);
		verify(gameManagerMock, times(1)).zoomOut();

		keyHandler.handle(left);
		verify(gameManagerMock, times(1)).moveViewRelative(-30, 0);

		keyHandler.handle(right);
		verify(gameManagerMock, times(1)).moveViewRelative(30, 0);

		keyHandler.handle(up);
		verify(gameManagerMock, times(1)).moveViewRelative(0, -20);

		keyHandler.handle(down);
		verify(gameManagerMock, times(1)).moveViewRelative(0, 20);
		
		verify(uiControllerMock, times(4)).resetFocus();

		keyHandler.handle(one);
		verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.MOVE);

		keyHandler.handle(two);
		verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.ABILITY1);

		when(gameManagerMock.getAbilitySelected()).thenReturn(AbilitySelected.ABILITY1);
		keyHandler.handle(two);
		verify(gameManagerMock, times(2)).setAbilitySelected(AbilitySelected.MOVE);

		keyHandler.handle(three);
		verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.ABILITY2);

		keyHandler.handle(four);
		verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.WEAPON);

		keyHandler.handle(five);
		// does nothing right now

		keyHandler.handle(six);
		// does nothing right now

		keyHandler.handle(seven);
		verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.UTILITY);

		keyHandler.handle(tab);
		verify(gameManagerMock, times(1)).toggleMiniHealthBars();
		verify(uiControllerMock, times(5)).resetFocus();

		keyHandler.handle(space);
		verify(gameManagerMock, times(1)).nextTurn();

		keyHandler.handle(enter);
		verify(gameManagerMock, times(1)).useSelectedAbility();

		keyHandler.handle(t);
		verify(uiControllerMock, times(1)).toggleChatBox();
	}

}

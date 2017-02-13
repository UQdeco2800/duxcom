package uq.deco2800.duxcom.handlers;

import javafx.scene.input.ScrollEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test MouseReleasedHandler
 *
 * @author Alex McLean
 */
@RunWith(MockitoJUnitRunner.class)
public class MouseScrolledHandlerTest{

    private MouseScrolledHandler mouseScrolledHandler;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;

    @Test
    public void testEvents() {
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        mouseScrolledHandler = new MouseScrolledHandler(gameManagerMock);

        ScrollEvent scrollEventIn = new ScrollEvent(ScrollEvent.SCROLL, 0, 0, 0, 0, false, false, false,
                false, false, false, 0, 1, 0, 0, ScrollEvent.HorizontalTextScrollUnits.NONE, 0,
                ScrollEvent.VerticalTextScrollUnits.NONE, 0, 0, null);
        mouseScrolledHandler.handle(scrollEventIn);
        verify(gameManagerMock, times(1)).zoomIn();

        ScrollEvent scrollEventOut = new ScrollEvent(ScrollEvent.SCROLL, 0, 0, 0, 0, false, false, false,
                false, false, false, 0, -1, 0, 0, ScrollEvent.HorizontalTextScrollUnits.NONE, 0,
                ScrollEvent.VerticalTextScrollUnits.NONE, 0, 0, null);
        mouseScrolledHandler.handle(scrollEventOut);
        verify(gameManagerMock, times(1)).zoomOut();
    }

}
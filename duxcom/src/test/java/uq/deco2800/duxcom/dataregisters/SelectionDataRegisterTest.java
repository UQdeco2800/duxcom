package uq.deco2800.duxcom.dataregisters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the SelectionDataRegister
 * @author Alex McLean
 */
public class SelectionDataRegisterTest {

    private SelectionDataRegister selectionDataRegister;

    @Before
    public void setUp() {
        selectionDataRegister = new SelectionDataRegister();
    }

    @Test
    public void testCreation() {
        assertNotNull(selectionDataRegister);
    }

    @Test
    public void testGetData() throws Exception {
        SelectionDataClass selectionDataClass = selectionDataRegister.getData(SelectionType.SELECTION_BASIC);
        assertNotNull(selectionDataClass);
    }
}
package uq.deco2800.duxcom.dataregisters;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test the DataRegisterManager class
 * @author Alex McLean
 */
public class DataRegisterManagerTest {
    /**
     * Tests that all the necessary registers exist
     */
    @Test
    public void testGetRegisters() {
        assertNotNull(DataRegisterManager.getEntityDataRegister());
        assertNotNull(DataRegisterManager.getTileDataRegister());
        assertNotNull(DataRegisterManager.getSelectionDataRegister());
        assertNotNull(DataRegisterManager.getHeroDataRegister());
        assertNotNull(DataRegisterManager.getAbilityDataRegister());
        assertNotNull(DataRegisterManager.getEnemyDataRegister());
    }


}
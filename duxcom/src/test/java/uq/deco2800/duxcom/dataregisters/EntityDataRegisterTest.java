package uq.deco2800.duxcom.dataregisters;

import org.junit.Before;
import org.junit.Test;
import uq.deco2800.duxcom.entities.EntityType;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the EntityDataRegister
 * @author Alex McLean
 */
public class EntityDataRegisterTest {

    private EntityDataRegister entityDataRegister;

    @Before
    public void setUp() {
        entityDataRegister = new EntityDataRegister();
    }

    @Test
    public void testCreation() {
        assertNotNull(entityDataRegister);
    }

    @Test
    public void testGetData() throws Exception {
        EntityDataClass entityDataClass = entityDataRegister.getData(EntityType.BETA_IBIS);
        assertNotNull(entityDataClass);
    }
}
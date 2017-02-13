package uq.deco2800.duxcom.objective;
import org.junit.Assert;
import org.junit.Test;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;

/**
 * Test for the ObjectiveCoordinate class (used purely for storing
 * a movement objective's target coordinates)
 * Created by Tom B on 14/10/2016.
 */
public class ObjectiveCoordinateTest {

    @Test
    public void testInitialisation() {
        int x = 1;
        int y = 4;
        ObjectiveCoordinate oc = new ObjectiveCoordinate(x, y);
        Assert.assertEquals(x, oc.getX());
        Assert.assertEquals(y, oc.getY());
    }

    @Test
    public void testEquals() {
        ObjectiveCoordinate oc1 = new ObjectiveCoordinate(1, 2);
        ObjectiveCoordinate oc2 = new ObjectiveCoordinate(3, 4);
        ObjectiveCoordinate oc3 = new ObjectiveCoordinate(1, 2);
        ObjectiveCoordinate oc4 = new ObjectiveCoordinate(1, 3);
        Boolean b = new Boolean("true");

        Assert.assertTrue(oc1.equals(oc3));
        Assert.assertFalse(oc1.equals(b));
        Assert.assertFalse(oc1.equals(oc2));
        Assert.assertFalse(oc1.equals(oc4));
    }

    @Test
    public void testHashCode() {
        ObjectiveCoordinate oc1 = new ObjectiveCoordinate(1, 2);
        ObjectiveCoordinate oc2 = new ObjectiveCoordinate(2, 1);
        ObjectiveCoordinate oc3 = new ObjectiveCoordinate(1, 2);

        Assert.assertEquals(oc1.hashCode(), oc3.hashCode());
        Assert.assertNotEquals(oc1.hashCode(), oc2.hashCode());
    }
}

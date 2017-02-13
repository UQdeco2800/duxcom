package uq.deco2800.duxcom.overworld.nodes;

import org.junit.Test;


import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * @author mtimmo
 */
public class NodeTest {

	@Test
	public void testCoordinates() throws Exception {
		// Make a new Node object
		Node node = new Node(null, null);

		// Ensure the new object has no pre-calculated values
		assertNull(node.getCoordinates());

		// Make an array of test scenarios
		Point2D[] points = new Point2D[]{
				new Point2D.Double(0, 0),
				new Point2D.Double(Math.random() * 10000, Math.random() * 10000),
				new Point2D.Double(Math.random() * -10000, Math.random() * 10000),
				new Point2D.Double(Math.random() * 10000, Math.random() * -10000),
				new Point2D.Double(Math.random() * -10000, Math.random() * -10000),
		};

		// Test each scenario
		for (Point2D point : points) {
			node.setCoordinates(point);
			assertEquals(node.getCoordinates(), point);
		}
	}

	@Test
	public void testParentNode() throws Exception {
		// Make a new Node object
		Node node = new Node(null, null);

		// Ensure the new object has no pre-calculated values
		assertNull(node.getParentNode());

		// Make an array of test scenarios
		Node node1 = new Node(new Point2D.Double(1, 1), node);
		Node node2 = new Node(new Point2D.Double(1, 2), node1);
		Node node3 = new Node(new Point2D.Double(1, 3), node2);
		Node node4 = new Node(new Point2D.Double(1, 4), node1);

		// Test each scenario
		assertEquals(node1.getParentNode(), node);
		assertEquals(node2.getParentNode(), node1);
		assertEquals(node3.getParentNode(), node2);
		assertEquals(node4.getParentNode(), node1);
	}

	@Test
	public void testNodeDirections() throws Exception {
		// Make a new Node object
		Node node = new Node(null, null);

		// Ensure the new object has no pre-calculated values
		assertNull(node.getNodeLeft());

		// Make an array of test scenarios
		Node node1 = new Node(new Point2D.Double(1, 2), null);
		Node node2 = new Node(new Point2D.Double(2, 2), node1);
		Node node3 = new Node(new Point2D.Double(2, 1), node2);
		Node node4 = new Node(new Point2D.Double(1, 1), node3);

		// Test each scenario
		assertEquals(node1.getNodeAbove(), null);
		assertEquals(node1.getNodeBelow(), null);
		assertEquals(node1.getNodeLeft(), null);
		assertEquals(node1.getNodeRight(), node2);
		assertEquals(node2.getNodeAbove(), node3);
		assertEquals(node2.getNodeBelow(), null);
		assertEquals(node2.getNodeLeft(), node1);
		assertEquals(node2.getNodeRight(), null);
		assertEquals(node3.getNodeAbove(), null);
		assertEquals(node3.getNodeBelow(), node2);
		assertEquals(node3.getNodeLeft(), node4);
		assertEquals(node3.getNodeRight(), null);
		assertEquals(node4.getNodeAbove(), null);
		assertEquals(node4.getNodeBelow(), null);
		assertEquals(node4.getNodeLeft(), null);
		assertEquals(node4.getNodeRight(), node3);
	}

	@Test
	public void testEqualsHash() throws Exception {
		// Make an array of test scenarios
		Node node1 = new Node(null, null);
		Node node2 = new Node(null, null);
		Node node3 = new Node(new Point2D.Double(1, 1), null);
		Node node4 = new Node(new Point2D.Double(1, 1), null);
		Node node5 = new Node(new Point2D.Double(2, 2), null);
		Node node6 = new Node(new Point2D.Double(2, 2), node1);
		Node node7 = new Node(new Point2D.Double(2, 2), node1);

		// Test equals for each scenario
		assertTrue(node1.equals(node2));
		assertTrue(node3.equals(node4));
		assertTrue(node6.equals(node7));
		assertFalse(node1.equals(node3));
		assertFalse(node1.equals(node5));
		assertFalse(node1.equals(node6));
		assertFalse(node3.equals(node5));
		assertFalse(node3.equals(node6));
		assertFalse(node5.equals(node6));

		// Test hashcode for each scenario
		assertEquals(node1.hashCode(), node2.hashCode());
		assertEquals(node3.hashCode(), node4.hashCode());
		assertEquals(node6.hashCode(), node7.hashCode());
		assertNotEquals(node1.hashCode(), node3.hashCode());
		assertNotEquals(node1.hashCode(), node5.hashCode());
		assertNotEquals(node1.hashCode(), node6.hashCode());
		assertNotEquals(node3.hashCode(), node5.hashCode());
		assertNotEquals(node3.hashCode(), node6.hashCode());
	}
}
package uq.deco2800.duxcom.overworld.nodes;

import java.awt.geom.Point2D;

/**
 * A node object represents a point on the virtual map that will be used when drawing a path between levels.
 *
 * @author Sceub
 * @author mtimmo
 */
public class Node {

	// The virtual position of this Node on the Overworld map
	private Point2D coordinates;

	// The Node that this Node branched from
	private Node parentNode;

	// The node connected to the left of this node
	private Node nodeLeft = null;

	// The node connected to the right of this node
	private Node nodeRight = null;

	// The node connected above this node
	private Node nodeAbove = null;

	// The node connected below this node
	private Node nodeBelow = null;

	/**
	 * Constructor.
	 *
	 * @param coordinates the virtual position of this Node on the Overworld map
	 * @param parentNode  the Node that this Node branched from
	 */
	public Node(Point2D coordinates, Node parentNode) {
		setCoordinates(coordinates);
		setParentNode(parentNode);
	}

	/**
	 * Get the virtual coordinates for this Node
	 *
	 * @return the virtual coordinates for this Node
	 */
	public Point2D getCoordinates() {
		return coordinates;
	}

	/**
	 * Set the virtual coordinates for this Node
	 *
	 * @param coordinates the virtual coordinates for this Node
	 */
	public void setCoordinates(Point2D coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Get the parent Node for this Node
	 *
	 * @return the parent Node for this Node
	 */
	public Node getParentNode() {
		return parentNode;
	}

	/**
	 * Set the parent Node for this Node
	 *
	 * @param parentNode the parent Node for this Node
	 */
	private void setParentNode(Node parentNode) {
		this.parentNode = parentNode;

		// If the parent node is not null, mark as a neighbouring node
		if (parentNode != null && parentNode.getCoordinates() != null) {
			Point2D pc = parentNode.getCoordinates();
			if (pc.getX() < this.coordinates.getX()) {
				parentNode.setNodeRight(this);
				this.setNodeLeft(parentNode);
			}
			if (pc.getX() > this.coordinates.getX()) {
				parentNode.setNodeLeft(this);
				this.setNodeRight(parentNode);
			}
			if (pc.getY() < this.coordinates.getY()) {
				parentNode.setNodeBelow(this);
				this.setNodeAbove(parentNode);
			}
			if (pc.getY() > this.coordinates.getY()) {
				parentNode.setNodeAbove(this);
				this.setNodeBelow(parentNode);
			}
		}
	}

	/**
	 * Get the node connected to the left of this node
	 *
	 * @return the node connected to the left of this node
	 */
	public Node getNodeLeft() {
		return nodeLeft;
	}

	/**
	 * Set the node connected to the left of this node
	 *
	 * @param nodeLeft the node connected to the left of this node
	 */
	private void setNodeLeft(Node nodeLeft) {
		this.nodeLeft = nodeLeft;
	}

	/**
	 * Get the node connected to the right of this node
	 *
	 * @return the node connected to the right of this node
	 */
	public Node getNodeRight() {
		return nodeRight;
	}

	/**
	 * Set the node connected to the right of this node
	 *
	 * @param nodeRight the node connected to the right of this node
	 */
	private void setNodeRight(Node nodeRight) {
		this.nodeRight = nodeRight;
	}

	/**
	 * Get the node connected above this node
	 *
	 * @return the node connected above this node
	 */
	public Node getNodeAbove() {
		return nodeAbove;
	}

	/**
	 * Set the node connected above this node
	 *
	 * @param nodeAbove the node connected above this node
	 */
	private void setNodeAbove(Node nodeAbove) {
		this.nodeAbove = nodeAbove;
	}

	/**
	 * Get the node connected below this node
	 *
	 * @return the node connected below this node
	 */
	public Node getNodeBelow() {
		return nodeBelow;
	}

	/**
	 * Set the node connected below this node
	 *
	 * @param nodeBelow the node connected below this node
	 */
	private void setNodeBelow(Node nodeBelow) {
		this.nodeBelow = nodeBelow;
	}

	/**
	 * Checks whether two nodes are equivalent to each other
	 *
	 * @param o the object to compare this node to
	 * @return true if the nodes are equivalent, false if they differ in any way
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Node))
			return false;

		Node node = (Node) o;

		if (getCoordinates() != null ? !getCoordinates().equals(node.getCoordinates()) : node.getCoordinates() != null)
			return false;
		if (getParentNode() != null ? !getParentNode().equals(node.getParentNode()) : node.getParentNode() != null)
			return false;
		if (getNodeLeft() != null ? !getNodeLeft().equals(node.getNodeLeft()) : node.getNodeLeft() != null)
			return false;
		if (getNodeRight() != null ? !getNodeRight().equals(node.getNodeRight()) : node.getNodeRight() != null)
			return false;
		if (getNodeAbove() != null ? !getNodeAbove().equals(node.getNodeAbove()) : node.getNodeAbove() != null)
			return false;
		return getNodeBelow() != null ? getNodeBelow().equals(node.getNodeBelow()) : node.getNodeBelow() == null;

	}

	/**
	 * Generates a unique hashcode that is only ever the same with two identical nodes
	 *
	 * @return the unique hashcode for this node
	 */
	@Override
	public int hashCode() {
		int result = getCoordinates() != null ? getCoordinates().hashCode() : 0;
		result = 31 * result + (getParentNode() != null ? getParentNode().hashCode() : 0);
		result = 31 * result + (getNodeLeft() != null ? getNodeLeft().hashCode() : 0);
		result = 31 * result + (getNodeRight() != null ? getNodeRight().hashCode() : 0);
		result = 31 * result + (getNodeAbove() != null ? getNodeAbove().hashCode() : 0);
		result = 31 * result + (getNodeBelow() != null ? getNodeBelow().hashCode() : 0);
		return result;
	}
}

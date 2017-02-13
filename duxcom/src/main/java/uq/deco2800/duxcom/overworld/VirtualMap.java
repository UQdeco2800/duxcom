package uq.deco2800.duxcom.overworld;

import uq.deco2800.duxcom.overworld.nodes.Level;
import uq.deco2800.duxcom.overworld.nodes.Node;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


/**
 * The map manager for the overworld controller to reference to.
 * This keeps track of the level objects and node objects and provides helper functions for the Overworld controller.
 *
 * @author Sceub
 * @author mtimmo
 */
class VirtualMap {

	private static Node[][] nodes;

	private static List<Level> levels = new ArrayList<>();

	private static int nodesHigh;

	private static int nodesWide;

	private static double gridWidth;

	private static double gridHeight;

	/**
	 * Private constructor required to fix sonar smell.
	 */
	private VirtualMap() {
	}

	static void scaleVirtualMap(double windowHeightPx, double windowWidthPx,
								int numNodesHigh, int numNodesWide) {

		nodesHigh = numNodesHigh;
		nodesWide = numNodesWide;

		nodes = new Node[numNodesHigh + 1][numNodesWide + 1];

		gridWidth = windowWidthPx / (numNodesWide + 1);
		gridHeight = windowHeightPx / (numNodesHigh + 1);
		gridWidth = 60;
		gridHeight = 60;
	}

	/**
	 * Returns the size of the horizontal component of the node grid.
	 *
	 * @return The amount of nodes in a single row of the node grid.
	 */
	static int getNodesWide() {
		return nodesWide;
	}

	/**
	 * Returns the size of the vertical component of the node grid.
	 *
	 * @return The amount of nodes in a single column of the node grid.
	 */
	static int getNodesHigh() {
		return nodesHigh;
	}

	/**
	 * Sets the image of the overworld icons used to represent levels based on
	 * the level state (locked, unlocked, conquered).
	 */
	static void setImages() {
		for (Level level : levels) {
			if (level.isConquered()) {
				level.setLevelImage("complete");
			} else if (level.isUnlocked()) {
				level.setLevelImage("swordIconOpen");

			} else {
				level.setLevelImage("lock");
			}
		}
	}

	/**
	 * Returns the on-screen position that a node corresponds to.
	 *
	 * @param node The node who's on-screen position is returned.
	 * @return A Point2D that contains the x and y on-screen coordinates in the
	 * x and y component of the point respectively.
	 */
	static Point2D getNodeActualPosition(Node node) {
		return new Point2D.Double((node.getCoordinates().getX()) * gridWidth,
				(node.getCoordinates().getY()) * gridHeight);
	}

	private static Node generateHorizontalPath(Node parentNode,
											   Point2D coordinates) {
		// Obtain the x and y position of the parent node
		double x = parentNode.getCoordinates().getX();
		double y = parentNode.getCoordinates().getY();

		// Check if the parent node horizontally matches the final coordinates
		if (Math.abs(x - coordinates.getX()) <= 0) {
			if (Math.abs(y - coordinates.getY()) <= 0) {
				return parentNode.getParentNode();
			}
			return parentNode;
		}

		// Create a new set of coordinates horizontally closer to the final
		// destination
		double newX = x + Math.copySign(1, coordinates.getX() - x);
		Point2D newCoordinates = new Point2D.Double(newX, y);

		// Make new node and add it to the nodes array
		Node newNode;
		if ((newNode = nodes[(int) y][(int) newX]) == null) {
			newNode = new Node(newCoordinates, parentNode);
		}

		// Update the nodes array with the new node
		nodes[(int) y][(int) newX] = newNode;

		// Recursive until 'coordinates' are reached
		return generateHorizontalPath(newNode, coordinates);
	}

	private static Node generateVerticalPath(Node parentNode,
											 Point2D coordinates) {
		// Obtain the x and y position of the parent node
		double x = parentNode.getCoordinates().getX();
		double y = parentNode.getCoordinates().getY();

		// Check if the parent node vertically matches the final coordinates
		if (Math.abs(y - coordinates.getY()) <= 0) {
			if (Math.abs(x - coordinates.getX()) <= 0) {
				return parentNode.getParentNode();
			}
			return parentNode;
		}

		// Create a new set of coordinates vertically closer to the final
		// destination
		double newY = y + Math.copySign(1, coordinates.getY() - y);
		Point2D newCoordinates = new Point2D.Double(x, newY);

		// Make new node and add it to the nodes array
		Node newNode;
		if ((newNode = nodes[(int) newY][(int) x]) == null) {
			newNode = new Node(newCoordinates, parentNode);
		}

		// Update the nodes array with the new node
		nodes[(int) newY][(int) x] = newNode;

		// Recursive until 'coordinates' are reached
		return generateVerticalPath(newNode, coordinates);
	}

	private static Node generatePathBetween(Level parentLevel,
											Point2D coordinates) {
		if (parentLevel == null) {
			return null;
		}

		int xDist = (int) coordinates.getX() - (int) parentLevel
				.getCoordinates().getX();
		int yDist = (int) coordinates.getY() - (int) parentLevel
				.getCoordinates().getY();

		if (Math.abs(yDist) > Math.abs(xDist)) {
			return generateHorizontalPath(generateVerticalPath(parentLevel,
					coordinates), coordinates);
		} else {
			return generateVerticalPath(generateHorizontalPath(parentLevel,
					coordinates), coordinates);
		}
	}

	/**
	 * Checks if a given node is in a given list of nodes.
	 *
	 * @param array The list of nodes that is checked to contain the given node.
	 * @param node  The node that is checked to be in the given list of nodes.
	 * @return A boolean indicating if the node was in the list (true) or if it
	 * wasn't (false)
	 */
	private static boolean arrayContains(Node[] array, Node node) {
		for (Node n : array) {
			if (n.equals(node)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a given node to a given list of nodes.
	 *
	 * @param array The list that has the given node added to it.
	 * @param node  The node that is added to the given list.
	 * @return The new list made after adding the given node to the given list.
	 */
	private static Node[] addToArray(Node[] array, Node node) {
		Node[] newArray = new Node[array.length + 1];
		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[array.length] = node;
		return newArray;
	}

	private static boolean verifyPathArgs(Node startNode, Node endNode,
										  Node[] currentPath) {
		return startNode != null && endNode != null && !arrayContains(
				currentPath, startNode);
	}

	private static Node[] checkFastestPath(Node testNode, Node endNode,
										   Node[] newCurrentPath, Node[] fastestPath) {
		Node[] testPath = getPathBetween(testNode, endNode, newCurrentPath);
		if (testPath.length != 0 && (fastestPath.length == 0
				|| testPath.length < fastestPath.length)) {
			return testPath;
		}
		return fastestPath;
	}

	static Node[] getPathBetween(Node startNode, Node endNode) {
		return getPathBetween(startNode, endNode, new Node[]{});
	}

	private static Node[] getPathBetween(Node startNode, Node endNode,
										 Node[] currentPath) {
		// Check arguments for nulls or duplicates
		if (!verifyPathArgs(startNode, endNode, currentPath)) {
			return new Node[]{};
		}

		// If start node is the same as the end node, return the single
		// coordinate
		if (startNode.getCoordinates().equals(endNode.getCoordinates())) {
			return addToArray(currentPath, startNode);
		}

		// Now we need some test paths and nodes to find the shortest route
		Node[] fastestPath = new Node[]{};
		Node[] newCurrentPath = addToArray(currentPath, startNode);

		// Check each direction to find the fastest path
		fastestPath = checkFastestPath(startNode.getNodeRight(), endNode,
				newCurrentPath, fastestPath);
		fastestPath = checkFastestPath(startNode.getNodeAbove(), endNode,
				newCurrentPath, fastestPath);
		fastestPath = checkFastestPath(startNode.getNodeBelow(), endNode,
				newCurrentPath, fastestPath);
		fastestPath = checkFastestPath(startNode.getNodeLeft(), endNode,
				newCurrentPath, fastestPath);

		// Return the shortest path
		return fastestPath;
	}

	/**
	 * Loads all the levels within VirtualMap to the to LevelRegister.
	 *
	 * @see LevelRegister
	 */
	static void loadLevels() {
		LevelRegister.addLevels();
	}

	/**
	 * Creates a Level out of the arguments given and adds it to the list of
	 * Levels within VirtualMap
	 *
	 * @param levelId          The argument used to open the map corresponding to the level.
	 * @param coordinates      The coordinates within the node grid in which the level is
	 *                         located.
	 * @param parentLevel      The Level which the Level being registered branches off of in
	 *                         the overworld.
	 * @param unlockedAfter    The list of Levels that must be completed before the Level
	 *                         being registered is unlocked.
	 * @param labelText        The text that shows up in the popup label below the level
	 *                         icon.
	 * @param levelDescription The text that shows up in the description box on the
	 *                         overworld.
	 * @return The Level that is created and registered.
	 */
	static Level registerLevel(String levelId, Point2D coordinates,
							   Level parentLevel, Level[] unlockedAfter, String labelText,
							   String levelDescription) {
		// Makes sure the levels are within the bounds of the virtual map.
		if (coordinates.getX() > nodesWide || coordinates.getY() > nodesHigh
				|| coordinates.getX() < 0 || coordinates.getY() < 0) {
			return null;
		}

		// Generate path between parent level and new level
		Node parentNode = generatePathBetween(parentLevel, coordinates);

		// Set up the Level object
		Level level = new Level(levelId, coordinates, parentNode,
				unlockedAfter);

		level.setLevelDescription(levelDescription);
		// Add label text
		level.setLevelLabelText(labelText);

		// Setup image size
		double xPos = getNodeActualPosition(level).getX();
		double yPos = getNodeActualPosition(level).getY();
		level.setUpImageView(xPos, yPos, gridHeight, gridWidth);

		// Add the level to the nodes array
		nodes[(int) coordinates.getY()][(int) coordinates.getX()] = level;

		// Add the level to the levels list
		levels.add(level);

		return level;
	}

	/**
	 * Checks if a given level is unlocked (can be played).
	 *
	 * @param level The level who's unlocked value is returned.
	 * @return A boolean indicating of the given Level is unlocked (true) or not
	 * (false).
	 */
	private static boolean levelUnlocked(Level level) {
		Level[] levels = level.getUnlockedAfter();

		if (levels == null || levels.length == 0) {
			return true;
		}

		for (Level l : levels) {
			if (!l.isConquered()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Refreshes the unlocked status of all levels stored within the VirtualMap.
	 */
	static void refreshLevels() {
		// Check if level should be unlocked
		for (Level level : levels) {
			level.setUnlocked(levelUnlocked(level));
		}
	}

	/**
	 * Sets a given Level to be conquered.
	 *
	 * @param level The Level who's conquered status is set to true.
	 */
	static void conquerLevel(Level level) {
		level.setConquered(true);
		refreshLevels();
	}

	/**
	 * Returns the list of levels stored within VirtualMap.
	 *
	 * @return The list of levels stored within VirtualMap.
	 */
	static List<Level> getLevels() {
		return levels;
	}

	/**
	 * Returns the on-screen height of all generated images in the overworld.
	 *
	 * @return the height value of all generated images in the overworld.
	 */
	static double getImageHeight() {
		return gridHeight;
	}

	/**
	 * Returns the on-screen width of all generated images in the overworld.
	 *
	 * @return the width value of all generated images in the overworld.
	 */
	static double getImageWidth() {
		return gridWidth;
	}
}

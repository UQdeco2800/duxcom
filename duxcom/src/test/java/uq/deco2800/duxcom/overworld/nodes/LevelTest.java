package uq.deco2800.duxcom.overworld.nodes;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * @author mtimmo
 */
public class LevelTest {
	@Test
	public void testStrings() throws Exception {
		// Make a new Level object
		Level level = new Level("potato", new Point2D.Double(1, 1), null, null);

		// Check the level id is recorded on creation
		assertEquals(level.getLevelId(), "potato");

		// Ensure the new object has no pre-calculated values
		assertNull(level.getLevelDescription());

		// Make an array of test scenarios
		String[] strings = new String[]{
				"test",
				"test with spaces",
				"test w1th numb3rs",
				"te$t w!th spe<i@l char&cters",
				"tE$t w!tH e<Ery7H!ng --- %$&*#15632?"
		};

		// Test each scenario
		for (String string : strings) {
			level.setLevelDescription(string);
			assertEquals(level.getLevelDescription(), string);

			level.setLevelLabelText(string);
			assertEquals(level.getLevelLabel().getText(), string);

			level.setLevelId(string);
			assertEquals(level.getLevelId(), string);
		}
	}

	@Test
	public void testBooleans() throws Exception {
		// Make a new Level object
		Level level = new Level("potato", new Point2D.Double(1, 1), null, null);

		// Test default values
		assertTrue(level.isUnlocked());
		assertFalse(level.isConquered());

		// Test getters and setters
		level.setUnlocked(true);
		assertTrue(level.isUnlocked());
		level.setUnlocked(false);
		assertFalse(level.isUnlocked());
		level.setConquered(true);
		assertTrue(level.isConquered());
		level.setConquered(false);
		assertFalse(level.isConquered());
	}
}
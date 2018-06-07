package de.ralfhergert.hearthstone.game.model;

import org.junit.Test;

/**
 * This test ensures that {@link Card} is working correctly.
 */
public class CardTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCardRejectCloningNull() {
		new Card(null);
	}
}

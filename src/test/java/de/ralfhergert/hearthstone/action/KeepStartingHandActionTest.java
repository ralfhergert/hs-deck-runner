package de.ralfhergert.hearthstone.action;

import org.junit.Test;

/**
 * This test ensures that {@link KeepStartingHandAction} is working correctly.
 */
public class KeepStartingHandActionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testActionRejectsNullOrdinal() {
		new MulliganStartingHandAction(null);
	}
}

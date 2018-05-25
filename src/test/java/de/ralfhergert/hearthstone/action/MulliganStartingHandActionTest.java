package de.ralfhergert.hearthstone.action;

import org.junit.Test;

/**
 * This test ensures that {@link MulliganStartingHandAction} is working correctly.
 */
public class MulliganStartingHandActionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testActionRejectsNullOrdinal() {
		new MulliganStartingHandAction(null);
	}
}

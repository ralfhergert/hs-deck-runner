package de.ralfhergert.hearthstone.action;

import org.junit.Test;

/**
 * This test ensures that {@link StartTurnAction} is working correctly.
 */
public class StartTurnActionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testActionRejectsNullOrdinal() {
		new StartTurnAction(null);
	}
}

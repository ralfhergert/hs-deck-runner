package de.ralfhergert.hearthstone.action;

import de.ralfhergert.hearthstone.game.model.GameOutcome;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link DrawCardsAction} is working correctly.
 */
public class DrawCardActionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testActionRejectsNullPlayerOrdinal() {
		new DrawCardsAction(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testActionRejectsZeroCards() {
		new DrawCardsAction(PlayerOrdinal.One, 0);
	}

	@Test
	public void testPlayerSuffersLethalFatigue() {
		final Player player1 = new Player().setCurrentHitPoints(1);
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		// check some predicate assumptions.
		Assert.assertEquals("player one has 1 hit point", 1, startState.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertTrue("player one has an empty library", startState.getPlayer(PlayerOrdinal.One).getHand().isEmpty());
		// let player one draw a card form his empty library.
		HearthstoneGameState state = new DrawCardsAction(PlayerOrdinal.One, 1).apply(startState);
		Assert.assertNotNull("new state should not be null", state);
		Assert.assertEquals("player two should have won", GameOutcome.Player2Wins, state.getOutcome());
	}
}

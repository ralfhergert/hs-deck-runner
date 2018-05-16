package de.ralfhergert.hearthstone.action;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link CreateGameAction} is working correctly.
 */
public class CreateGameActionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCreateGameActionRejectsNullAsPlayer1() {
		new CreateGameAction(null, new Player());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateGameActionRejectsNullAsPlayer2() {
		new CreateGameAction(new Player(), null);
	}

	@Test
	public void testCreateNewGame() {
		final Player player1 = new Player();
		final Player player2 = new Player();
		final HearthstoneGameState game = new CreateGameAction(player1, player2).applyTo(null);
		Assert.assertNotNull("game should not be null", game);
		Assert.assertNotNull("players should not be null", game.getPlayers());
		Assert.assertEquals("number of players", 2, game.getPlayers().length);
		Assert.assertSame("player1 should be the given player", player1, game.getPlayers()[0]);
		Assert.assertSame("player2 should be the given player", player2, game.getPlayers()[1]);
	}
}

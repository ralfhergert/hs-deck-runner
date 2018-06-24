package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.action.CreateGameAction;
import de.ralfhergert.hearthstone.action.StartTurnAction;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link HearthstoneGameState} is working.
 */
public class HearthstoneGameStateTest {

	@Test
	public void ensurePlayersHeroPowerBecomeAvailableWhenStartingPlayersTurn() {
		final Player player1 = new Player().setHeroPower(new HeroPower(2, null));
		final Player player2 = new Player().setHeroPower(new HeroPower(2, null));
		final HearthstoneGameState startState = new CreateGameAction(player1, player2).apply(null);
		// after creating the game both player's hero powers should still be unavailable.
		Assert.assertEquals("player1 heroPower should be unavailable", false, startState.getPlayer(PlayerOrdinal.One).getHeroPower().isAvailable());
		Assert.assertEquals("player2 heroPower should be unavailable", false, startState.getPlayer(PlayerOrdinal.Two).getHeroPower().isAvailable());
		// start the turn for player 1.
		final HearthstoneGameState nextState = new StartTurnAction(PlayerOrdinal.One).apply(startState);
		// player1's hero power should be available now.
		Assert.assertEquals("player1 heroPower should be available", true, nextState.getPlayer(PlayerOrdinal.One).getHeroPower().isAvailable());
		Assert.assertEquals("player2 heroPower should be unavailable", false, nextState.getPlayer(PlayerOrdinal.Two).getHeroPower().isAvailable());
		// the start state should not have been affected.
		Assert.assertEquals("player1 heroPower should be unavailable", false, startState.getPlayer(PlayerOrdinal.One).getHeroPower().isAvailable());
		Assert.assertEquals("player2 heroPower should be unavailable", false, startState.getPlayer(PlayerOrdinal.Two).getHeroPower().isAvailable());
	}
}

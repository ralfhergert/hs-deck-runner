package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test checks that 'Deadly Shot' is working correctly.
 */
public class DeadlyShotTest {

	@Test
	public void testPlayDeadlyShotAgainstOneMinion() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(3)
			.addToHand(CardRepository.createByName("Deadly Shot"));
		final Player player2 = new Player()
			.setHitPoints(30)
			.addToBattlefield(((MinionCard)CardRepository.createByName("Fen Creeper")).createMinion());
		HearthstoneGameState state = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		state.setTurn(Turn.Player1Turn);
		state = ActionUtil.playAction(state, action -> action instanceof PlayAbilityCard);
		Assert.assertNotNull("after state should not be null", state);
		Assert.assertEquals("player one should have less mana", 0, state.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, state.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, state.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, current health", 30, state.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player two, number of minions", 0, state.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
	}
}

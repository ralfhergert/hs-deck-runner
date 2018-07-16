package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayTargetedAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * This test checks that 'Kill Command' is working correctly.
 */
public class KillCommandTest {

	@Test
	public void testPlayKillCommandWithoutAnyMinions() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(3)
			.addToHand(CardRepository.createByName("Kill Command"));
		final Player player2 = new Player()
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Optional<Action<HearthstoneGameState>> foundAction = actions.stream()
			.filter(action -> action instanceof PlayTargetedAbilityCard)
			.filter(action -> ((PlayTargetedAbilityCard)action).getTargetRef().equals(player2.getTargetRef()))
			.findAny();
		Assert.assertTrue("action should have been found", foundAction.isPresent());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.get().apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player two, current hit points", 27, afterState.getPlayer(PlayerOrdinal.Two).getCurrentHitPoints());
	}

	@Test
	public void testPlayKillCommandWithOneBeast() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(3)
			.addToHand(CardRepository.createByName("Kill Command"))
			.addToBattlefield(CardRepository.createTokenByName("Misha"));
		final Player player2 = new Player()
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Optional<Action<HearthstoneGameState>> foundAction = actions.stream()
			.filter(action -> action instanceof PlayTargetedAbilityCard)
			.filter(action -> ((PlayTargetedAbilityCard)action).getTargetRef().equals(player2.getTargetRef()))
			.findAny();
		Assert.assertTrue("action should have been found", foundAction.isPresent());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.get().apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player two, current hit points", 25, afterState.getPlayer(PlayerOrdinal.Two).getCurrentHitPoints());
	}
}

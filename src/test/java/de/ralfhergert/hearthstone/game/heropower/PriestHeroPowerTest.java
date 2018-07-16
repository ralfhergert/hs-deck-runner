package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.PlayTargetedHeroPower;
import de.ralfhergert.hearthstone.game.model.GameOutcome;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * This test ensures that the priest hero power works correctly.
 */
public class PriestHeroPowerTest {

	/**
	 * In this test a game state is created in which player one can heal himself for
	 * 1 hit point. Player 2 is not damaged, so healing player 2 has not effect.
	 * The players have empty hands, so no other play should be viable.
	 */
	@Test
	public void testPriestPlayerHealsHimself() {
		/* create a game state in which */
		final Player player1 = new Player()
			.setCurrentHitPoints(19)
			.setMaxHitPoints(20)
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new PriestHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player()
			.setCurrentHitPoints(20)
			.setMaxHitPoints(20);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		Assert.assertNotNull("actions should not be null", actions);
		// find the action which heals player 1.
		Optional<Action<HearthstoneGameState>> foundAction = actions.stream()
			.filter(action -> action instanceof PlayTargetedHeroPower)
			.filter(action -> ((PlayTargetedHeroPower)action).getTargetRef().equals(player1.getTargetRef()))
			.findAny();
		Assert.assertTrue("action should have been found", foundAction.isPresent());
		// play the hero power.
		HearthstoneGameState afterState = foundAction.get().apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("after state should still be undecided", GameOutcome.Undecided, afterState.getOutcome());
		Assert.assertEquals("player one should have 20 hitPoints", 20, afterState.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
	}

	/**
	 * In this test a game state is created in which player one can heal an opponent minion.
	 */
	@Test
	public void testPriestPlayerHealsOpponentMinion() {
		/* create a game state in which */
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new PriestHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player()
			.addToBattlefield(new Minion().setMaxHitPoints(4).setCurrentHitPoints(1)) // damaged minion
			.addToBattlefield(new Minion().setMaxHitPoints(3).setCurrentHitPoints(2).setElusive(true)) // damaged but elusive
			.addToBattlefield(new Minion().setMaxHitPoints(2).setCurrentHitPoints(2)); // undamaged but elusive
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("no action for the elusive minion should exist", 0, actions.stream()
			.filter(action -> action instanceof PlayTargetedHeroPower)
			.filter(action -> ((PlayTargetedHeroPower)action).getTargetRef().equals(player2.getBattlefield().get(1).getTargetRef()))
			.count());
		// find the action which heals the first minion.
		Optional<Action<HearthstoneGameState>> foundAction = actions.stream()
			.filter(action -> action instanceof PlayTargetedHeroPower)
			.filter(action -> ((PlayTargetedHeroPower)action).getTargetRef().equals(player2.getBattlefield().get(0).getTargetRef()))
			.findAny();
		Assert.assertTrue("action healing the first minion should have been found", foundAction.isPresent());
		// play the hero power.
		HearthstoneGameState afterState = foundAction.get().apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("after state should still be undecided", GameOutcome.Undecided, afterState.getOutcome());
		Assert.assertEquals("minion one of player two should have 3 hitPoints", 3, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getCurrentHitPoints());
	}
}

package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.EndTurnAction;
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

/**
 * This test ensures that the mage hero power works correctly.
 */
public class MageHeroPowerTest {

	/**
	 * In this test a game state is created in which player one can ping player 2 for
	 * lethal using the mage hero power. The players have empty hands, so no other play
	 * should be viable.
	 */
	@Test
	public void testMagePlayerPingsForLethal() {
		/* create a game state in which */
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new MageHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player().setCurrentHitPoints(1);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 3, actions.size());
		// filter the EndTurnAction and pinging own face.
		Action<HearthstoneGameState> foundAction = actions.stream()
			.filter(action -> !(action instanceof EndTurnAction))
			.filter(action -> !(action instanceof PlayTargetedHeroPower && ((PlayTargetedHeroPower)action).getTargetRef().equals(player1.getTargetRef())))
			.findFirst().get();
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayTargetedHeroPower.class, foundAction.getClass());
		// play the hero power.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertTrue("queuedEvents should be empty", afterState.getQueuedEffects().isEmpty());
		Assert.assertEquals("player one should have won", GameOutcome.Player1Wins, afterState.getOutcome());
	}

	/**
	 * In this test a game state is created in which player one can ping a minion of
	 * player 2. The players have empty hands, so no other play
	 * should be viable.
	 */
	@Test
	public void testMagePlayerPingsMinionToDeath() {
		/* create a game state in which */
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new MageHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player()
			.setElusive(true) // declare the player elusive, so the mage hero power can not target this player.
			.addToBattlefield(new Minion().setCurrentHitPoints(1));
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		Assert.assertEquals("player two should have one minion on the battlefield", 1, startState.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		Assert.assertEquals("player two should have no minion in the graveyard", 0, startState.getPlayer(PlayerOrdinal.Two).getGraveyard().size());
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 3, actions.size());
		// filter the EndTurnAction and pinging own face.
		Action<HearthstoneGameState> foundAction = actions.stream()
			.filter(action -> !(action instanceof EndTurnAction))
			.filter(action -> !(action instanceof PlayTargetedHeroPower && ((PlayTargetedHeroPower)action).getTargetRef().equals(player1.getTargetRef())))
			.findFirst().get();
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayTargetedHeroPower.class, foundAction.getClass());
		// play the hero power.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertTrue("queuedEvents should be empty", afterState.getQueuedEffects().isEmpty());
		Assert.assertEquals("game is still undecided", GameOutcome.Undecided, afterState.getOutcome());
		Assert.assertEquals("player two should have no minion on the battlefield", 0, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		Assert.assertEquals("player two should have one minion in the graveyard", 1, afterState.getPlayer(PlayerOrdinal.Two).getGraveyard().size());
	}
}

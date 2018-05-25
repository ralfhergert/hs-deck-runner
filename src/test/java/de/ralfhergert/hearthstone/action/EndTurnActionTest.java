package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that {@link EndTurnAction} is working correctly.
 */
public class EndTurnActionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testActionRejectsNullOrdinal() {
		new EndTurnAction(null);
	}

	@Test
	public void testPlayerSuffersLethalFatigue() {
		final Player player1 = new Player().setHitPoints(20);
		final Player player2 = new Player().setHitPoints(20);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		List<Action<HearthstoneGameState>> foundActions = new ActionFactory().createAllApplicableActions(startState);
		// the only action found should be the EndTurnAction.
		Assert.assertNotNull("foundAction should not be null", foundActions);
		Assert.assertEquals("number of actions", 1, foundActions.size());
		Action<HearthstoneGameState> foundAction = foundActions.get(0);
		Assert.assertEquals("found action is of type", EndTurnAction.class, foundAction.getClass());
		// trigger the action.
		HearthstoneGameState nextState = foundAction.applyTo(startState);
		Assert.assertNotNull("nextState should not be null", nextState);
		Assert.assertEquals("nextState's turn should be", Turn.Player2Turn, nextState.getTurn());
	}
}

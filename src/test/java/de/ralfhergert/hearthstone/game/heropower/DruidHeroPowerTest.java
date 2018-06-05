package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.CharacterAttacksAction;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayHeroPower;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that the druid hero power works correctly.
 */
public class DruidHeroPowerTest {

	/**
	 * This test creates a game state in which the only playable action for player one
	 * is to use the hero power.
	 */
	@Test
	public void testPlayerUsesDruidHeroPower() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new DruidHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		Assert.assertEquals("player one armor should be", 0, startState.getPlayer(PlayerOrdinal.One).getArmor());
		Assert.assertEquals("player one power should be", 0, startState.getPlayer(PlayerOrdinal.One).getPower());
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayHeroPower.class, foundAction.getClass());
		// play the hero power.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one armor should be", 1, afterState.getPlayer(PlayerOrdinal.One).getArmor());
		Assert.assertEquals("player one power should be", 1, afterState.getPlayer(PlayerOrdinal.One).getPower());
		// confirm that startState has not been altered.
		Assert.assertEquals("player one armor should be", 0, startState.getPlayer(PlayerOrdinal.One).getArmor());
		Assert.assertEquals("player one power should be", 0, startState.getPlayer(PlayerOrdinal.One).getPower());
		// check which options the druid player now has.
		List<Action<HearthstoneGameState>> nextActions = new ActionFactory().createAllApplicableActions(afterState);
		nextActions = ActionUtil.remove(nextActions, CharacterAttacksAction.class);
		Assert.assertNotNull("actions should not be null", nextActions);
		Assert.assertEquals("number of found actions", 1, nextActions.size());
		Action<HearthstoneGameState> endTurnAction = nextActions.get(0);
		Assert.assertNotNull("found action should not be null", endTurnAction);
		Assert.assertEquals("found action should by of type", EndTurnAction.class, endTurnAction.getClass());
		// play the hero power.
		HearthstoneGameState finalState = endTurnAction.applyTo(afterState);
		Assert.assertNotNull("final state should not be null", finalState);
		Assert.assertEquals("player one armor should be", 1, finalState.getPlayer(PlayerOrdinal.One).getArmor());
		Assert.assertEquals("player one power should be", 0, finalState.getPlayer(PlayerOrdinal.One).getPower());
		// confirm that afterState has not been altered.
		Assert.assertEquals("player one armor should be", 1, afterState.getPlayer(PlayerOrdinal.One).getArmor());
		Assert.assertEquals("player one power should be", 1, afterState.getPlayer(PlayerOrdinal.One).getPower());

	}
}

package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayHeroPower;
import de.ralfhergert.hearthstone.game.model.GameOutcome;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that the warrior hero power works correctly.
 */
public class WarriorHeroPowerTest {

	/**
	 * This test creates a game state in which the only playable action for player one
	 * is to use the hero power, which will kill player one.
	 */
	@Test
	public void testPlayerSuicidesWithWarlockHeroPower() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new WarriorHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		Assert.assertEquals("player one armor should be", 0, startState.getPlayer(PlayerOrdinal.One).getArmor());
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 2, actions.size());
		// filter the EndTurnAction and return the remaining action.
		Action<HearthstoneGameState> foundAction = actions.stream().filter(action -> !(action instanceof EndTurnAction)).findFirst().get();
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayHeroPower.class, foundAction.getClass());
		// play the hero power.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("game is still undecided", GameOutcome.Undecided, afterState.getOutcome());
		Assert.assertEquals("player one should have armor", 2, afterState.getPlayer(PlayerOrdinal.One).getArmor());
	}
}

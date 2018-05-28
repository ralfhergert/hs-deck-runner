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
 * This test ensures that the {@link RogueHeroPowerEffect} works correctly.
 */
public class RogueHeroPowerTest {

	/**
	 * This test creates a game state in which the only playable action for player one
	 * is to use the hero power.
	 */
	@Test
	public void testPlayerSuicidesWithWarlockHeroPower() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new RogueHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player().setCurrentHitPoints(2);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 2, actions.size());
		// filter the EndTurnAction and return the remaining action.
		Action<HearthstoneGameState> foundAction = actions.stream().filter(action -> !(action instanceof EndTurnAction)).findFirst().get();
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayHeroPower.class, foundAction.getClass());
		// play the hero power.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertTrue("queuedEvents should be empty", afterState.getQueuedEffects().isEmpty());
		Assert.assertEquals("after state should be undecided", GameOutcome.Undecided, afterState.getOutcome());
		Assert.assertNotNull("player one should have a weapon", afterState.getPlayer(PlayerOrdinal.One).getWeapon());
		Assert.assertEquals("weapon should have 1 attack", 1, afterState.getPlayer(PlayerOrdinal.One).getWeapon().getAttack());
		Assert.assertEquals("weapon should have 2 durability", 2, afterState.getPlayer(PlayerOrdinal.One).getWeapon().getDurability());
	}
}

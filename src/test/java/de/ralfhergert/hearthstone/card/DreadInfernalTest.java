package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Dread Infernal' is working correctly.
 */
public class DreadInfernalTest {

	@Test
	public void testPlayGuardianOfKings() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(7)
			.addToBattlefield(new Minion().setHitPoints(1))
			.addToHand(CardRepository.createByName("Dread Infernal"));
		final Player player2 = new Player()
			.setHitPoints(30)
			.addToBattlefield(new Minion().setHitPoints(1));
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 2, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayMinionCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 1, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one should one minion on board", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one's minion should have full health", 6, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getCurrentHitPoints());
		Assert.assertEquals("player one should not have full health", 29, afterState.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player two should not have full health", 29, afterState.getPlayer(PlayerOrdinal.Two).getCurrentHitPoints());
		Assert.assertEquals("player two should not have minions on board", 0, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		// confirm that Dread Infernal has summoning sickness.
		List<Action<HearthstoneGameState>> afterActions = new ActionFactory().createAllApplicableActions(afterState);
		Assert.assertNotNull("actions should not be null", afterActions);
		Assert.assertEquals("number of found actions", 1, afterActions.size());
		Assert.assertEquals("found action should by of type", EndTurnAction.class, afterActions.get(0).getClass());
	}
}

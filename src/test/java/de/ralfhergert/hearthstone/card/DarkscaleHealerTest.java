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
 * This test checks that 'Darkscale Healer' is working correctly.
 */
public class DarkscaleHealerTest {

	@Test
	public void testPlayGuardianOfKings() {
		final Player player1 = new Player()
			.setCurrentHitPoints(20)
			.setMaxHitPoints(30)
			.setAvailableMana(7)
			.addToBattlefield(new Minion().setCurrentHitPoints(1).setMaxHitPoints(5))
			.addToHand(CardRepository.createByName("Darkscale Healer"));
		final Player player2 = new Player()
			.setCurrentHitPoints(23)
			.setMaxHitPoints(30)
			.addToBattlefield(new Minion().setCurrentHitPoints(1).setMaxHitPoints(2));
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
		Assert.assertEquals("player one should have less mana", 2, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one should two minion on board", 2, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one's first minion should have full health", 5, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getCurrentHitPoints());
		Assert.assertEquals("player one's second minion should have been healed", 3, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(1).getCurrentHitPoints());
		Assert.assertEquals("player one should not have been healed", 22, afterState.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player two should not have been healed", 23, afterState.getPlayer(PlayerOrdinal.Two).getCurrentHitPoints());
		Assert.assertEquals("player two should have one minion on board", 1, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		Assert.assertEquals("player two's first minion should have not have been healed", 1, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getCurrentHitPoints());
	}
}

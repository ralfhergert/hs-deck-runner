package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Flamestrike' is working correctly.
 */
public class FlamestrikeTest {

	@Test
	public void testPlayFlamestrike() {
		final Player player1 = new Player()
			.setHitPoints(1)
			.setAvailableMana(7)
			.addToHand(CardRepository.createByName("Flamestrike"));
		final Player player2 = new Player()
			.setHitPoints(1)
			.addToBattlefield(new Minion().setHitPoints(1))
			.addToBattlefield(new Minion().setHitPoints(2))
			.addToBattlefield(new Minion().setHitPoints(3))
			.addToBattlefield(new Minion().setHitPoints(4))
			.addToBattlefield(new Minion().setHitPoints(5));
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayAbilityCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one should have correct health", 1, afterState.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player two should have correct health", 1, afterState.getPlayer(PlayerOrdinal.Two).getCurrentHitPoints());
		Assert.assertEquals("player two should have one minion on board", 1, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		Assert.assertEquals("player two's minion should have one health", 1, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getCurrentHitPoints());
	}
}

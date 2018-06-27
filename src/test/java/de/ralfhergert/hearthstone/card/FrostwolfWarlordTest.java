package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Forstwolf Warlord' is working correctly.
 */
public class FrostwolfWarlordTest {

	@Test
	public void testPlayFrostwolfWarlord() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(5)
			.addToHand(CardRepository.createByName("Frostwolf Warlord"))
			.addToBattlefield(((MinionCard)CardRepository.createByName("Goldshire Footman")).createMinion())
			.addToBattlefield(((MinionCard)CardRepository.createByName("Kobold Geomancer")).createMinion());
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 3, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayMinionCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("active player should have less mana", 0, afterState.getActivePlayer().getAvailableMana());
		Assert.assertEquals("active player should have no overload", 0, afterState.getActivePlayer().getCrystalsLockedNextTurn());
		Assert.assertEquals("active player should have no card in hand", 0, afterState.getActivePlayer().getHand().size());
		Assert.assertEquals("active player should one minion on board", 3, afterState.getActivePlayer().getBattlefield().size());
		Assert.assertEquals("active player, first minion, name", "Frostwolf Warlord", afterState.getActivePlayer().getBattlefield().get(0).getName());
		Assert.assertEquals("active player, first minion, power", 6, afterState.getActivePlayer().getBattlefield().get(0).getPower());
		Assert.assertEquals("active player, first minion, current health", 6, afterState.getActivePlayer().getBattlefield().get(0).getCurrentHitPoints());
		Assert.assertEquals("active player, first minion, max health", 6, afterState.getActivePlayer().getBattlefield().get(0).getMaxHitPoints());
	}
}

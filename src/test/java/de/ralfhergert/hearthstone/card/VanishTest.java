package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Vanish' is working correctly.
 */
public class VanishTest {

	@Test
	public void testPlayVanish() {
		final Player player1 = new Player()
			.setHitPoints(1)
			.setAvailableMana(7)
			.addToHand(CardRepository.createByName("Vanish"))
			.addToBattlefield(((MinionCard)CardRepository.createByName("Bluegill Warrior")).createMinion());
		final Player player2 = new Player()
			.addToHand(CardRepository.createByName("Wisp")) // 1
			.addToHand(CardRepository.createByName("Wisp")) // 2
			.addToHand(CardRepository.createByName("Wisp")) // 3
			.addToHand(CardRepository.createByName("Wisp")) // 4
			.addToHand(CardRepository.createByName("Wisp")) // 5
			.addToHand(CardRepository.createByName("Wisp")) // 6
			.addToHand(CardRepository.createByName("Wisp")) // 7
			.addToHand(CardRepository.createByName("Wisp")) // 8
			.addToHand(CardRepository.createByName("Wisp")) // 9
			.addToBattlefield(((MinionCard)CardRepository.createByName("Acolyte of Pain")).createMinion())
			.addToBattlefield(((MinionCard)CardRepository.createByName("Booty Bay Bodyguard")).createMinion());
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
		Assert.assertEquals("player one should have less mana", 1, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one, cards in hand", 1, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, minions on battlefield", 0, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one, minions in graveyard", 0, afterState.getPlayer(PlayerOrdinal.One).getGraveyard().size());
		Assert.assertEquals("player two, cards in hand", 10, afterState.getPlayer(PlayerOrdinal.Two).getHand().size());
		Assert.assertEquals("player two, minions on battlefield", 0, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		Assert.assertEquals("player two, minions in graveyard", 1, afterState.getPlayer(PlayerOrdinal.Two).getGraveyard().size());
		Assert.assertEquals("player two, 10. card in hand is", "Acolyte of Pain", afterState.getPlayer(PlayerOrdinal.Two).getHand().get(9).getName());
		Assert.assertEquals("player two, card in graveyard", "Booty Bay Bodyguard", afterState.getPlayer(PlayerOrdinal.Two).getGraveyard().get(0).getName());

	}
}

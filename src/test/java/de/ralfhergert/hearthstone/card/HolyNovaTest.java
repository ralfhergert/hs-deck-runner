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
 * This test checks that 'Holy Nova' is working correctly.
 */
public class HolyNovaTest {

	/**
	 * In this scenario (https://www.youtube.com/watch?v=IU3bZrsLKHs&t=11m03s 25.06.2016)
	 *  - the opponent played a 'Knife Juggler'
	 *  - the opponent played an 'Imp Gang Boss'
	 *  - the active player play a 'Holy Nova'
	 * Expected result is:
	 *  - 'Holy Nova' injures 'Imp Gang Boss'
	 *  - 'Imp Gang Boss' is triggered and summons an 'Imp'
	 *  - 'Knife Juggler' is triggered and throws a dagger
	 * (Here the second scenario https://us.battle.net/forums/en/hearthstone/topic/20745105136 is merged in)
	 *  - Knife juggle hit the active player for lethal
	 *  - the heal from 'Holy Nova' heals active player back to 2 HP.
	 */
	@Test
	public void testPlayHolyNovaVersusKnifeJugglerAndImpGangBoss() {
		final Player player1 = new Player()
			.setCurrentHitPoints(1)
			.setMaxHitPoints(30)
			.setAvailableMana(5)
			.addToHand(CardRepository.createByName("Holy Nova"));
		final Player player2 = new Player()
			.setHitPoints(30)
			.addToBattlefield(((MinionCard)CardRepository.createByName("Knife Juggler")).createMinion())
			.addToBattlefield(((MinionCard)CardRepository.createByName("Imp Gang Boss")).createMinion());
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
		Assert.assertEquals("player one, health", 2, afterState.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player two, health", 28, afterState.getPlayer(PlayerOrdinal.Two).getCurrentHitPoints());
		Assert.assertEquals("player two, number of minions on board", 2, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		Assert.assertEquals("player two, first minion, name", "Imp Gang Boss", afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getName());
		Assert.assertEquals("player two, first minion, health", 2, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getCurrentHitPoints());
		Assert.assertEquals("player two, second minion, name", "Imp", afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().get(1).getName());
		Assert.assertEquals("player two, second minion, health", 1, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().get(1).getCurrentHitPoints());
		Assert.assertEquals("player two, number of minions in graveyard", 1, afterState.getPlayer(PlayerOrdinal.Two).getGraveyard().size());
		Assert.assertEquals("player two, minion in graveyard, name", "Knife Juggler", afterState.getPlayer(PlayerOrdinal.Two).getGraveyard().get(0).getName());
	}
}

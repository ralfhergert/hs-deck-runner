package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
import de.ralfhergert.hearthstone.action.PlayTargetedAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test checks that 'Sap' is working correctly.
 */
public class SapTest {

	@Test
	public void testPlaySapVersusStormwindChamion() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setNumberOfManaCrystals(1)
			.addToHand(CardRepository.createByName("Sap"));
		final Player player2 = new Player()
			.setHitPoints(30)
			.setAvailableMana(7)
			.addToHand(CardRepository.createByName("Stormwind Champion"))
			.addToBattlefield(((MinionCard)CardRepository.createByName("Kor'kron Elite")).createMinion());
		HearthstoneGameState state = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		state.setTurn(Turn.Player2Turn);
		state = ActionUtil.playAction(state, action ->
			action instanceof PlayMinionCard && ((PlayMinionCard)action).getPosition() == 1);
		// confirm that the Stormwind Champions buffs has been applied.
		Assert.assertEquals("player two, number of minions", 2, state.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		Assert.assertEquals("player two, minion one, name", "Kor'kron Elite", state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getName());
		Assert.assertEquals("player two, minion one, attack", 5, state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getAttack());
		Assert.assertEquals("player two, minion one, current health", 4, state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getCurrentHitPoints());
		Assert.assertEquals("player two, minion one, max health", 4, state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getMaxHitPoints());
		final TargetRef stormwindRef = state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(1).getTargetRef();
		// switch turns
		state = ActionUtil.endTheTurn(state);
		state = ActionUtil.playAction(state, action ->
			action instanceof PlayTargetedAbilityCard && ((PlayTargetedAbilityCard)action).getTargetRef().equals(stormwindRef));
		Assert.assertEquals("player one, available mana", 0, state.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one, overload", 0, state.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one, number of cards in hand", 0, state.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player two, minions on board", 1, state.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
		Assert.assertEquals("player two, minion one, name", "Kor'kron Elite", state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getName());
		Assert.assertEquals("player two, minion one, attack", 4, state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getAttack());
		Assert.assertEquals("player two, minion one, current health", 3, state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getCurrentHitPoints());
		Assert.assertEquals("player two, minion one, max health", 3, state.getPlayer(PlayerOrdinal.Two).getBattlefield().get(0).getMaxHitPoints());
		Assert.assertEquals("player two, number of cards in hand", 1, state.getPlayer(PlayerOrdinal.Two).getHand().size());
		Assert.assertEquals("player two, card one, name", "Stormwind Champion", state.getPlayer(PlayerOrdinal.Two).getHand().get(0).getName());
	}
}

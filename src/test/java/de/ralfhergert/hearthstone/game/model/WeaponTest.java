package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that {@link Weapon} is working correctly.
 */
public class WeaponTest {

	@Test(expected = IllegalArgumentException.class)
	public void testWeaponRejectsCloningFromNull() {
		new Weapon(null);
	}

	/**
	 * This test gives both players a 0/1 weapon. Due to the weapon having 0 attack
	 * the players only option is ending their turns. This test checks that the
	 * weapons activate and deactivate correctly.
	 */
	@Test
	public void testWeaponActivationOnSwitchingTurns() {
		final Player player1 = new Player()
			.setCurrentHitPoints(10)
			.setWeapon(new Weapon().setAttack(0).setDurability(1).setActive(true));
		final Player player2 = new Player()
			.setCurrentHitPoints(10)
			.setWeapon(new Weapon().setAttack(0).setDurability(1).setActive(false));
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", EndTurnAction.class, foundAction.getClass());
		// switch turns.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("it should be player two's turn now", Turn.Player2Turn, afterState.getTurn());
		Assert.assertEquals("player one's weapon should be deactivated", false, afterState.getPlayer(PlayerOrdinal.One).getWeapon().isActive());
		Assert.assertEquals("player two's weapon should be activated", true, afterState.getPlayer(PlayerOrdinal.Two).getWeapon().isActive());
		Assert.assertEquals("after state gameOutcome should be", GameOutcome.Undecided, afterState.getOutcome());
	}
}

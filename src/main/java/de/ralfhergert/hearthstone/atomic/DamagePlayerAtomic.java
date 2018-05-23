package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.PlayerTakesDamageEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This atomic will damage a player.
 */
public class DamagePlayerAtomic implements Action<HearthstoneGameState> {

	private final Player player;
	private final int damage;

	public DamagePlayerAtomic(Player player, int damage) {
		if (player == null) {
			throw new IllegalArgumentException("player can not be null");
		}
		if (damage < 1) {
			throw new IllegalArgumentException("damage can not be smaller than 1");
		}
		this.player = player;
		this.damage = damage;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final int hitPointsBefore = player.getHitPoints();
		final int hitPointsAfter = player.takeDamage(damage);
		if (hitPointsBefore > hitPointsAfter) {
			nextState.onEvent(new PlayerTakesDamageEvent(nextState, player, hitPointsBefore));
		}
		// firing the event might have altered the player's hit points, so don't use hitPointsAfter.
		return (player.getHitPoints() < 1)
			? new DestroyPlayerAtomic(player).applyTo(nextState)
			: nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}

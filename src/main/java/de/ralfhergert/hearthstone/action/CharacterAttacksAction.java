package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.atomic.DamageMinionAtomic;
import de.ralfhergert.hearthstone.atomic.DamagePlayerAtomic;
import de.ralfhergert.hearthstone.event.CharacterAttackedEvent;
import de.ralfhergert.hearthstone.event.CharacterAttacksEvent;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This action lets the active player play it's hero power.
 */
public class CharacterAttacksAction implements Action<HearthstoneGameState> {

	private final TargetRef attackerRef;
	private final TargetRef targetRef;

	public CharacterAttacksAction(TargetRef attackerRef, TargetRef targetRef) {
		this.attackerRef = attackerRef;
		this.targetRef = targetRef;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		if (!isApplicableTo(state)) {
			return state;
		}
		final HearthstoneGameState nextState = new HearthstoneGameState(state, this)
			.setCurrentAttackerRef(attackerRef)
			.setCurrentTargetRef(targetRef);
		nextState.onEvent(new CharacterAttacksEvent(nextState, attackerRef)); // events may exist which alter the target.
		final Character attacker = nextState.findTarget(attackerRef);
		final Character target = nextState.findTarget(nextState.getCurrentTargetRef());
		final HearthstoneGameState afterState = nextState
			.apply(target instanceof Player
				? new DamagePlayerAtomic(nextState.getPlayerOrdinal((Player)target), attacker.getAttack())
				: new DamageMinionAtomic(nextState.getCurrentTargetRef(), attacker.getAttack()))
			.apply(attacker instanceof Player
				? new DamagePlayerAtomic(nextState.getPlayerOrdinal((Player)attacker), target.getAttack())
				: new DamageMinionAtomic(nextState.getCurrentTargetRef(), target.getAttack()))
			.setCurrentAttackerRef(null)
			.setCurrentTargetRef(null);
		afterState.onEvent(new CharacterAttackedEvent(afterState, attackerRef));
		return afterState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		if (attackerRef == null || targetRef == null) {
			return false;
		}
		final PlayerOrdinal activePlayerOrdinal = state.getTurn().getPlayerOrdinal();
		if (activePlayerOrdinal == null) {
			return false;
		}
		final PlayerOrdinal opponentPlayerOrdinal = activePlayerOrdinal == PlayerOrdinal.One ? PlayerOrdinal.Two : PlayerOrdinal.One;
		return state.findOwnerOrdinal(attackerRef) == activePlayerOrdinal &&
			state.findOwnerOrdinal(targetRef) == opponentPlayerOrdinal &&
			state.findTarget(attackerRef).canAttack() &&
			(state.findTarget(targetRef).hasTaunt() || !state.getPlayer(opponentPlayerOrdinal).hasTauntsOnBoard());
	}

	public TargetRef getTargetRef() {
		return targetRef;
	}
}

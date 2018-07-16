package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.event.EndTurnEvent;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;

/**
 * This effect may be applied onto {@link Character}s to increase their attack power
 * for the duration of the current turn. At the end of the turn this effect will
 * remove itself.
 */
public class IncreaseAttackUntilEndOfTurn implements TargetedEffect, GameEventListener<HearthstoneGameState> {

	private final PlayerOrdinal owner;
	private final int increase;
	private final TargetFinder targetFinder;

	public IncreaseAttackUntilEndOfTurn(PlayerOrdinal owner, int increase, TargetFinder targetFinder) {
		this.owner = owner;
		this.increase = increase;
		this.targetFinder = targetFinder;
	}

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, final TargetRef targetRef) {
		Character target = state.findTarget(targetRef);
		if (target != null && getPossibleTargets(state).contains(targetRef)) {
			target.setPower(increase + target.getAttack());
			target.addEffect(this);
		}
		return state;
	}

	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		for (Character character : state.findAllEffectedCharacters(this)) {
			character.setPower(character.getPower() - increase);
			character.removeEffect(this);
		}
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof EndTurnEvent) {
			EndTurnEvent endTurnEvent = (EndTurnEvent)event;
			// if it is the end of turn of the player who created this effect.
			if (owner == endTurnEvent.getPlayerOrdinal()) {
				return unapplyOn(state);
			}
		}
		return state;
	}

	@Override
	public List<TargetRef> getPossibleTargets(HearthstoneGameState state) {
		return targetFinder.findPossibleTargets(state);
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}

package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.EffectEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.UntilEndOfTurn;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.target.AnyNonElusiveCharacter;

import java.util.List;

/**
 * This effect is a permanent modification of the attack of a character.
 */
public class FreezeCharacterEffect implements GeneralEffect, TargetedEffect, GameEventListener<HearthstoneGameState> {

	private final TargetFinder targetFinder;
	private final EffectEventListener<FreezeCharacterEffect> eventListener;

	public FreezeCharacterEffect() {
		this(new AnyNonElusiveCharacter());
	}

	public FreezeCharacterEffect(TargetFinder targetFinder) {
		this(targetFinder, new UntilEndOfTurn<>());
	}

	public FreezeCharacterEffect(TargetFinder targetFinder, EffectEventListener<FreezeCharacterEffect> eventListener) {
		this.targetFinder = targetFinder;
		this.eventListener = eventListener;
	}

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, TargetRef targetRef) {
		Character character = state.findTarget(targetRef);
		if (character != null) {
			character.addEffect(this);
			return applyTo(state);
		}
		return state;
	}

	/**
	 * Applies this buff.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		if (character != null) {
			character.setFrozen(true);
		}
		return state;
	}

	/**
	 * Removes the buff.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		if (character != null) {
			character.setFrozen(false);
			character.removeEffect(this);
		}
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		return eventListener.onEvent(state, event, this);
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}

	@Override
	public List<TargetRef> getPossibleTargets(HearthstoneGameState state) {
		return targetFinder.findPossibleTargets(state);
	}
}

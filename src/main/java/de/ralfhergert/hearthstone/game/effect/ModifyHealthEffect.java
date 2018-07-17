package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.target.AnyNonElusiveCharacter;

import java.util.List;

/**
 * This effect is a permanent modification of the health of a character.
 */
public class ModifyHealthEffect implements GeneralEffect, TargetedEffect, GameEventListener<HearthstoneGameState> {

	private final TargetFinder targetFinder;
	private final int modification;

	public ModifyHealthEffect(int modification) {
		this(modification, new AnyNonElusiveCharacter());
	}

	public ModifyHealthEffect(int modification, TargetFinder targetFinder) {
		this.targetFinder = targetFinder;
		this.modification = modification;
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
	 * Applies the buff.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		if (character != null) {
			character.setCurrentHitPoints(character.getCurrentHitPoints() + modification);
			character.setMaxHitPoints(character.getMaxHitPoints() + modification);
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
			character.setCurrentHitPoints(character.getCurrentHitPoints() - modification);
			character.setMaxHitPoints(character.getMaxHitPoints() - modification);
			character.removeEffect(this);
		}
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		return state;
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

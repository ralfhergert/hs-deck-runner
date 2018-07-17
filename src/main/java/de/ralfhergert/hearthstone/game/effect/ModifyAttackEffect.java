package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.EffectEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.NoEffectEventListener;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.target.AnyNonElusiveCharacter;

import java.util.List;

/**
 * This effect is a permanent modification of the attack of a character.
 */
public class ModifyAttackEffect implements GeneralEffect, TargetedEffect, GameEventListener<HearthstoneGameState> {

	private final TargetFinder targetFinder;
	private final int modification;
	private final EffectEventListener<ModifyAttackEffect> eventListener;

	public ModifyAttackEffect(int modification) {
		this(modification, new NoEffectEventListener<>());
	}

	public ModifyAttackEffect(int modification, EffectEventListener<ModifyAttackEffect> eventListener) {
		this(modification, eventListener, new AnyNonElusiveCharacter());
	}

	public ModifyAttackEffect(int modification, TargetFinder targetFinder) {
		this(modification, new NoEffectEventListener<>(), targetFinder);
	}

	public ModifyAttackEffect(int modification, EffectEventListener<ModifyAttackEffect> eventListener, TargetFinder targetFinder) {
		this.modification = modification;
		this.eventListener = eventListener;
		this.targetFinder = targetFinder;
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
			character.setPower(character.getPower() + modification);
		}
		return state;
	}

	/**
	 * Removes the buff.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		character.setPower(character.getPower() - modification);
		character.removeEffect(this);
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

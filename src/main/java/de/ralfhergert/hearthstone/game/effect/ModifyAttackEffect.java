package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.EffectEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.NoEffectEventListener;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This effect is a permanent modification of the attack of a character.
 */
public class ModifyAttackEffect implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	private final TargetRef targetRef;
	private final int modification;
	private final EffectEventListener<ModifyAttackEffect> eventListener;

	public ModifyAttackEffect(TargetRef targetRef, int modification) {
		this(targetRef, modification, new NoEffectEventListener<>());
	}

	public ModifyAttackEffect(TargetRef targetRef, int modification, EffectEventListener<ModifyAttackEffect> eventListener) {
		this.targetRef = targetRef;
		this.modification = modification;
		this.eventListener = eventListener;
	}

	/**
	 * Applies the buff.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Character character = state.findTarget(targetRef);
		character.addEffect(this);
		character.setPower(character.getPower() + modification);
		return state;
	}

	/**
	 * Removes the buff.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		character.removeEffect(this);
		character.setPower(character.getPower() - modification);
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
}

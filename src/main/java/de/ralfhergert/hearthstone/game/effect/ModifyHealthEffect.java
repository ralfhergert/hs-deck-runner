package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This effect is a permanent modification of the health of a character.
 */
public class ModifyHealthEffect implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	private final TargetRef targetRef;
	private final int modification;

	public ModifyHealthEffect(TargetRef targetRef, int modification) {
		this.targetRef = targetRef;
		this.modification = modification;
	}

	/**
	 * Applies the buff.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Character character = state.findTarget(targetRef);
		character.addEffect(this);
		character.setCurrentHitPoints(character.getCurrentHitPoints() + modification);
		character.setMaxHitPoints(character.getMaxHitPoints() + modification);
		return state;
	}

	/**
	 * Removes the buff.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		character.removeEffect(this);
		character.setCurrentHitPoints(character.getCurrentHitPoints() - modification);
		character.setMaxHitPoints(character.getMaxHitPoints() - modification);
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
}

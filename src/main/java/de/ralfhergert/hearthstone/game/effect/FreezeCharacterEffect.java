package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.EffectEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.UntilEndOfTurn;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This effect is a permanent modification of the attack of a character.
 */
public class FreezeCharacterEffect implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	private final TargetRef targetRef;
	private final EffectEventListener<FreezeCharacterEffect> eventListener;

	public FreezeCharacterEffect(TargetRef targetRef) {
		this(targetRef, new UntilEndOfTurn<>());
	}

	public FreezeCharacterEffect(TargetRef targetRef, EffectEventListener<FreezeCharacterEffect> eventListener) {
		this.targetRef = targetRef;
		this.eventListener = eventListener;
	}

	/**
	 * Applies this buff.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Character character = state.findTarget(targetRef);
		character.addEffect(this);
		character.setFrozen(true);
		return state;
	}

	/**
	 * Removes the buff.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		character.removeEffect(this);
		character.setFrozen(false);
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

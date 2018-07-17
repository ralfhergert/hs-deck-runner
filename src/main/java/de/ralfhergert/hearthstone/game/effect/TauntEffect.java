package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.target.AnyNonElusiveCharacter;

import java.util.List;

/**
 * This effect gives the minion it is applied to taunt.
 */
public class TauntEffect implements GeneralEffect, TargetedEffect, GameEventListener<HearthstoneGameState> {

	private final TargetFinder targetFinder;

	public TauntEffect() {
		this(new AnyNonElusiveCharacter());
	}

	public TauntEffect(TargetFinder targetFinder) {
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

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		if (character != null) {
			character.setHasTaunt(true);
		}
		return state;
	}

	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		Character character = state.getEffectOwner(this);
		if (character != null) {
			character.setHasTaunt(false);
			character.removeEffect(this);
		}
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof MinionEntersBattlefieldEvent) {
			MinionEntersBattlefieldEvent minionEvent = (MinionEntersBattlefieldEvent)event;
			// is it the minion which owns this effect?
			if (state.getEffectOwner(this).getTargetRef().equals(minionEvent.getMinionRef())) {
				return applyTo(state);
			}
		}
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}

	@Override
	public List<TargetRef> getPossibleTargets(HearthstoneGameState state) {
		return targetFinder.findPossibleTargets(state);
	}
}

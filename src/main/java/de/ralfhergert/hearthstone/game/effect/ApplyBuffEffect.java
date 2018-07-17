package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.target.AnyNonElusiveCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This effect applies all given effect onto a target.
 */
public class ApplyBuffEffect implements TargetedEffect {

	private final TargetFinder targetFinder;
	private final List<GeneralEffect> buffs = new ArrayList<>();

	public ApplyBuffEffect(GeneralEffect... buffs) {
		this(new AnyNonElusiveCharacter(), buffs);
	}

	public ApplyBuffEffect(TargetFinder targetFinder, GeneralEffect... buffs) {
		this.buffs.addAll(Arrays.asList(buffs));
		this.targetFinder = targetFinder;
	}

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, TargetRef targetRef) {
		final Character character = state.findTarget(targetRef);
		if (character != null) {
			HearthstoneGameState nextState = state;
			for (GeneralEffect buff : buffs) {
				character.addEffect(buff);
				nextState = buff.applyTo(nextState);
			}
			return nextState;
		}
		return state;
	}

	@Override
	public List<TargetRef> getPossibleTargets(HearthstoneGameState state) {
		return targetFinder.findPossibleTargets(state);
	}
}

package de.ralfhergert.hearthstone.game.target;

import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This {@link TargetFinder} implementation identifies any non-elusive opposing {@link Character} as target.
 */
public class AnyNonElusiveOpposingCharacter implements TargetFinder {

	@Override
	public List<TargetRef> findPossibleTargets(HearthstoneGameState state) {
		return state.getPassivePlayer().getAllCharactersInOrderOfPlay().stream()
			.filter(character -> !character.isElusive())
			.map(Character::getTargetRef)
			.collect(Collectors.toList());
	}
}

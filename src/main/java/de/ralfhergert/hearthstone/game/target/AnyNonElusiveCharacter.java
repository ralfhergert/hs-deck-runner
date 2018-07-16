package de.ralfhergert.hearthstone.game.target;

import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This {@link TargetFinder} implementation identifies any {@link Character} as target.
 */
public class AnyNonElusiveCharacter implements TargetFinder {

	@Override
	public List<TargetRef> findPossibleTargets(HearthstoneGameState state) {
		return state.getAllCharacters().stream()
			.filter(character -> !character.isElusive())
			.map(Character::getTargetRef)
			.collect(Collectors.toList());
	}
}

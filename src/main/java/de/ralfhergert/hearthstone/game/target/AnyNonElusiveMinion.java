package de.ralfhergert.hearthstone.game.target;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This {@link TargetFinder} implementation identifies any non-elusive {@link Minion}s as target.
 */
public class AnyNonElusiveMinion implements TargetFinder {

	@Override
	public List<TargetRef> findPossibleTargets(HearthstoneGameState state) {
		return state.getAllMinionInOrderOfPlay().stream()
			.filter(minion -> !minion.isElusive())
			.map(Minion::getTargetRef)
			.collect(Collectors.toList());
	}
}

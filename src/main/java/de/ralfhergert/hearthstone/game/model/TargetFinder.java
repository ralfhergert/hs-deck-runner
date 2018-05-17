package de.ralfhergert.hearthstone.game.model;

import java.util.List;

/**
 * Interface for objects being able to identify and find targets.
 */
public interface TargetFinder {

	List<Target> findPossibleTargets(HearthstoneGameState state);
}

package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

import java.util.List;

/**
 * This interface describes how an action-discovery looks alike.
 * @param <ObjectOfInterest> for which object this discovery shall explore possible actions
 */
public interface ActionDiscovery<ObjectOfInterest> {

	List<Action<HearthstoneGameState>> createPossibleActions(ObjectOfInterest object, HearthstoneGameState state);
}

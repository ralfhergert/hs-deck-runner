package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class helps to shorten the tests by providing shared methods and utils.
 */
public final class ActionUtil {

	private ActionUtil() { /* no need to instantiate */ }

	/**
	 * This method remove the given action class from the list of actions.
	 */
	public static List<Action<HearthstoneGameState>> remove(List<Action<HearthstoneGameState>> actions, Class<? extends Action<HearthstoneGameState>> actionClass) {
		Assert.assertNotNull("actions should not be null", actions);
		// filter the given action class and return the remaining actions.
		return actions.stream().filter(action -> !(action.getClass().equals(actionClass))).collect(Collectors.toList());
	}
}

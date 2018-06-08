package de.ralfhergert.hearthstone.game.minion;

import de.ralfhergert.hearthstone.game.model.Minion;

/**
 * Interface for all minion factories.
 */
public interface MinionFactory {

	Minion create();
}

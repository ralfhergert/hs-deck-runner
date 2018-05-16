package de.ralfhergert.hearthstone.game.model;

/**
 * This class covers the state of the starting hand.
 */
public enum StartingHandState {
	Undecided, // the player has still to decided wether to mulligan or keep the hand.
	Keep,
	Mulligan
}

package de.ralfhergert.hearthstone.card;

/**
 * This exception is thrown when a requested card could not be found.
 */
public class CardNotFoundException extends RuntimeException {

	public CardNotFoundException(String message) {
		super(message);
	}
}

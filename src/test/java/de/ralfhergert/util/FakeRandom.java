package de.ralfhergert.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This helper class is used in test to get predictable results.
 */
public class FakeRandom extends Random {

	private final Iterator<Integer> preparedIntsIterator;
	private final List<Integer> nextIntBounds = new ArrayList<>();

	public FakeRandom(Integer... intValues) {
		this(Arrays.asList(intValues));
	}

	public FakeRandom(Iterable<Integer> preparedInts) {
		this.preparedIntsIterator = preparedInts.iterator();
	}

	@Override
	public int nextInt(int bound) {
		nextIntBounds.add(bound);
		return preparedIntsIterator.next();
	}
}

package simulation.lib.rng;

import java.util.Random;

/**
 * Standard random number generator
 */
public class StdRNG extends RNG {
	Random rng;
	
	public StdRNG() {
		super();
	}
	/**
	 * Constructor with current milliseconds as seed
	 * @param currentTimeMillis
	 */
	public StdRNG(long currentTimeMillis) {
		super(currentTimeMillis);
	}

	/**
	 * @see RNG#setSeed(long)
	 */
	@Override
	public void setSeed(long seed) {
		/*
		 * TODO Problem 2.3.1 - setSeed
		 * Update the seed of your random number generator rng (Hint: rng can be null @see RNG)
		 */
		if(rng != null) {
			rng.setSeed(seed);			
		}
	}

	/**
	 * @see RNG#rnd()
	 */
	@Override
	public double rnd() {
		/*
		 * TODO Problem 2.3.1 - rnd
		 * Create a random value: 0 < value < 1
		 */
		double rv = rng.nextDouble();
		return rv;
	}
}

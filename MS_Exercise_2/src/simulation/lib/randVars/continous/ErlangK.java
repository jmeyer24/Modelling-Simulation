package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.3 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Erlang-k distributed random variable.
 */
public class ErlangK extends RandVar {
	private double lambda;
	private int k;

	public ErlangK(RNG rng, double lambda, int k) {
		super(rng);
		this.lambda = lambda;
		this.k = k;
	}

	@Override
	public double getRV() {		
		double product = 1;
		double u = 0;
		for (int i = 0; i < k; i++) {
			u = super.rng.rnd();
			product *= u;
		}
		return -1/lambda * Math.log(product);
	}

	@Override
	public double getMean() {
		return k/lambda;
	}

	@Override
	public double getVariance() {
		return k/Math.pow(lambda,2);
	}

	@Override
	public void setMean(double m) {
		if (m <= 0)
			throw new IllegalArgumentException("Mean must be greater than zero.");
		else {
			lambda = k/m;
		}
	}

	@Override
	public void setStdDeviation(double s) {
		//1/Math.sqrt(k) = s / getMean();
		k = (int)Math.pow(getMean()/s,2);
		lambda = Math.sqrt(k)/s;
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setMean(m);
		setStdDeviation(s);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + 
				"\tlambda: " + lambda + "\n" +
				"\tk: " + k + "\n";
	}		
}

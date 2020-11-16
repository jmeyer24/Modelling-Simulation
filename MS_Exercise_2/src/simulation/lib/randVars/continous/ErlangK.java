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
		//script f(x), range [0,inf)
		double x = rng.rnd() * Double.MAX_VALUE;
		int fac = 1;
		for (int i=1; i<=k-1; i++)
        {
			fac *= i;
        }
		return (Math.pow(lambda*x, k-1)/fac) * lambda * Math.exp(-lambda * x);
	}

	@Override
	public double getMean() {
		throw new UnsupportedOperationException("the k-Erlang function got no mean");
	}

	@Override
	public double getVariance() {
		return k/Math.pow(lambda,2);
	}

	@Override
	public void setMean(double m) {
		throw new UnsupportedOperationException("the k-Erlang function got no mean");
	}

	@Override
	public void setStdDeviation(double s) {
		lambda = Math.sqrt(k)/s;
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setStdDeviation(s);
		throw new UnsupportedOperationException("the k-Erlang function got no mean");
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "ErlangK";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\tlambda: " + lambda + "\n" +
				"\tk: " + k + "\n";
	}		
}

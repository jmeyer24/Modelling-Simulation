/**
 * 
 */
package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.2 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Expnential distributed random variable.
 */
public class Exponential extends RandVar {
	private double lambda;
	
	public Exponential(RNG rng, double lambda) {
		super(rng);
		this.lambda = lambda;
	}

	@Override
	public double getRV() {
		//script f(x)
		//* Double.Max_Value as the interval for the function is [0,inf)
		double x = rng.rnd() * Double.MAX_VALUE;
		return lambda * Math.exp(-lambda * x);
	}

	@Override
	public double getMean() {
		throw new UnsupportedOperationException("the exponential function got no mean");
	}

	@Override
	public double getVariance() {
		return 1/Math.pow(lambda, 2);
	}

	@Override
	public void setMean(double m) {
		throw new UnsupportedOperationException("the exponential function got no mean");
	}

	@Override
	public void setStdDeviation(double s) {
		lambda = 1/s;
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setStdDeviation(s);
		throw new UnsupportedOperationException("the exponential function got no mean");
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Exponential";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\tlambda: " + lambda + "\n";
	}
	
}

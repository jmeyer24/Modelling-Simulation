package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.1 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Uniform distributed random variable.
 */
public class Uniform extends RandVar {
	private double a;
	private double b;
	
	//private double mean;
	//private double cvar;
	//private double range;

	public Uniform(RNG rng, double a, double b) { //double mean, double cvar, double range) {
		super(rng);
		this.a = a;
		this.b = b;
		
		//this.mean = mean;
		//this.cvar = cvar;
		//this.range = range;
	}

	@Override
	public double getRV() {
		return (rng.rnd()-a)/(b-a);
	}

	@Override
	public double getMean() {
		return mean;
	}

	@Override
	public double getVariance() {
		return Math.pow(range, 2)/12;
	}

	@Override
	public void setMean(double m) {
		this.mean = m;
	}

	@Override
	public void setStdDeviation(double s) {
		this.cvar = s/mean;
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setMean(m);
		setStdDeviation(s);
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String toString() {
		return null;
	}
	
}

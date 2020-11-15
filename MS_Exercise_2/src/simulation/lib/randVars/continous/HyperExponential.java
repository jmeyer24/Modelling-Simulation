package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;
import static java.lang.Math.*;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.4 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Hyperexponential distributed random variable.
 */
public class HyperExponential extends RandVar {
	private double[] lambdas;
	private double[] ps;
	
	public HyperExponential(RNG rng, double[] lambdas, double[] ps) {
		super(rng);
		this.lambdas = lambdas;
		this.ps = ps;
	}

	@Override
	public double getRV() {
		double result = 0;
		for(int i=0;i<ps.length;i++) {
			result += ps[i] * lambdas[i] * Math.exp(-lambdas[i] * rng.rnd() * Double.MAX_VALUE);
		}
		return result;
	}

	@Override
	public double getMean() {
		throw new IllegalArgumentException("got no mean");
	}

	@Override
	public double getVariance() {
		double ex = 0;
		double cvar;
		double a = 0;
		for(int i=0;i<ps.length;i++) {
			ex += ps[i]/lambdas[i];
			a += 2*ps[i]/Math.pow(lambdas[i], 2);
		}
		cvar = Math.sqrt(a/Math.pow(ex, 2) - 1);
		double var = Math.pow(cvar * ex,2);
		return var;
	}

	@Override
	public void setMean(double m) {
		throw new IllegalArgumentException("got no mean");
	}

	@Override
	public void setStdDeviation(double s) {
		
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setStdDeviation(s);
		throw new IllegalArgumentException("got no mean");
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}

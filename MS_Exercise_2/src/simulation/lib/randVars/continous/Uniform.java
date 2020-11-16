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

	public Uniform(RNG rng, double a, double b) {
		super(rng);
		this.a = a;
		this.b = b;
	}

	@Override
	public double getRV() {
		double u = super.rng.rnd();
		return a + u*(b-a);
	}

	@Override
	public double getMean() {
		return (a+b)/2;
	}

	@Override
	public double getVariance() {
		return Math.pow(b-a, 2)/12;
	}

	@Override
	public void setMean(double m) {
		//move a and b to the direction of new mean
		double dev = m - getMean();
		this.a = this.a + dev;
		this.b = this.b + dev;
	}

	@Override
	public void setStdDeviation(double s) {
		//sqrt of variance stdDev = (b-a)/2sqrt(3)
		//s * 2*Math.sqrt(3) = b-a;
		//s*2*Math.sqrt(3) = mean+x - (mean-x) [x abweichung von mean]
		//s*2*Math.sqrt(3) = 2x
		//s*Math.sqrt(3) = x
		//move b and a by x
		double x = s*Math.sqrt(3);
		double mean = getMean();
		this.a = mean - x;
		this.b = mean + x;
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setMean(m);
		setStdDeviation(s);
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return super.toString() +
				"\ta: " + a + "\n" +
				"\tb: " + b + "\n";
	}
	
}

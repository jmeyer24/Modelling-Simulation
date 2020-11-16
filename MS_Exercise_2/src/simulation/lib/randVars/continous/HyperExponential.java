package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.4 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Hyperexponential distributed random variable.
 */
public class HyperExponential extends RandVar {
	private double[] lambdas;
	private double[] probabilities;
	
	public HyperExponential(RNG rng, double[] lambdas, double[] probabilities) {
		super(rng);
		//both got length k, one of them is picked for the exponential function representative (see script)
		this.lambdas = lambdas;
		this.probabilities = probabilities;
	}

	@Override
	public double getRV() {
		double x = rng.rnd() * Double.MAX_VALUE;
		double result = 0;
		for(int i=0;i<probabilities.length;i++) {
			result += probabilities[i] * lambdas[i] * Math.exp(-lambdas[i] * x);
		}
		return result;
	}

	@Override
	public double getMean() {
		throw new UnsupportedOperationException("the HyperExponential function got no mean");
	}

	@Override
	public double getVariance() {
		//formula wikipedia, script 1.3.4 Var
		double E1 = 0;
		double E2 = 0;
		for(int i=0;i<probabilities.length;i++) {
			double p = probabilities[i];
			double l = lambdas[i];
			E1 += p/l;
			E2 += 2*p/Math.pow(l, 2);
		}
		double var = E2 - Math.pow(E1, 2);
		return var;
	}

	@Override
	public void setMean(double m) {
		throw new UnsupportedOperationException("the HyperExponential function got no mean");
	}

	@Override
	public void setStdDeviation(double s) {
		//TODO
		//endless loop this would be :P
		//setVariance(Math.pow(s, 2));
		/*
		 * std = sqrt(E2 - E1^2) <=> std = sqrt(2*p1/l1^2+2*p2/l2^2 - (p1/l1 + p2/l2)^2) 
		 * <=> std = sqrt(2*p1/l1^2 + 2*p2/l2^2 - [(p1/l1)^2 + 2*p1*p2/(l1*l2) + (p2/l2)^2])
		 * <=> std = sqrt((2*p1-p1^2)/l1^2 + (2*p2-p2^2)/l2^2 + 2*p1*p2/(l1*l2)) 
		*/
		throw new UnsupportedOperationException("this is too hard to calculate...");
		
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setStdDeviation(s);
		throw new UnsupportedOperationException("the HyperExponential function got no mean");
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "HyperExponential";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\tlambdas: " + lambdas.toString() + "\n" +
				"\tprobabilities: " + probabilities.toString() + "\n";
	}
}

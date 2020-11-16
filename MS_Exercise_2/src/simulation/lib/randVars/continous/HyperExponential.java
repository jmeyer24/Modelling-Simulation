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
		//both got length k, here k = 2, one of them is picked for the exponential function representative (see script)
		this.lambdas = lambdas;
		this.probabilities = probabilities;
	}

	@Override
	public double getRV() {
		double u_1 = rng.rnd();
		double u_2 = rng.rnd();
		
		double sum = 0;
		int j = -1;
		while (sum < u_1) {
			j++;
			sum += probabilities[j];
		}
		
		return -Math.log(u_2)/lambdas[j];
	}

	@Override
	public double getMean() {
		double mean = 0; 
		for (int i = 0; i < probabilities.length; i++) {
			mean += probabilities[i]/lambdas[i];
		}
		return mean;
	}

	@Override
	public double getVariance() {
		//https://de.wikipedia.org/wiki/Hyperexponentialverteilung unter Eigenschaften
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
		/*
		 * Gleichungssystem lösen?!
		 * wähle:
		 * lamdas[0] = 1/E * (1 + sqrt(cvar^2 - 1/cvar^2 + 1))
		 * lamdas[1] = 1/E * (1 - sqrt(cvar^2 - 1/cvar^2 + 1))
		 * 
		 * probabilities[0] / lamdas[0] = probabilities[1] / lambdas[1]
		 * 
		 * 1 = prob[0] + prob[1] <=> prob[1] = 1 - prob[0]
		 * 
		 * Lösung:
		 * prob[0] = (1-prob[0]) * lambdas[0] /lambdas[1]
		 * prob[0]  = lambdas[0]/lambdas[1] - lambdas[0]*prob[0] / lambdas[1]
		 * prob[0] + l[0]*prob[0]/l[1] = l[0]/l[1]
		 * prob[0]  = (l[0]/l[1]) / [1 + l[0]/l[1]]
		 * prob[0] = (l[0]/l[1]) / a
		 * prob[0] = l[0]/l[1] * 1/a
		 * prob[0] = l[0]/ (l[1]*a)
		 * prob[0] = l[0]/ (l[1]*[1 + l[0]/l[1]])
		 * prob[0] = l[0] / (l[1]+l[0])
		 * 
		 * prob[1] = 1-prob[0]
		 */
		double cvar = super.getCvar(); 
		lambdas[0] = 1/m * (1 + Math.sqrt((Math.pow(cvar,2) - 1)/(Math.pow(cvar,2) + 1)));
		lambdas[1] = 1/m * (1 - Math.sqrt((Math.pow(cvar,2) - 1)/(Math.pow(cvar,2) + 1)));
		probabilities[0] = lambdas[0] / (lambdas[1]+lambdas[0]);
		probabilities[1] = 1 - probabilities[0];
	}

	@Override
	public void setStdDeviation(double s) {
		double m = getMean();
		double cvar = s/m; 
		lambdas[0] = 1/m * (1 + Math.sqrt((Math.pow(cvar,2) - 1)/(Math.pow(cvar,2) + 1)));
		lambdas[1] = 1/m * (1 - Math.sqrt((Math.pow(cvar,2) - 1)/(Math.pow(cvar,2) + 1)));
		probabilities[0] = lambdas[0] / (lambdas[1]+lambdas[0]);
		probabilities[1] = 1 - probabilities[0];
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		//both calculations depend on the cvar, which change after the first calculation independent of the order
		throw new UnsupportedOperationException("only can set either one, not both");
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
				"\tlambdas: " + lambdas.toString() + "\n" +
				"\tprobabilities: " + probabilities.toString() + "\n";
	}
}

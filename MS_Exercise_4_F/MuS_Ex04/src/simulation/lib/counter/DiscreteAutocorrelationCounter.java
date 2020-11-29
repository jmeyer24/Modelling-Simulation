package simulation.lib.counter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class implements a discrete time autocorrelation counter
 */
public class DiscreteAutocorrelationCounter extends DiscreteCounter {
	/*
     * TODO Problem 4.1.1 - Implement this class according to the given class diagram!
     * Hint: see section 4.4 in course syllabus
     */

	private int maxLag;				
	// Es wird angenommen, dass maxLang für eine laufende Reihe ned geändert werden kann
	private int countFirst = 0;
	
	private double[] firstValues; 
	private double[] lastValues; 
	
	private double[] lagSqrSums;
		
	public DiscreteAutocorrelationCounter(String variable, int maxLag) {
		super(variable, "counter type: autocorrelation counter");
		setMaxLag(maxLag);
	}

	public DiscreteAutocorrelationCounter(String variable, String type, int maxLag) {
		super(variable, type);
		setMaxLag(maxLag);
	}
	
	/**
	 * Returns the maximum allowed lag for autocorrelation and autocovariance. 
	 */
	public int getMaxLag() {
		return maxLag;
	}
	
	/**
	 * Sets the maximum allowed lag for autocorrelation and autocovariance to the given value.
	 * @param new maxLag
	 */
	public void setMaxLag(int maxLag) {
		this.maxLag = maxLag;
		firstValues = new double[maxLag];
		lastValues = new double[maxLag];
		lagSqrSums = new double[maxLag+1];
	}
	
	/**
	 * 
	 */
	public void count(double x) {
		super.count(x);
		
		// Fill up firstValues
		if (countFirst < maxLag) {
			firstValues[countFirst] = x;
			countFirst++;
		}
		
//		System.out.println("fV ####################################");
//		for(double s : firstValues) {
//			System.out.println(s);
//		}
		
		// Update lagSqrSums
		lagSqrSums[0] = lagSqrSums[0] + x*x;
		for(int i = 1; i <= maxLag; i++) {
			lagSqrSums[i] = lagSqrSums[i] + x * lastValues[i-1];
		}
//		System.out.println("lSS ####################################");
//		for(double s : lagSqrSums) {
//			System.out.println(s);
//		}
		
		// Update lastValues
		for(int i = maxLag-1; i > 0; i--) {
			lastValues[i] = lastValues[i-1];
		}
		lastValues[0] = x;
		
//		System.out.println("lV ####################################");
//		for(double s : lastValues) {
//			System.out.println(s);
//		}
		
	}
	
	/**
	 * 
	 * @param lag 
	 * @return
	 */
	public double getAutoCovariance(int lag) {
		if(lag <= maxLag) {
			System.out.println("lag: " + lag);

			long n = super.getNumSamples();
			double mean = super.getMean();
			
			double sumToJ = 0; 
			for(int i = 0; i < lag; i++) {
				sumToJ += firstValues[i];
			}
			System.out.println("fV ####################################");
			for(double s : firstValues) {
				System.out.println(s);
			}
			System.out.println(sumToJ);
			
			double sumFromN_J = 0; 
			for(int i = 0; i < lag; i++) {
				sumFromN_J += lastValues[i];
			}
			System.out.println("lV ####################################");
			for(double s : lastValues) {
				System.out.println(s);
			}
			System.out.println(sumFromN_J);
			
			System.out.println(lagSqrSums[lag]);

			return (1/(n-lag)) * (lagSqrSums[lag] - mean * (2 * super.getSumPowerOne() - sumToJ - sumFromN_J)) + Math.pow(mean, 2);
		}
		else {
			return Double.POSITIVE_INFINITY;
//			throw new Exception("The given lag value is greater than the maximum allowed lag.");
		}
	}
	
	/**
	 * 
	 * @param lag
	 * @return
	 */
	public double getAutoCorrelation(int lag) {
		return getAutoCovariance(lag) / super.getVariance();
	}
	
	/**
	 * Resets the counter and all corresponding variables. 
	 */
	public void reset() {
		super.reset();
		maxLag = 0;
		countFirst = 0;
		firstValues = new double[0];
		lastValues = new double[0];
		lagSqrSums = new double[0];
	}
	
	/**
	 * @see Counter#report()
	 * TODO Uncomment this function if you have implemented the class!
	 */
	@Override
	public String report() {
		String out  = super.report();
		out += ("\n\tCorrelation/Covariance:\n");
		for(int i = 0; i <= (getNumSamples() < maxLag ? getNumSamples() : maxLag); i++){
			out += ("\t\tlag = " + i + "   " +
					"covariance = " + getAutoCovariance(i) + "   " +
					"correlation = " + getAutoCorrelation(i)+"\n");
		}
		return out;
	}
	/**
	 * @see Counter#csvReport(String)
	 * TODO Uncomment this function if you have implemented the class!
	 */
	@Override
	public void csvReport(String outputdir){
	    String content = "";
        for(int i = 0; i <= (getNumSamples() < maxLag ? getNumSamples() : maxLag); i++) {
            content += observedVariable + " (lag=" + i + ")" + ";" + getNumSamples() + ";" + getMean() + ";" +
                    getVariance() + ";" + getStdDeviation() + ";" + getCvar() + ";" + getMin() + ";" + getMax() + ";" +
                    getAutoCovariance(i) + ";" + getAutoCorrelation(i) + "\n";
        }
        String labels = "#counter ; numSamples ; MEAN; VAR; STD; CVAR; MIN; MAX; COV; CORR\n";
        writeCsv(outputdir, content, labels);
	}
}

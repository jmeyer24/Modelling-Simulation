package simulation.lib.counter;

import java.util.LinkedList;

/**
 * This class implements a discrete time autocorrelation counter
 */
public class DiscreteAutocorrelationCounter extends DiscreteCounter{
	private int maxLag;
	private LinkedList<Double> xList = new LinkedList<Double>();
	
	public DiscreteAutocorrelationCounter(String variable, int maxLag) {
		super(variable, "counter type: autocorrelation counter");
		this.maxLag = maxLag;
	}
	
	public DiscreteAutocorrelationCounter(String variable, String type, int maxLag) {
		super(variable, type);
		this.maxLag = maxLag;
	}
	
	public int getMaxLag() {
		return maxLag;
	}
	
	public void setMaxLag(int maxLag) {
		this.maxLag = maxLag;
	}
	
	public void count(double x) {
		super.count(x);
		xList.add(x);
	}
	
	public double getAutoCovariance(int lag) {
		int diff = (int)getNumSamples() - lag;
		double mean = getMean();
		double sum = 0.0;
		for(int i = 0; i < diff; i++) {
			sum += (xList.get(i)-mean) * (xList.get(i+lag)-mean);
		}
		return sum/diff;
	}
	
	public double getAutoCorrelation(int lag) {
		int num = (int)getNumSamples();
		return num > 1 & num > lag ? getAutoCovariance(lag) / (num/(num-1) * (getSumPowerTwo()/num - Math.pow(getMean(), 2))) : 0;
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

	public void reset() {
		super.reset();
		// with this new assignment the old list is still floating around, doesn't it?
		xList = new LinkedList<Double>();
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

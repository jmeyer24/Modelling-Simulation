package study;

import simulation.lib.counter.DiscreteAutocorrelationCounter;

/*
 * TODO Problem 4.1.2 - Implement this class
 * You can call your test from the main-method in SimulationStudy
 */

public class AutocorrelationTest {

    public static void testAutocorrelation() {
    	DiscreteAutocorrelationCounter dac = new DiscreteAutocorrelationCounter("time series","counter type: discrete autocorrelation counter",8);

    	double[][] series = new double[6][];
        series[0] = new double[] {2,2,2,2,2,2,2,2,2};
        series[1] = new double[] {2,2,-2,2,2,-2,2,2,-2};
        series[2] = new double[] {2,3,4,3,2,3,4,3,2};
        series[3] = new double[] {2,-4,-2,4,2,-4,-2,4,2};
        series[4] = new double[] {2,3,4,5,4,3,2,3,4};
        series[5] = new double[] {2,3,4,5,2,3,4,5,2};
        
        for(double[] serie : series) {
        	dac.reset();
        	for(double x : serie) {
        		dac.count(x);
        	}
        	System.out.println(dac.report());
        }
    }
}

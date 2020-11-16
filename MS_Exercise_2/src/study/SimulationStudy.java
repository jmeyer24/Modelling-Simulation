package study;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import simulation.lib.Simulator;
import simulation.lib.statistic.IStatisticObject;
import simulation.lib.counter.*;
import simulation.lib.histogram.*;
import simulation.lib.randVars.continous.*;
import simulation.lib.rng.*;

/**
 * Represents a simulation study. Contains diverse counters for statistics and
 * program/simulator parameters. Starts the simulation.
 */
public class SimulationStudy {
    /*
	 * TODO Problem 2.2.4 - configure program arguments here
	 * Here you can set the different parameters for your simulation
	 * Note: Units are real time units (seconds).
	 * They get converted to simulation time units in setSimulationParameters.
	 */
	protected long cInterArrivalTime = 10;
	protected long cServiceTime = 11;
	protected long cSimulationTime = 10000;
	
	// TODO
	protected int numIntervals = 10;
	protected double suUpperBound = 1000000;
	protected double suLowerBound = 0;
	protected double qoUpperBound = Math.ceil(((double)cInterArrivalTime/(double)cServiceTime)*(cSimulationTime/100)*(cServiceTime-cInterArrivalTime));//(double)cSimulationTime;
	protected double qoLowerBound = 0;
	protected double tUpperBound = 2*cSimulationTime;
	protected double tLowerBound = 0;
	
	/**
	 * Main method
	 */
	
	public static void main(String[] args) {
		/*
		 * create simulation object
		 */
		Simulator sim = new Simulator();
		/*
		 * run simulation
		 */
		sim.start();
		/*
		 * print out report
		 */
		sim.report();
		//TODO
		/*
		 * test random variable functions
		 */
		StdRNG rng = new StdRNG();
		Uniform u = new Uniform(rng, 0, 5); //a,b
		Exponential e = new Exponential(rng, 1); //lambda
		ErlangK k = new ErlangK(rng, 1, 5); //lambda, k
		
		double[] l = {1,2};
		double[] p = {0.4,0.6};
		
		HyperExponential h = new HyperExponential(rng, l, p); //lambdas, probabilities
		RandVarTest.testRandVars(u, e, k, h);
	}

	// PARAMETERS
	/**
	 * Turn on/off debug report in console.
	 */
	protected boolean isDebugReport = true;

	/**
	 * Turn on/off report in csv-files.
	 */
	protected boolean isCsvReport = true;

	/**
	 * Simulation time.
	 */
	public long simulationTime;


	/**
	 * inter arrival time of customers (in simulation time).
	 */
	public long interArrivalTime;

	/**
	 * service time of a customer (in simulation time).
	 */
	public long serviceTime;

	// STATISTICS
	/**
	 * Map that contains all statistical relevant object such as counters and
	 * histograms.
	 */
	public HashMap<String, IStatisticObject> statisticObjects;

	/**
	 * Maximum queue size.
	 */
	public long maxQS;

	/**
	 * Minimum queue size.
	 */
	public long minQS;

	/*
	 * TODO Problem 2.2 - naming your statistic objects
	 * Here you have to set some names (as Sting objects) for all your statistic objects
	 * They are later used to retrieve them from the dictionary
	 */
	// Example for discrete counter which measures customer waiting time:
	public String dcWaitingTime = "discreteCounterWaitingTime";
	public String dcServiceTime = "discreteCounterServiceTime";
	public String ccQueueOccupancy = "continuousCounterQueueOccupancy";
	public String ccServerUtilization = "continuousCounterServerUtilization";

	public String dhWaitingTime = "discreteHistogramWaitingTime";
	public String dhServiceTime = "discreteHistogramServiceTime";
	public String chQueueOccupancy = "continuousHistogramQueueOccupancy";
	public String chServerUtilization = "continuousHistogramServerUtilization";
	
	
	//TODO 2.3.3
	public String dcUniform = "discreteCounterUniform";
	public String dcExponential = "discreteCounterExponential";
	public String dcErlangK = "discreteCounterErlangK";
	public String dcHyperExponential = "discreteCounterHyperExponential";
	
	private Simulator simulator;

	/**
	 * Constructor
	 * @param sim Simulator instance.
	 */
	public SimulationStudy(Simulator sim) {
		simulator = sim;
		simulator.setSimTimeInRealTime(1000);
		setSimulationParameters();
		initStatistics();
	}

	/**
	 * Sets simulation parameters, converts real time to simulation time if
	 * needed.
	 */
	private void setSimulationParameters() {
		interArrivalTime = simulator.realTimeToSimTime(cInterArrivalTime);
		serviceTime = simulator.realTimeToSimTime(cServiceTime);
		simulationTime = simulator.realTimeToSimTime(cSimulationTime);
	}

	/**
	 * Initializes statistic objects. Adds them into Hashmap.
	 */
	private void initStatistics() {
		maxQS = Long.MIN_VALUE;
		minQS = Long.MAX_VALUE;

		statisticObjects = new HashMap<>();

        /*
          TODO Problem 2.2 - add statistic objects (counters) to the HashMap
          Here you have to create your counters and add them to the statisticObjects HashMap
          Use the name which you specified above as the key
         */
		statisticObjects.put(dcWaitingTime, new DiscreteCounter("waiting_time_customer"));
		statisticObjects.put(dcServiceTime, new DiscreteCounter("service_time_customer"));
		//statisticObjects.put(ccQueueOccupancy, new DiscreteCounter("queue_occupancy_time"));
		//statisticObjects.put(ccServerUtilization, new DiscreteCounter("server_utilization_time"));
		statisticObjects.put(ccQueueOccupancy, new ContinuousCounter("queue_occupancy_time",simulator));
		statisticObjects.put(ccServerUtilization, new ContinuousCounter("server_utilization_time",simulator));

		statisticObjects.put(dhWaitingTime, new DiscreteHistogram("waiting_time_customer",numIntervals,tLowerBound,tUpperBound));
		statisticObjects.put(dhServiceTime, new DiscreteHistogram("service_time_customer",numIntervals,tLowerBound,tUpperBound));
		statisticObjects.put(chQueueOccupancy, new ContinuousHistogram("queue_occupancy_time",numIntervals,(int)qoLowerBound,(int)qoUpperBound,simulator));
		statisticObjects.put(chServerUtilization, new ContinuousHistogram("server_utilization_time",numIntervals,(int)suLowerBound,(int)suUpperBound,simulator));
		
		statisticObjects.put(dcUniform, new DiscreteCounter("uniform"));
		statisticObjects.put(dcExponential, new DiscreteCounter("exponential"));
		statisticObjects.put(dcErlangK, new DiscreteCounter("erlangk"));
		statisticObjects.put(dcHyperExponential, new DiscreteCounter("hyperexponential"));
	}


    /**
     * Report results. Print to console if isDebugReport = true. Print to csv
     * files if isCsvReport = true. Note: Histogramms are only printed to csv
     * files.
     */
    public void report() {
        String sd = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date(System.currentTimeMillis()));
        String destination = sd + this.getClass().getSimpleName();

        if (isCsvReport) {
            File file = new File(destination);
            file.mkdir();
            for (IStatisticObject so : statisticObjects.values()) {
                so.csvReport(destination);
            }
        }
        if (isDebugReport) {
            System.out.println("E[interArrivalTime] = " + simulator.simTimeToRealTime(interArrivalTime) + "\n"
                    + "E[serviceTime] = " + simulator.simTimeToRealTime(serviceTime) + "\n"
                    + "server utilization: "+ ((double) serviceTime / (double) interArrivalTime) + "\n");

            for (IStatisticObject so : statisticObjects.values()) {
                System.out.println(so.report());
            }

            System.out.println("minimum queue size: " + minQS + "\n" + "maximum queue size: " + maxQS);
        }
    }
}

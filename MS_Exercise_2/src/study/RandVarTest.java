package study;
import simulation.lib.counter.DiscreteCounter;
import simulation.lib.randVars.continous.*;
import simulation.lib.rng.*;

/*
 * TODO Problem 2.3.3 and 2.3.4[Bonus] - implement this class
 * You can call your test from the main-method in SimulationStudy
 */
public class RandVarTest {
	private static double mean = 1;
	private static double cvar = 2;
	private static int n = 1000000;
	
	private static DiscreteCounter u = new DiscreteCounter("uniform");
	private static DiscreteCounter e = new DiscreteCounter("exponential");
	private static DiscreteCounter k = new DiscreteCounter("erlangk");
	private static DiscreteCounter h = new DiscreteCounter("hyperexponential");
	
    public static void main(String[] args) {
    	StdRNG rng = new StdRNG(1000);
    	
    	Uniform uv = new Uniform(rng, 0, 5);
    	Exponential ev = new Exponential(rng, 1);
    	ErlangK kv = new ErlangK(rng, 1 , 2);
    	double[] prob = new double[] {.5,.5};
    	double[] lamb = new double[] {1,2};
    	HyperExponential hv = new HyperExponential(rng, lamb, prob);

    	uv.setMean(mean);
    	uv.setCvar(cvar);
    	
    	ev.setMean(mean);
    	ev.setCvar(cvar);
    	
    	kv.setMean(mean);
    	kv.setCvar(cvar);
    	
    	hv.setMean(mean);
    	hv.setCvar(cvar);
    	
    	System.out.println(uv.toString());
    	System.out.println(ev.toString());
    	System.out.println(kv.toString());
    	System.out.println(hv.toString());
    	
    	
    	for(int i=0;i<n;i++) {
    		u.count(uv.getRV());
    		e.count(ev.getRV());
    		k.count(kv.getRV());
    		h.count(hv.getRV());
    	}
    	
    	System.out.println(u.report());
    	System.out.println(e.report());
    	System.out.println(k.report());
    	System.out.println(h.report());
    }
    
}

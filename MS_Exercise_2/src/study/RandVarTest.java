package study;
import simulation.lib.randVars.continous.*;

/*
 * TODO Problem 2.3.3 and 2.3.4[Bonus] - implement this class
 * You can call your test from the main-method in SimulationStudy
 */
public class RandVarTest {
	private static double mean = 1;
	private static double cvar = .1;
	private static int n = 1000000;
	
	//? i dont know
	
    public static void testRandVars(Uniform u, Exponential e, ErlangK k, HyperExponential h) {
        // TODO Auto-generated method stub
    	u.setMean(mean);
    	u.setCvar(cvar);
    	
    	e.setMean(mean);
    	e.setCvar(cvar);
    	
    	k.setMean(mean);
    	k.setCvar(cvar);
    	
    	h.setMean(mean);
    	h.setCvar(cvar);
    }
}

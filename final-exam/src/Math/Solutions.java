/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

/**
 *
 * @author htrefftz
 */
public class Solutions {
    // Number of solutions
    int numSolutions;
    // First solution
    double t1;
    // Second solution
    double t2;

    /**
     * Constructor
     * @param numSolutions Number of solutions. May be 0, 1 or 2
     * @param t1 first solution
     * @param t2 second solution
     */
    public Solutions(int numSolutions, double t1, double t2) {
        this.numSolutions = numSolutions;
        this.t1 = t1;
        this.t2 = t2;
    }

    public int getNumSolutions() {
        return numSolutions;
    }

    public double getT1() {
        return t1;
    }

    public double getT2() {
        return t2;
    }   
    
    @Override
    public String toString() {
        return "Solutions{" + "numSolutions=" + numSolutions + ", t1=" + t1 + ", t2=" + t2 + '}';
    }
    
}

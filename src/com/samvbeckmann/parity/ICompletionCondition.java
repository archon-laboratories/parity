package com.samvbeckmann.parity;

/**
 * Determines if the simulation has reached its end.
 * An implementation needs to be chosen for each simulation.
 * A sample implementation can be found at {@link BasicCompletionCondition}
 *
 * @author Sam Beckmann
 */
public interface ICompletionCondition
{
    /**
     * Determines whether or not the simulation has reached its end
     * @return
     */
    boolean simulationComplete(Population p);
}

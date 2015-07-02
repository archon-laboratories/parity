package com.samvbeckmann.parity.core;

import com.samvbeckmann.parity.demoProgram.BasicCompletionCondition;

/**
 * Determines if the simulation has reached its end.
 * An implementation needs to be chosen for each simulation.
 * A sample implementation can be found at {@link BasicCompletionCondition}
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public interface ICompletionCondition
{
    /**
     * Determines whether or not the simulation has reached its end
     *
     * @return true if simulation should end, otherwise false
     */
    boolean simulationComplete(Population population);
}

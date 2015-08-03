package com.samvbeckmann.parity.core;

/**
 * Determines if the simulation has reached its end.
 * An implementation needs to be chosen for each simulation.
 * A sample implementation can be found at our extension.
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

    /**
     * Gets a human readable name for display to users in the GUI.
     * Called once, upon initialization of the GUI.
     *
     * @return Human readable name for the CompletionCondition.
     */
    String getName();
}

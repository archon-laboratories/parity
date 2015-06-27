package com.samvbeckmann.parity.basicProgram;

import com.samvbeckmann.parity.ICompletionCondition;
import com.samvbeckmann.parity.Population;

/**
 * Sample implementation of {@link ICompletionCondition}
 * Use / extend this or make your own.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class BasicCompletionCondition implements ICompletionCondition
{
    /**
     * @param population Population being tested
     * @return Whether or not the simulation is complete
     */
    public boolean simulationComplete(Population population)
    {
        double netOpinion = population.getAverageOpinion();

        return netOpinion <= .05 || netOpinion >= .95 || population.getTimestep() > 100;
    }
}

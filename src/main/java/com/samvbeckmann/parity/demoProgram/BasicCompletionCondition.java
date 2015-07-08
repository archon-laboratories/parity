package com.samvbeckmann.parity.demoProgram;

import com.samvbeckmann.parity.core.ICompletionCondition;
import com.samvbeckmann.parity.core.Population;

/**
 * Sample implementation of {@link ICompletionCondition}
 * Use / extend this or make your own.
 * @deprecated
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

        return netOpinion <= .05 || netOpinion >= .95 || population.getTimestep() > 99;
    }

    @Override
    public String getName()
    {
        return "Basic Completion Condition";
    }
}

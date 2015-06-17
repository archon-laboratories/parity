package com.samvbeckmann.parity;

/**
 * Sample implementation of {@link ICompletionCondition}
 * Use / extend this or make your own.
 *
 * @author Sam Beckmann
 */
public class BasicCompletionCondition implements ICompletionCondition
{
    public boolean simulationComplete(Population population)
    {
        double netOpinion = population.getAverageOpinion();

        return netOpinion < .01 || netOpinion > .99;
    }
}

package com.samvbeckmann.parity;

import java.util.List;

/**
 * Defines a population, consisting of multiple {@link Community} objects.
 *
 * @author Sam Beckmann
 */
public class Population
{
    public List<Community> communities;

    public double getAverageOpinion()
    {
        double total = 0;
        for (Community community : communities)
        {
            List<Double> opinions = community.getOpinions();
            for (double opinion : opinions)
            {
                total += opinion;
            }
        }
        return total / this.getPopulationSize();
    }

    public int getPopulationSize()
    {
        int total = 0;
        for (Community community : communities)
        {
            total += community.getCommunitySize();
        }
        return total;
    }

}

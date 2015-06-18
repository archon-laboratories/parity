package com.samvbeckmann.parity;

/**
 * Defines a population, consisting of multiple {@link Community} objects.
 *
 * @author Sam Beckmann
 */
public class Population
{
    public Community[] communities;

    public Population(Community[] communities)
    {
        this.communities = communities;
    }
    
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

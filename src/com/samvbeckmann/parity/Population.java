package com.samvbeckmann.parity;

import java.util.Random;

/**
 * Defines a population, consisting of multiple {@link Community} objects.
 *
 * @author Sam Beckmann
 */
public class Population
{
    public static Random rnd;
    public Community[] communities;

    public Population()
    {
        rnd = new Random();
    }

    public Population(Community[] communities)
    {
        rnd = new Random();
        this.communities = communities;
    }

    public double getAverageOpinion()
    {
        double total = 0;
        for (Community community : communities)
        {
            double[] opinions = community.getOpinions();
            for (double opinion : opinions)
                total += opinion;
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

package com.samvbeckmann.parity;

import java.util.Random;

/**
 * Defines a population, consisting of multiple {@link Community} objects.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class Population
{
    /**
     * The global static random generator for the population.
     */
    public static final Random rnd = new Random();

    /**
     * The communities in the population
     */
    private Community[] communities;

    /**
     *
     * @param communities Communities in the population
     */
    public Population(Community[] communities)
    {
        this.communities = communities;
    }

    /**
     * @return Communities in the population
     */
    public Community[] getCommunities()
    {
        return communities;
    }

    /**
     * @return The average opinion of the population
     */
    public double getAverageOpinion()
    {
        double total = 0;
        for (Community community : communities)
            total += community.getAverageOpinion();
        return total / communities.length;
    }

    /**
     * @return The size of the population in terms of agents.
     */
    public int getPopulationSize()
    {
        int total = 0;
        for (Community community : communities)
        {
            total += community.communitySize();
        }
        return total;
    }

}

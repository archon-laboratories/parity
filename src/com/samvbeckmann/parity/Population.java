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
     * The current time step of the program (the interaction handler is run once per timestep).
     */
    private int timestep;

    /**
     * The communities in the population
     */
    private Community[] communities;

    /**
     * The list of unique two-way connections in the population
     */
    private Connection[] connections;

    /**
     * @param communities Communities in the population
     */
    public Population(Community[] communities)
    {
        timestep = 0;
        this.communities = communities;
        connections = null;
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
            for (double opinion : community.getOpinions())
                total += opinion;

        return total / ((double) getPopulationSize());
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

    /**
     * @return The timestep of the current trial
     */
    public int getTimestep()
    {
        return timestep;
    }

    /**
     * The method parity will use to increment the timestep
     */
    void incrementTimestep() {
        timestep++;
    }

    /**
     * Returns all of the unique connections in the population
     */
    public Connection[] getConnections() {
        return connections;
    }

    /**
     * Sets the connections
     */
    void setConnections(Connection[] connections)
    {
        this.connections = connections;
    }
}

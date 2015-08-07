package com.samvbeckmann.parity.core;

import com.samvbeckmann.parity.utilities.IndexHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a Community, which consists of {@link AbstractAgent} and {@link Connection}
 * to other communities
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class Community
{
    /**
     * The {@link Connection} list of potential neighbours.
     */
    private Connection[] neighbours;

    /**
     * The {@link AbstractAgent}s living in the community
     */
    private AbstractAgent[] agents;

    /**
     * A list of indices for selecting agents in the interaction handler
     */
    private List<Integer> availability;

    /**
     * @return The community's neighbours
     */
    public Connection[] getNeighbours()
    {
        return neighbours;
    }

    /**
     * Sets the neighbours of the community
     *
     * @param neighbours The {@link Connection}s of the community
     */
    void setNeighbours(Connection[] neighbours)
    {
        this.neighbours = neighbours;
    }

    /**
     * Checks a community to see if it's in the array of neighbours
     *
     * @param community The community being looked for
     * @return The Connection to that community, or null if there is none
     */
    public Connection getConnectionByCommunity(Community community)
    {
        for (Connection neighbour : neighbours)
        {
            if (neighbour.getNeighbourCommunity().equals(community))
            {
                return neighbour;
            }
        }

        return null;
    }

    /**
     * @return The {@link AbstractAgent}s living in the community
     */
    public AbstractAgent[] getAgents()
    {
        return agents;
    }

    /**
     * Sets the {@link AbstractAgent}s living in the community
     *
     * @param agents The {@link AbstractAgent}s living in the community
     */
    void setAgents(AbstractAgent[] agents)
    {
        this.agents = agents;
    }

    /**
     * @return The number of agents in the community
     */
    public int communitySize()
    {
        return agents.length;
    }

    /**
     * Marks an agent as going to interact in the next timestep
     *
     * @param index The index of the agent to be marked
     * @return The agent at that index
     */
    public AbstractAgent markAgentForInteraction(Integer index)
    {
        if (availability.contains(index))
        {
            availability.remove(index);
            return agents[index];
        } else
        {
            return null;
        }
    }

    /**
     * Marks a random agent for interaction
     *
     * @return The random agent marked for interaction
     */
    public AbstractAgent markRandomAgentForInteraction()
    {
        int index = Population.rnd.nextInt(availability.size());
        return markAgentForInteraction(availability.get(index));
    }

    /**
     * Makes all agents available for interaction at the next timestep.
     * Called once per timestep, after all interactions have been completed.
     */
    public void resetAvailability()
    {
        availability = IndexHelper.generateIndices(agents.length);
    }

    /**
     * @return The number of agents that are still eligible to interact
     */
    public int getNumberAvailable()
    {
        return availability.size();
    }

    /**
     * @return The average opinion of the community
     */
    public double getAverageOpinion()
    {
        double total = 0;
        for (AbstractAgent agent : agents)
        {
            total += agent.getOpinion();
        }
        return total / ((double) agents.length);
    }

    /**
     * @return The opinions of the agents
     */
    public double[] getOpinions()
    {
        double[] opinions = new double[agents.length];
        for (int i = 0; i < agents.length; i++)
        {
            opinions[i] = agents[i].getOpinion();
        }
        return opinions;
    }

    /**
     * Make sure that things are instantiated, even if they do not do anything/exist.
     */
    public Community() {
        neighbours = new Connection[0];
        agents = new AbstractAgent[0];
        availability = new ArrayList<>();
    }
}

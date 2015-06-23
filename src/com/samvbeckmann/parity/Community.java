package com.samvbeckmann.parity;

import com.samvbeckmann.parity.utilities.IndexHelper;

import java.util.List;
import java.util.Random;

/**
 * Defines a Community, which consists of {@link AbstractAgent} and {@link OneWayConnection}
 * to other communities
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class Community
{
    /**
     * The {@link OneWayConnection} list of potential neighbours.
     */
    private OneWayConnection[] neighbours;

    /**
     * The {@link AbstractAgent}s living in the community
     */
    private AbstractAgent[] agents;

    /**
     * A list of indices for selecting agents in the interaction handler
     */
    private List<Integer> availability;

    // TODO make constructor

    /**
     * @return The community's neighbours
     */
    public OneWayConnection[] getNeighbours()
    {
        return neighbours;
    }

    /**
     * Checks a community to see if it's in the array of neighbors
     *
     * @param community The community being looked for
     * @return The OneWayConnection to that community, or null if there is none
     */
    public OneWayConnection getConnectionByCommunity(Community community)
    {
        for (OneWayConnection neighbour : neighbours)
        {
            if (neighbour.getCommunity().equals(community))
                return neighbour;
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
     * @param agents The {@link AbstractAgent}s living in the community
     */
    void setAgents(AbstractAgent[] agents)
    {
        this.agents = agents;
    }

    /**
     * Sets the neighbours of the community
     * @param neighbours The {@link OneWayConnection}s of the community
     */
    void setNeighbours(OneWayConnection [] neighbours) { this.neighbours = neighbours;}

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
    public AbstractAgent markAgentForInteraction(int index)
    {
        if (availability.contains(index))
        {
            //noinspection SuspiciousMethodCalls
            availability.remove((Object) index);
            return agents[index];
        } else return null;
    }

    /**
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
            total += agent.getOpinion();
        return total / agents.length;
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
}

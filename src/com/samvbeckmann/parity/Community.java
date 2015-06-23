package com.samvbeckmann.parity;

import com.samvbeckmann.parity.utilities.IndexHelper;

import java.util.List;
import java.util.Random;

/**
 * Defines a Community, which consists of {@link AbstractAgent} and {@link OneWayConnection}
 * to other communities
 *
 * @author Sam Beckmann
 */
public class Community
{
    private OneWayConnection[] neighbours;
    private AbstractAgent[] agents;
    private List<Integer> availability;

    // TODO make constructor

    public OneWayConnection[] getNeighbours()
    {
        return neighbours;
    }

    public OneWayConnection getConnectionByCommunity(Community community)
    {
        for (OneWayConnection neighbour : neighbours)
        {
            if (neighbour.getCommunity().equals(community))
                return neighbour;
        }

        return null;
    }

    public AbstractAgent[] getAgents()
    {
        return agents;
    }

    void setAgents(AbstractAgent[] agents)
    {
        this.agents = agents;
    }

    void setNeighbours(OneWayConnection [] neighbours) { this.neighbours = neighbours;}

    public int getCommunitySize()
    {
        return agents.length;
    }

    public AbstractAgent markAgentForInteraction(int index)
    {
        if (availability.contains(index))
        {
            //noinspection SuspiciousMethodCalls
            availability.remove((Object) index);
            return agents[index];
        } else return null;
    }

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

    public int getNumberAvailable()
    {
        return availability.size();
    }

    public double getAverageOpinion()
    {
        double total = 0;
        for (AbstractAgent agent : agents)
            total += agent.getOpinion();
        return total / agents.length;
    }

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

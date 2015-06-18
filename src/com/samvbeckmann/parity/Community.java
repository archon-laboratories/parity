package com.samvbeckmann.parity;

import com.samvbeckmann.parity.utilities.IndexHelper;

import java.util.ArrayList;
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
    private int xPos;
    private int yPos;

    private List<OneWayConnection> neighbours;
    private AbstractAgent[] agents;
    private List<Integer> availability;
    private Random rnd = new Random();

    // TODO make constructor

    public List<OneWayConnection> getNeighbours()
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

    public int getX()
    {
        return xPos;
    }

    public int getY()
    {
        return yPos;
    }

    void setX(int xPos)
    {
        this.xPos = xPos;
    }

    void setY(int yPos)
    {
        this.yPos = yPos;
    }

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
        int index = rnd.nextInt(availability.size());
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
        {
            total += agent.getOpinion();
        }
        return total / agents.length;
    }

    public List<Double> getOpinions()
    {
        List<Double> opinions = new ArrayList<>();
        for (AbstractAgent agent : agents)
        {
            opinions.add(agent.getOpinion());
        }
        return opinions;
    }
}

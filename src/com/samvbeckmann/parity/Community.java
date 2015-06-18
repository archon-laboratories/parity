package com.samvbeckmann.parity;

import com.samvbeckmann.parity.utilities.IndexHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Defines a Community, which consists of {@link IAgent} and {@link OneWayConnection}
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
        return agents.size();
    }

    public IAgent markAgentForInteraction(IAgent agent)
    {
        int index = agents.indexOf(agent);
        if (availability.contains(index))
        {
            availability.remove((Object) index);
            return agent;
        } else return null;

    }

    public IAgent markAgentForInteraction(int index)
    {
        IAgent agent = agents.get(index);
        return markAgentForInteraction(agent);
    }

    public IAgent markRandomAgentForInteraction()
    {
        int index = rnd.nextInt(availability.size());
        return markAgentForInteraction(availability.get(index));
    }

    public void resetAvailability()
    {
        availability = IndexHelper.generateIndices(agents.size());
    }

    public int getNumberAvailable()
    {
        return availability.size();
    }

    public double getAverageOpinion()
    {
        double total = 0;
        for (IAgent agent : agents)
        {
            total += agent.getOpinion();
        }
        return total / agents.size();
    }

    public List<Double> getOpinions()
    {
        List<Double> opinions = new ArrayList<>();
        for (IAgent agent : agents)
        {
            opinions.add(agent.getOpinion());
        }
        return opinions;
    }
}

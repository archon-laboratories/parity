package com.samvbeckmann.parity;

import com.samvbeckmann.parity.utilities.IndexHelper;

import java.util.List;
import java.util.Random;

/**
 * Defines a Community, which consists of agents and connections
 * to other communities
 *
 * @author Sam Beckmann
 */
public class Community
{
    private List<OneWayConnection> neighbours;
    private List<IAgent> agents;
    private List<Integer> availability;
    private Random rnd = new Random();


    public List<OneWayConnection> getNeighbours()
    {
        return neighbours;
    }

    public OneWayConnection getConnectionByCommunity(Community community)
    {
        for(OneWayConnection neighbour : neighbours)
        {
            if (neighbour.getCommunity().equals(community))
                return neighbour;
        }

        return null;
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
}

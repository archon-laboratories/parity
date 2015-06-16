package com.samvbeckmann.parity;

import java.util.List;

/**
 * parity, class made on 6/15/2015.
 *
 * @author Sam Beckmann
 */
public class Community
{
    private List<OneWayConnection> neighbours;
    private List<IAgent> agents;

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
}

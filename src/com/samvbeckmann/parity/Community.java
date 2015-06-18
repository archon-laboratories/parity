package com.samvbeckmann.parity;

import java.util.List;

/**
 * parity, class made on 6/15/2015.
 *
 * @author Sam Beckmann
 */
public class Community
{
    private int xPos;
    private int yPos;

    private List<OneWayConnection> neighbours;
    private AbstractAgent[] agents;

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
}

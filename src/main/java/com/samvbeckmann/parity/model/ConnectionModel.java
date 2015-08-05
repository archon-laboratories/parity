package com.samvbeckmann.parity.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * parity, class made on 2015-08-04
 *
 * @author Sam Beckmann
 */
public class ConnectionModel
{
    private CommunityModel community;
    private IntegerProperty numConnections;

    public ConnectionModel(CommunityModel community, int numConnections)
    {
        this.community = community;
        this.numConnections = new SimpleIntegerProperty(numConnections);
    }

    public CommunityModel getCommunity()
    {
        return community;
    }

    public void setCommunity(CommunityModel community)
    {
        this.community = community;
    }

    public int getNumConnections()
    {
        return numConnections.get();
    }

    public IntegerProperty numConnectionsProperty()
    {
        return numConnections;
    }

    public void setNumConnections(int numConnections)
    {
        this.numConnections.set(numConnections);
    }
}

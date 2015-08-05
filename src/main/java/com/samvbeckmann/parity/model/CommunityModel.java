package com.samvbeckmann.parity.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model Class for a Community
 *
 * @author Sam Beckmann
 */
public class CommunityModel
{
    ObservableList<AgentModel> agents;
    ObservableList<ConnectionModel> connections;
    CommunityNode node;
    
    public CommunityModel()
    {
        this.agents = FXCollections.observableArrayList();
        this.connections = FXCollections.observableArrayList();
    }

    public CommunityModel(ObservableList<AgentModel> agents)
    {
        this.agents = agents;
        this.connections = FXCollections.observableArrayList();
    }

    public ObservableList<AgentModel> getAgents()
    {
        return agents;
    }

    public void setAgents(ObservableList<AgentModel> agents)
    {
        this.agents = agents;
    }

    public CommunityNode getNode()
    {
        return node;
    }

    public void setNode(CommunityNode node)
    {
        this.node = node;
    }

    public ObservableList<ConnectionModel> getConnections()
    {
        return connections;
    }

    public void setConnections(ObservableList<ConnectionModel> connections)
    {
        this.connections = connections;
    }

    /**
     * Tests to see if this has a connection to the given CommunityModel.
     *
     * @param otherCommunity Community to test for connection
     * @return The connection, if it exists
     */
    public ConnectionModel getConnectionTo(CommunityModel otherCommunity)
    {
//        if (connections != null)
//        {
            for (ConnectionModel connection : connections)
            {
                if (connection.getCommunity().equals(otherCommunity))
                    return connection;
            }
//        }
        return null;
    }
}

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
    CommunityNode node;
    
    public CommunityModel()
    {
        this.agents = FXCollections.observableArrayList();
    }

    public CommunityModel(ObservableList<AgentModel> agents)
    {
        this.agents = agents;
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
}

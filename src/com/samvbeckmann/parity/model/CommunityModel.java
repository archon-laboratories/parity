package com.samvbeckmann.parity.model;

import javafx.collections.ObservableList;

/**
 * Model Class for a Community
 *
 * @author Sam Beckmann
 */
public class CommunityModel
{
    ObservableList<AgentModel> agents;

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
}

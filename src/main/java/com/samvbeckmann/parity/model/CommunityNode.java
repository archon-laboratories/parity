package com.samvbeckmann.parity.model;

import com.samvbeckmann.parity.ParityRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Defines a community in the interface that contains a communityModel
 * TODO: Separation of Data and Gui
 *
 * @author Sam Beckmann
 */
public class CommunityNode extends Circle
{
    private CommunityModel community = new CommunityModel();

    public CommunityNode(CommunityModel community, double radius)
    {
        super(radius);
        this.community = community;
    }

    public CommunityNode(double radius, Paint fill)
    {
        super(radius, fill);
    }

//    public CommunityNode()
//    {
//    }

    public CommunityNode(double centerX, double centerY, double radius)
    {
        super(centerX, centerY, radius);
        ObservableList<AgentModel> testAgents = FXCollections.observableArrayList();
        testAgents.add(new AgentModel(ParityRegistry.getAgents().get(0), 0.5));
        community = new CommunityModel(testAgents);
    }

    public CommunityNode(double centerX, double centerY, double radius, Paint fill)
    {
        super(centerX, centerY, radius, fill);
    }

    public CommunityModel getCommunity()
    {
        return community;
    }

    public void setCommunity(CommunityModel community)
    {
        this.community = community;
    }
}

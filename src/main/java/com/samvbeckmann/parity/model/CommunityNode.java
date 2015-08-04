package com.samvbeckmann.parity.model;

import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.view.CommunityNodeController;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Defines a community in the interface that contains a communityModel
 *
 * @author Sam Beckmann
 */
public class CommunityNode extends Circle
{
    private CommunityModel community = new CommunityModel();

    public CommunityNode(double centerX, double centerY, double radius, Paint fill, MainApp mainApp)
    {
        this(centerX, centerY, radius, fill, new CommunityModel(), mainApp);
    }

    public CommunityNode(double centerX,
                         double centerY,
                         double radius,
                         Paint fill,
                         CommunityModel community,
                         MainApp mainApp)
    {
        super(centerX, centerY, radius, fill);
        CommunityNodeController.configureHandlers(this, mainApp);
        this.community = community;
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

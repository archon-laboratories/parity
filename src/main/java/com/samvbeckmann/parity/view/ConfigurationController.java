package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.model.CommunityNode;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * Controller for the population builder and configuration settings.
 *
 * @author Sam Beckmann & Nate Beckemeyer
 */
public class ConfigurationController
{
    private MainApp mainApp;
    private AnchorPane populationPane;

    public ConfigurationController()
    {
        // NOOP
    }

    @FXML
    private void initialize()
    {
        // NOOP
    }

    @FXML
    public void handleAddCommunity()
    {
        CommunityNode newCommunity = new CommunityNode(populationPane.getWidth() / 2, populationPane.getHeight() / 2,
                20, Color.DEEPSKYBLUE, mainApp);
        populationPane.getChildren().add(newCommunity);
    }

    @FXML
    public void handleRemoveCommunity()
    {
        // NOOP
    }


    public void setMainAppAndPane(MainApp mainApp, AnchorPane populationPane)
    {
        this.mainApp = mainApp;
        this.populationPane = populationPane;
    }

}

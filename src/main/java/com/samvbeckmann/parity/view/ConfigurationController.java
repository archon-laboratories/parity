package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.ParityRegistry;
import com.samvbeckmann.parity.ReflectionWrapper;
import com.samvbeckmann.parity.model.CommunityNode;
import com.samvbeckmann.parity.reference.Messages;
import com.samvbeckmann.parity.reference.Names;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * Controller for the population builder and configuration settings.
 *
 * @author Sam Beckmann & Nate Beckemeyer
 */
public class ConfigurationController
{
    @FXML
    private ComboBox<ReflectionWrapper> completionCondition;
    @FXML
    private ComboBox<ReflectionWrapper> interactionHandler;
    @FXML
    private TextField numIterations;

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
        CommunityNode activeNode = mainApp.getActiveCommunity().getNode();
        populationPane.getChildren().remove(activeNode);
    }

    public String getActiveCompletionCondition()
    {
        return completionCondition.getValue().getClasspath();
    }

    public int getNumIterations()
    {
        int number = -1;
        try
        {
            number = Integer.parseInt(numIterations.getText());
            if (number <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle(Messages.Errors.INVALID_FIELDS);
            alert.setHeaderText(Messages.Errors.INVALID_FIELDS_HEADER);
            alert.setContentText(Messages.Errors.INVALID_NUM_TRIALS);

            alert.showAndWait();
        }
        return number;
    }

    public String getActiveInteractionHandler()
    {
        return interactionHandler.getValue().getClasspath();
    }


    public void setMainAppAndPane(MainApp mainApp, AnchorPane populationPane)
    {
        this.mainApp = mainApp;
        this.populationPane = populationPane;
    }

    public void populateComboBoxes()
    {
        completionCondition.setItems(ParityRegistry.getCompletionConditions());
        interactionHandler.setItems(ParityRegistry.getInteractionHandlers());
        numIterations.setText(Names.EMPTY_STRING);
    }
}

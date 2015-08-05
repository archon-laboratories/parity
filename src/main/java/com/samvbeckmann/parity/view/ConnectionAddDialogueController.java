package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.model.CommunityModel;
import com.samvbeckmann.parity.model.ConnectionModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for the dialogue box to add/edit connections.
 *
 * @author Sam Beckmann & Nate Beckemeyer
 */
public class ConnectionAddDialogueController
{
    @FXML
    private TextField numConnectionsField;

    private CommunityModel activeCommunity;
    private CommunityModel passiveCommunity;
    private boolean isEditing;
    private Stage dialogueStage;

    @FXML
    private void initialize()
    {
        /* NOOP */
    }

    public void setDialogueStage(Stage dialogueStage)
    {
        this.dialogueStage = dialogueStage;
    }

    public void setConnections(CommunityModel activeCommunity,
                               CommunityModel passiveCommunity)
    {
        this.activeCommunity = activeCommunity;
        this.passiveCommunity = passiveCommunity;
        ConnectionModel connection = this.activeCommunity.getConnectionTo(passiveCommunity);
        isEditing = connection != null;
        if (isEditing)
        {
            this.numConnectionsField.setText(Integer.toString(connection.getNumConnections()));
        }
    }

    @FXML
    private void handleAdd()
    {
        if (isInputValid())
        {
            int numConnections = Integer.parseInt(numConnectionsField.getText());

            ConnectionModel activeConnection = new ConnectionModel(passiveCommunity, numConnections);
            ConnectionModel passiveConnection = new ConnectionModel(activeCommunity, numConnections);

            if (isEditing)
            {
                activeCommunity.getConnections().remove(activeCommunity.getConnectionTo(passiveCommunity));
                passiveCommunity.getConnections().remove(passiveCommunity.getConnectionTo(activeCommunity));
            }

            activeCommunity.getConnections().add(activeConnection);
            passiveCommunity.getConnections().add(passiveConnection);

            dialogueStage.close();
        }
    }

    @FXML
    private void handleRemove()
    {
        activeCommunity.getConnections().remove(activeCommunity.getConnectionTo(passiveCommunity));
        passiveCommunity.getConnections().remove(passiveCommunity.getConnectionTo(activeCommunity));
        dialogueStage.close();
    }

    private boolean isInputValid()
    {
        String errorMessage = "";
        try
        {
            int value = Integer.parseInt(numConnectionsField.getText());
            if (value < 0) throw new NumberFormatException();
        } catch (NumberFormatException e)
        {
            errorMessage += "Not a valid number of connections"; // TODO: Move to messages
        }
        if (errorMessage.length() == 0)
        {
            return true;
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogueStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please Correct Invalid Fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}

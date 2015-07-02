package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.model.AgentModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to add new Agents to the community.
 *
 * @author Sam Beckmann
 */
public class AgentAddDialogController
{
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextField opinionField;
    @FXML
    private TextField numberAddField;

    private Stage dialogStage;
    private java.util.List<AgentModel> agents;
    private boolean okClicked = false;


    @FXML
    private void initialize()
    {
        /* NOOP */
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void setList(java.util.List<AgentModel> agents)
    {
        this.agents = agents;
        opinionField.setText("");
        numberAddField.setText("");
    }

    public boolean isOkClicked()
    {
        return okClicked;
    }

    @FXML
    private void handleOK()
    {
        if (isInputValid())
        {
            for (int i = 0; i < Integer.parseInt(numberAddField.getText()); i++)
            {
                agents.add(new AgentModel("Basic Agent", Double.parseDouble(opinionField.getText())));

                okClicked = true;
                dialogStage.close();
            }
        }
    }

    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    private boolean isInputValid()
    {
        String errorMessage = "";
        try
        {
            double value = Double.parseDouble(opinionField.getText());
            if (value < 0 || value > 1) throw new NumberFormatException();
        } catch (NumberFormatException e)
        {
            errorMessage += "Not a valid opinion.\n";
        }
        try
        {
            int number = Integer.parseInt(numberAddField.getText());
            if (number <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e)
        {
            errorMessage += "Not a valid number of agents to add.\n";
        }
        if (errorMessage.length() == 0)
        {
            return true;
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please Correct Invalid Fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

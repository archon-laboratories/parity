package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.ParityRegistry;
import com.samvbeckmann.parity.ReflectionWrapper;
import com.samvbeckmann.parity.model.AgentModel;
import com.samvbeckmann.parity.reference.Messages;
import com.samvbeckmann.parity.reference.Names;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for the dialog box
 * to add new Agents to the community.
 *
 * @author Sam Beckmann & Nate Beckemeyer
 */
public class AgentAddDialogController
{
    @FXML
    private ComboBox<ReflectionWrapper> comboBox;
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
        opinionField.setText(Names.EMPTY_STRING);
        numberAddField.setText(Names.EMPTY_STRING);
        comboBox.setItems(ParityRegistry.getAgents());
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
                agents.add(new AgentModel(comboBox.getValue(), Double.parseDouble(opinionField.getText())));
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    private boolean isInputValid()
    {
        String errorMessage = Names.EMPTY_STRING;
        if (comboBox.getValue() == null)
            errorMessage += Messages.Errors.INVALID_AGENT;
        try
        {
            double value = Double.parseDouble(opinionField.getText());
            if (value < 0 || value > 1) throw new NumberFormatException();
        } catch (NumberFormatException e)
        {
            errorMessage += Messages.Errors.INVALID_OPINION;
        }
        try
        {
            int number = Integer.parseInt(numberAddField.getText());
            if (number <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e)
        {
            errorMessage += Messages.Errors.INVALID_NUM_AGENTS;
        }
        if (errorMessage.length() == 0)
        {
            return true;
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle(Messages.Errors.INVALID_FIELDS);
            alert.setHeaderText(Messages.Errors.INVALID_FIELDS_HEADER);
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

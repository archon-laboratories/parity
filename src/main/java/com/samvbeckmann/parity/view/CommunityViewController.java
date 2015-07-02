package main.java.com.samvbeckmann.parity.view;

import main.java.com.samvbeckmann.parity.MainApp;
import main.java.com.samvbeckmann.parity.model.AgentModel;
import main.java.com.samvbeckmann.parity.reference.Messages;
import main.java.com.samvbeckmann.parity.utilities.ModelHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * parity, class made on 6/25/2015.
 *
 * @author Sam Beckmann
 */
public class CommunityViewController
{
    @FXML
    private TableView<AgentModel> agentTable;
    @FXML
    private TableColumn<AgentModel, String> nameColumn;
    @FXML
    private TableColumn<AgentModel, Double> opinionColumn;

    private MainApp mainApp;

    public CommunityViewController()
    {
        // NOOP
    }

    @FXML
    private void initialize()
    {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().classpathProperty());
        opinionColumn.setCellValueFactory(cellData -> cellData.getValue().opinionProperty().asObject());
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

        agentTable.setItems(mainApp.getCommunityData());
    }

    @FXML
    private void handleDeleteAgent()
    {
        int selectedIndex = agentTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            AgentModel agent = agentTable.getItems().get(selectedIndex);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle(Messages.Alerts.DELETE_CONFIRM);
            alert.setHeaderText(Messages.Alerts.DELETE_CONFIRM_MESSAGE);
            alert.setContentText(ModelHelper.getAgentStatsAsString(agent));

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) agentTable.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void handleAddAgents()
    {
        List<AgentModel> newAgents = new ArrayList<>();
        boolean okClicked = mainApp.showAgentAddDialog(newAgents);
        if (okClicked)
        {
            for (AgentModel agent : newAgents)
            {
                mainApp.getCommunityData().add(agent);
            }
        }
    }
}

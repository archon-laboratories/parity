package com.samvbeckmann.parity;

import com.samvbeckmann.parity.model.AgentModel;
import com.samvbeckmann.parity.reference.Names;
import com.samvbeckmann.parity.reference.Reference;
import com.samvbeckmann.parity.view.AgentAddDialogController;
import com.samvbeckmann.parity.view.CommunityViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Initializes the GUI portion of Parity.
 *
 * @author Sam Beckmann & Nate Beckemeyer
 */
public class MainApp extends Application
{
    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<AgentModel> communityData = FXCollections.observableArrayList();

    public MainApp()
    {
        communityData.add(new AgentModel("Basic Agent", 0.5)); /* Dummy Data for Testing */
        communityData.add(new AgentModel("Basic Agent", 0.5));
        communityData.add(new AgentModel("Basic Agent", 1.0));
        communityData.add(new AgentModel("Basic Agent", 0.0));
        communityData.add(new AgentModel("Q-Learner", 0.5));
        communityData.add(new AgentModel("Q-Learner", 1.0));
        communityData.add(new AgentModel("Q-Learner", 1.0));
        communityData.add(new AgentModel("Q-Learner", 0.0));
        communityData.add(new AgentModel("Q-Learner", 0.0)); /* TODO Remove when table populates */
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public ObservableList<AgentModel> getCommunityData()
    {
        return communityData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ParityRegistry.initializeRegistry();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Reference.NAME);
        this.primaryStage.setMinWidth(600);
        this.primaryStage.setMinHeight(500);

        initRootLayout();
        showConfigurationSettings();
        showCommunityOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout()
    {
        try
        {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Names.FXMLPaths.ROOT_LAYOUT));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showCommunityOverview()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Names.FXMLPaths.AGENT_VIEW));
            BorderPane communityView = loader.load();

            SplitPane split = (SplitPane) rootLayout.getCenter();
            split.getItems().add(communityView);

            CommunityViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showConfigurationSettings()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Names.FXMLPaths.CONFIGURATION_VIEW));
            BorderPane configurationSettings = loader.load();

            SplitPane split = (SplitPane) rootLayout.getCenter();
            split.getItems().add(configurationSettings);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean showAgentAddDialog(List<AgentModel> newAgents)
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Names.FXMLPaths.AGENT_ADD_DIALOGUE));
            AnchorPane page = loader.load();

            // Create the dialogue Stage.
            Stage dialogueStage = new Stage();
            dialogueStage.setTitle("Add Agents");
            dialogueStage.initModality(Modality.WINDOW_MODAL);
            dialogueStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogueStage.setScene(scene);

            // Set the person into the controller.
            AgentAddDialogController controller = loader.getController();
            controller.setDialogStage(dialogueStage);
            controller.setList(newAgents);

            // Show the dialog and wait until the user closes it
            dialogueStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return The main stage.
     */
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }


}

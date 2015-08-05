package com.samvbeckmann.parity;

import com.samvbeckmann.parity.core.Community;
import com.samvbeckmann.parity.core.ICompletionCondition;
import com.samvbeckmann.parity.core.IInteractionHandler;
import com.samvbeckmann.parity.model.AgentModel;
import com.samvbeckmann.parity.model.CommunityModel;
import com.samvbeckmann.parity.model.CommunityNode;
import com.samvbeckmann.parity.reference.Names;
import com.samvbeckmann.parity.reference.Reference;
import com.samvbeckmann.parity.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private CommunityViewController communityController;
    private CommunityModel activeCommunity = new CommunityModel();

    public MainApp()
    {
        // NOOP
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public CommunityModel getActiveCommunity()
    {
        return activeCommunity;
    }

    public void updateActiveCommunity(CommunityModel activeCommunity)
    {
        CommunityNode oldNode = this.activeCommunity.getNode();
        if (oldNode != null) oldNode.setFill(Color.DEEPSKYBLUE);
        this.activeCommunity = activeCommunity;
        communityController.updateCommunity();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ParityRegistry.populateRegistry();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Reference.NAME);
        this.primaryStage.setMinWidth(600);
        this.primaryStage.setMinHeight(500);
        this.primaryStage.getIcons().add(new Image("logo.png")); // TODO import image

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

            // Set up controller
            MenuBarController controller = loader.getController();
            controller.setMainApp(this);

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
            this.communityController = controller;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void showConfigurationSettings()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Names.FXMLPaths.CONFIGURATION_VIEW));
            BorderPane configurationSettings = loader.load();

            GridPane grid = (GridPane) ((VBox) configurationSettings.getTop()).getChildren().get(2);

            ComboBox<ReflectionWrapper> interactionHandlers = (ComboBox<ReflectionWrapper>) grid.getChildren().get(1);
            ComboBox<ReflectionWrapper> completionConditions = (ComboBox<ReflectionWrapper>) grid.getChildren().get(3);

            interactionHandlers.setItems(ParityRegistry.getInteractionHandlers());
            completionConditions.setItems(ParityRegistry.getCompletionConditions());

//            Rectangle rectangle = new Rectangle(); // TODO: Make rectangle purely visible, not affecting logic
//            rectangle.setTranslateY(10);
//            rectangle.setTranslateX(10);
//            rectangle.setFill(new Color(1.0, 1.0, 1.0, 1.0));
//            rectangle.setStroke(new Color(0.3, 0.3, 0.3, 1.0));
//            rectangle.setArcHeight(10);
//            rectangle.setArcWidth(10);
//            rectangle.heightProperty().bind(pane.heightProperty().subtract(20));
//            rectangle.widthProperty().bind(pane.widthProperty().subtract(20));
//            pane.getChildren().add(rectangle);

            AnchorPane pane = (AnchorPane) configurationSettings.getCenter();

            ConfigurationController controller = loader.getController();
            controller.setMainAppAndPane(this, pane);

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

    public void showConnectionAddDialogue(CommunityModel activeCommunity, CommunityModel passiveCommunity)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Names.FXMLPaths.CONNECTION_ADD_DIALOGUE));
            AnchorPane page = loader.load();

            Stage dialogueStage = new Stage();
            dialogueStage.setTitle("Add/Edit Connection");
            dialogueStage.initModality(Modality.WINDOW_MODAL);
            dialogueStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogueStage.setScene(scene);

            ConnectionAddDialogueController controller = loader.getController();
            controller.setDialogueStage(dialogueStage);
            controller.setConnections(activeCommunity, passiveCommunity);

            dialogueStage.showAndWait();


        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    // TODO: Implement these
    public Community[] getCommunities()
    {
        return null;
    }

    public ICompletionCondition getCompletionCondition()
    {
        return null;
    }

    public IInteractionHandler getInteractionHandler()
    {
        return null;
    }

    public int getNumIterations()
    {
        return 0;
    }

    /**
     * @return The main stage.
     */
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }
}

package com.samvbeckmann.parity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * parity, class made on 6/23/2015.
 *
 * @author Sam Beckmann
 */
public class MainApp extends Application
{
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Parity");

        initRootLayout();

        showConfigurationSettings();

        showCommunityOverview();

//        Parent root = FXMLLoader.load(getClass().getResource("view/rootLayout.fxml"));
//        primaryStage.setTitle("Parity");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
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
            loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showCommunityOverview()
    {
        try
        {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/agentView.fxml"));
            BorderPane communityView = (BorderPane) loader.load();

            // Set person overview into the center of root layout.
            SplitPane split = (SplitPane) rootLayout.getCenter();
            split.getItems().add(communityView);
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
            loader.setLocation(MainApp.class.getResource("view/settingsView.fxml"));
            VBox configurationSettings = (VBox) loader.load();
//            configurationSettings.setAlignment(Pos.TOP_CENTER);
//            configurationSettings.getChildren().add(new AnchorPane());

            SplitPane split = (SplitPane) rootLayout.getCenter();
            split.getItems().add(configurationSettings);
//            split.getItems().add(new AnchorPane());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return The main stage.
     */
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

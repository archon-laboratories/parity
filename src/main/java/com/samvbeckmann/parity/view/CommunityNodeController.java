package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.model.CommunityNode;
import com.samvbeckmann.parity.model.ConnectionModel;
import com.samvbeckmann.parity.reference.Names;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Configures the handlers for a {@link CommunityNode}
 *
 * @author Sam Beckmann & Nate Beckmeyer
 */
public class CommunityNodeController
{
    public void configureHandlers(CommunityNode community, MainApp mainApp)
    {
        community.setFill(Color.DEEPSKYBLUE);
        Wrapper<Point2D> mouseLocation = new Wrapper<>();

        community.setOnDragDetected(event ->
        {
            community.getParent().setCursor(Cursor.CLOSED_HAND);
            mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
        });

        community.setOnMouseReleased(event ->
        {
            community.getParent().setCursor(Cursor.DEFAULT);
            mouseLocation.value = null;
        });

        community.setOnMouseDragged(event ->
        {
            if (mouseLocation.value != null)
            {
                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();
                double newX = community.getCenterX() + deltaX;
                double newY = community.getCenterY() + deltaY;

                AnchorPane pane = (AnchorPane) community.getParent();

                if (newX > 30 && newX < pane.getWidth() - 30)
                    community.setCenterX(newX);
                if (newY > 80 && newY < pane.getHeight() - 30)
                    community.setCenterY(newY);

                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }
        });

        community.setOnContextMenuRequested(event ->
        {
            CommunityNode activeCommunity = mainApp.getActiveCommunity().getNode();
            if (!activeCommunity.equals(community))
            {
                mainApp.showConnectionAddDialogue(activeCommunity.getCommunity(), community.getCommunity());
            }
        });

        community.setOnMouseClicked(event ->
        {
            if (event.getButton().equals(MouseButton.PRIMARY))
            {
                mainApp.updateActiveCommunity(community.getCommunity());
                community.setFill(Color.MEDIUMSEAGREEN);
            }
        });
    }

    static class Wrapper<T>
    {
        T value;
    }

}

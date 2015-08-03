package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.model.CommunityNode;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * parity, class made on 2015-07-31
 *
 * @author Sam Beckmann
 */
public class CommunityNodeController
{
//    private CommunityNode

    public static void configureHandlers(CommunityNode community, MainApp mainApp)
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
                if (newY > 30 && newY < pane.getHeight() - 30)
                    community.setCenterY(newY);
                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }
        });

        community.setOnContextMenuRequested(event ->
                community.setFill(Color.BLUE));

        community.setOnMouseClicked(event ->
        {
            if (event.getButton().equals(MouseButton.PRIMARY))
            {
                mainApp.updateActiveCommunity(community.getCommunity());
            }
        });
    }

    static class Wrapper<T>
    {
        T value;
    }

}

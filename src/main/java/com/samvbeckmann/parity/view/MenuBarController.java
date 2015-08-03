package com.samvbeckmann.parity.view;

import javafx.fxml.FXML;

/**
 * parity, class made on 2015-07-31
 *
 * @author Sam Beckmann
 */
public class MenuBarController
{
    public MenuBarController()
    {

    }

    @FXML
    private void initialize()
    {
        // NOOP
    }

    @FXML
    public void handleRunConfiguration()
    {
        // call saveAsTemp
        System.out.println("Made it to handler!");
        // run temp file.
    }
}

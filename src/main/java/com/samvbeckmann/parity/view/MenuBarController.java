package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.MainApp;
import javafx.fxml.FXML;

/**
 * parity, class made on 2015-07-31
 *
 * @author Sam Beckmann
 */
public class MenuBarController
{
    /* Reference to the main application */
    private MainApp mainApp;

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
        System.out.println(mainApp != null ? "Main app set" : "Error!");
        // run temp file.
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
}

package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.core.DataSaver;
import javafx.fxml.FXML;

/**
 * parity, class made on 2015-07-31
 *
 * @author Sam Beckmann
 */
public class MenuBarController
{
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
        System.out.println("Made it to handler!");
        System.out.println(mainApp != null ? "Main app set" : "Error!");
        //DataSaver.saveAsTemp(mainApp);
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
}

package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.core.DataSaver;
import com.samvbeckmann.parity.core.Parity;
import javafx.fxml.FXML;

import java.io.File;

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
        File tempFile = DataSaver.saveAsTemp(mainApp);
        if (tempFile != null)
        {
            Parity.main(new String[]{tempFile.getAbsolutePath()}, mainApp.getPrimaryStage());
        }
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
}

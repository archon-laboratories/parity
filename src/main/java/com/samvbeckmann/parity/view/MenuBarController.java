package com.samvbeckmann.parity.view;

import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.core.DataSaver;
import com.samvbeckmann.parity.core.Parity;
import javafx.fxml.FXML;

import java.io.File;
import java.util.ArrayList;

/**
 * parity, class made on 2015-07-31
 *
 * @author Sam Beckmann
 */
public class MenuBarController
{
    private MainApp mainApp;
    private ArrayList<String> datasets;

    private boolean firstAddToQueue;
    private boolean useSaveAs;
    private File saveFile;

    private boolean changed()
    {
        // TODO implement
        return true;
    }

    private boolean wantToSave()
    {
        // TODO show dialog request
        return true;
    }

    public MenuBarController()
    {
        firstAddToQueue = true;
        useSaveAs = true;
        datasets = new ArrayList<>();
    }

    @FXML
    private void initialize()
    {
        // NOOP
    }

    @FXML
    public void handleNew()
    {
        if (changed())
        {
            if (wantToSave())
            {
                handleSave();
            }
        }
        // Clear the configuration
        useSaveAs = true;
    }

    @FXML
    public void handleSave()
    {
        if (useSaveAs)
        {
            handleSaveAs();
        } else
        {
            saveFile = DataSaver.saveAsDataset(mainApp, saveFile);
        }
    }

    @FXML
    public void handleSaveAs()
    {
        saveFile = DataSaver.saveAsDataset(mainApp);
        useSaveAs = false;
    }

    @FXML
    public void handleOpen()
    {
        /*
        Attempt to load file.
        If successful:
            Mark useSaveAs as false
            Set saveFile to the loaded file
        If failure:
            Do nothing
         */
    }

    @FXML
    public void handleInfo()
    {
        // TODO implement
    }

    @FXML
    public void handleSettings()
    {
        // TODO implement
    }

    @FXML
    public void handleRunQueue()
    {
        String[] filepaths = (String[]) datasets.toArray();
        Parity.main(filepaths, mainApp.getPrimaryStage());
    }

    @FXML
    public void handleAddQueue()
    {
        if (firstAddToQueue)
        {
            // Show warning that if they save the file after adding it to the queue, the changes *will* affect the run.
            // Additionally, changes will be saved automatically henceforth.
            if (changed())
            {
                handleSaveAs();
            }
            datasets.add(saveFile.getAbsolutePath());
            firstAddToQueue = false;
        } else
        {
            handleSave();
            datasets.add(saveFile.getAbsolutePath());
        }
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

    public ArrayList<String> getDatasetQueue()
    {
        return datasets;
    }


    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
}

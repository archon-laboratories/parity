package com.samvbeckmann.parity.core;


import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created for parity by @author Nate Beckemeyer on 2015-07-31.
 */
public class DataSaver
{
    private static FileChooser chooser = new FileChooser();

    private static File getFileLocation(Stage stage) throws IOException
    {
        chooser.setTitle("Select the Save Location");
        File toSave = chooser.showSaveDialog(stage);
        if (toSave != null)
        {
            if (toSave.getParentFile().isDirectory())
            {
                chooser.setInitialDirectory(toSave.getParentFile());
            }

            if (toSave.createNewFile())
            {
                System.out.println("File " + toSave.getAbsolutePath() + " created successfully!");
            } else
            {
                System.out.println("Did not create file " + toSave.getAbsolutePath());
            }
        }
        return toSave;
    }

    /**
     * Saves the results of the run
     *
     * @return The success of the save
     */
    public static boolean saveResults(Stage stage)
    {
        try
        {
            File toSave = getFileLocation(stage);
            FileWriter writer = new FileWriter(toSave);

            double[] opinions = Parity.getOpinions();
            int[] timesteps = Parity.getTimesteps();
            for (int i = 0; i < Parity.getNumberTrials(); i++)
            {
                writer.write(opinions[i] + " " + timesteps[i] + "%n");
            }

            writer.close();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Saves the information from the GUI
     *
     * @return The success of the save
     */
    public static boolean saveDataset(Stage stage)
    {
        return false;
    }
}

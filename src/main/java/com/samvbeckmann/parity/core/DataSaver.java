package com.samvbeckmann.parity.core;


import com.samvbeckmann.parity.MainApp;
import com.samvbeckmann.parity.reference.DataTemplates;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class saves information from the GUI. Saving is generally unnecessary for the command line.
 * <p>
 * Created for parity by @author Nate Beckemeyer on 2015-07-31.
 */
public class DataSaver
{
    private static FileChooser chooser = new FileChooser();

    private static boolean tempCreatedThisRun = false;

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
        }
        return toSave;
    }

    private static String transcribeOpinions(AbstractAgent agent)
    {
        String returnStr = "";
        for (int i = 0; i < agent.getOpinions().length; i++)
        {
            returnStr += String.format(DataTemplates.opinionTemplate, agent.getOpinions()[i]);

            if (i < agent.getOpinions().length - 1)
            {
                returnStr += ",";
            }

            returnStr += String.format("%n");
        }

        return returnStr;
    }

    private static String transcribeAgents(AbstractAgent[] agents)
    {
        String returnStr = "";
        for (int i = 0; i < agents.length; i++)
        {
            returnStr += String.format(DataTemplates.agentTemplate, agents[i].getClass().getName(),
                    i, i, transcribeOpinions(agents[i]));

            if (i < agents.length - 1)
            {
                returnStr += ",";
            }

            returnStr += String.format("%n");
        }

        return returnStr;
    }

    private static String transcribeNeighbours(Connection[] neighbours, Community[] communities)
    {
        String returnStr = "";
        for (int i = 0; i < neighbours.length; i++)
        {
            int neighborId;

            // Find the id of the neighboring community
            for (neighborId = 0; neighborId < communities.length; neighborId++)
            {
                if (communities[neighborId] == neighbours[i].getNeighbourCommunity())
                {
                    break;
                }
            }
            returnStr += String.format(DataTemplates.neighbourTemplate,
                    neighbours[i].getPossibleInteractions(), neighborId);

            if (i < neighbours.length - 1)
            {
                returnStr += ",";
            }

            returnStr += String.format("%n");
        }

        return returnStr;
    }

    private static String transcribeCommunities(Community[] communities)
    {
        String returnStr = "";
        for (int i = 0; i < communities.length; i++)
        {
            returnStr += String.format(DataTemplates.communityTemplate, i, communities[i].getNeighbours().length,
                    transcribeNeighbours(communities[i].getNeighbours(), communities),
                    communities[i].communitySize(), transcribeAgents(communities[i].getAgents()));

            if (i < communities.length - 1)
            {
                returnStr += ",";
            }

            returnStr += String.format("%n");
        }
        return returnStr;
    }

    private static void saveConfigData(MainApp mainApp, File location) throws IOException
    {
        FileWriter writer = new FileWriter(location);
        String fileContents = String.format(DataTemplates.populationTemplate, mainApp.getNumIterations(),
                mainApp.getCommunities().length, mainApp.getCompletionCondition().getClass().getName(),
                mainApp.getInteractionHandler().getClass().getName(), 1,
                transcribeCommunities(mainApp.getCommunities()));
        writer.write(fileContents);
        writer.close();
    }

    /**
     * Saves the results of the run.
     *
     * @return The location of the file
     */
    public static File saveResults(Stage stage)
    {
        File toSave;
        try
        {
            toSave = getFileLocation(stage);
            FileWriter writer = new FileWriter(toSave);

            double[] opinions = Parity.getOpinions();
            int[] timesteps = Parity.getTimesteps();

            for (int i = 0; i < Parity.getNumberTrials(); i++)
            {
                writer.write(String.format(DataTemplates.resultTemplate, opinions[i], timesteps[i]));
            }

            writer.close();

        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return toSave;
    }

    /**
     * Saves the information from the GUI. A "save as" function.
     *
     * @return The new file; null if failure.
     */
    public static File saveAsDataset(MainApp mainApp)
    {
        File toSave;
        Stage stage = mainApp.getPrimaryStage();
        try
        {
            toSave = getFileLocation(stage);
            saveConfigData(mainApp, toSave);
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return toSave;
    }

    /**
     * Saves the configuration into a temp file so that it can be run.
     *
     * @param mainApp The main class of the program
     * @return The temp file; null if failure.
     */
    public static File saveAsTemp(MainApp mainApp)
    {
        File temp;
        try
        {
            // Empty and create the directory
            File tempDir = new File("temp");
            if (!tempCreatedThisRun)
            {
                if (tempDir.exists())
                {
                    for (File cur : tempDir.listFiles())
                    {
                        cur.delete();
                    }
                } else
                {
                    tempDir.mkdir();
                }
                tempCreatedThisRun = true;
            }

            // Create a new temp file
            int count = 0;
            do
            {
                temp = new File("temp/temp_" + count + ".json");
                count++;
            } while (temp.exists());

            if (!temp.createNewFile())
                return null;
            saveConfigData(mainApp, temp);
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return temp;
    }
}

package com.samvbeckmann.parity.core;

import javafx.stage.Stage;

import java.util.Map;

/**
 * Created by Nate Beckemeyer and Sam Beckmann
 */
public class Parity
{
    private static double[] averageOpinions;
    private static int[] timesteps;
    private static int numberTrials;

    private static void run(String[] filepaths, Stage stage)
    {
        for (String datafile : filepaths)
        {
            long startTime = System.nanoTime();
            double averageOpinion = 0;
            int averageTimestep = 0;

            Dataset primary = new Dataset(datafile);
            ICompletionCondition condition = primary.getCompletionCondition();
            IInteractionHandler handler = primary.getInteractionHandler();
            numberTrials = primary.getNumTrials();

            averageOpinions = new double[numberTrials];
            timesteps = new int[numberTrials];

            // Run the trials
            for (int i = 0; i < numberTrials; i++)
            {
                Population initial = primary.shuffleData();

                // Continue performing interactions until the simulation completes. Responsibility for ensuring that
                // the simulation can always eventually complete falls to the user.
                while (!condition.simulationComplete(initial))
                {
                    performInteractions(handler, initial);
                    initial.incrementTimestep();
                }

                averageOpinions[i] = initial.getAverageOpinion();
                timesteps[i] = initial.getTimestep();
            }

            if (stage == null)
            {
                for (int i = 0; i < averageOpinions.length; i++)
                {
                    averageOpinion += averageOpinions[i];
                    averageTimestep += timesteps[i];
                }

                System.out.printf(
                        "Simulation complete! Statistics for %d trials:%nFinal opinion: %f%nTimestep: %f%nTime to " +
                                "complete: %.5fs%n%n",
                        numberTrials,
                        averageOpinion / ((double) primary.getNumTrials()),
                        averageTimestep / ((double) primary.getNumTrials()),
                        (System.nanoTime() - startTime) / (Math.pow(10., 9)));
            } else
            {
                DataSaver.saveResults(stage);
            }
        }
    }

    public static void main(String[] filepaths, Stage stage)
    {
        run(filepaths, stage);
    }

    public static void main(String[] args)
    {
        run(args, null);
    }

    /**
     * Performs the interactions for the population
     *
     * @param handler    The interaction handler with which to handle the interactions
     * @param population The population on which to run the data
     */
    private static void performInteractions(IInteractionHandler handler, Population population)
    {
        Map<AbstractAgent, AbstractAgent> interactions = handler.determineInteractions(population);

        for (Map.Entry<AbstractAgent, AbstractAgent> entry : interactions.entrySet())
        {
            AbstractAgent columnPlayer = entry.getKey();
            AbstractAgent rowPlayer = entry.getValue();
            Object column = columnPlayer.interaction();
            Object row = rowPlayer.interaction();

            entry.getKey().updateOpinions(handler.getColumnFeedback(column, row));
            entry.getValue().updateOpinions(handler.getRowFeedback(column, row));
        }
    }

    public static double[] getOpinions()
    {
        return averageOpinions;
    }

    public static int[] getTimesteps()
    {
        return timesteps;
    }

    public static int getNumberTrials()
    {
        return numberTrials;
    }
}

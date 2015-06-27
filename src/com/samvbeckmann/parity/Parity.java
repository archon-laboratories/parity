package com.samvbeckmann.parity;

import com.samvbeckmann.parity.basicProgram.Choices;
import com.samvbeckmann.parity.basicProgram.States;

import java.util.Map;

/**
 * Created by Nate Beckemeyer and Sam Beckmmann
 */
public class Parity
{
    public static void main(String[] args)
    {

        long startTime = System.nanoTime();
        double averageOpinion = 0;
        double averageTimestep = 0;
        Dataset primary = new Dataset("datasets/demo.json");
        ICompletionCondition condition = primary.getCompletionCondition();
        IInteractionHandler handler = primary.getInteractionHandler();

        for (int i = 1; i <= primary.getNumTrials(); i++)
        {
            Population initial = primary.scrambleData();

            while (!condition.simulationComplete(initial))
            {
                performInteractions(handler, initial);
                initial.incrementTimestep();
            }

            averageOpinion += initial.getAverageOpinion();
            averageTimestep += initial.getTimestep();
        }

        System.out.printf("Simulation complete! Statistics:\nFinal opinion: %f\nTimestep: %f\nTime to complete: %.5fs",
                averageOpinion / ((double) primary.getNumTrials()),
                averageTimestep / ((double) primary.getNumTrials()),
                (System.nanoTime() - startTime) / (Math.pow(10., 9)));
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
            Choices column = entry.getKey().interaction(States.COLUMN);
            Choices row = entry.getValue().interaction(States.ROW);

            entry.getKey().updateOpinions(handler.getColumnFeedback(column, row));
            entry.getValue().updateOpinions(handler.getRowFeedback(column, row));
        }
    }
}

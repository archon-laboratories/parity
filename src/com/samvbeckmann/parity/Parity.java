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

        double average = 0;

        for (int i = 1; i <= 1000; i++)
        {

            Dataset primary = new Dataset("datasets/demo.json");

            Population initial = primary.getDatasetPopulation();
            ICompletionCondition condition = primary.getCompletionCondition();


            while (!condition.simulationComplete(initial))
                performInteractions(primary.getInteractionHandler(), initial);
            average += initial.getAverageOpinion();
        }


        System.out.println("Simulation complete! Final opinion: " + average/1000);
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

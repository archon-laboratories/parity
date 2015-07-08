package com.samvbeckmann.parity.core;

import com.samvbeckmann.parity.demoProgram.BasicChoices;
import com.samvbeckmann.parity.demoProgram.BasicStates;

import java.util.Map;

/**
 * Created by Nate Beckemeyer and Sam Beckmann
 */
public class Parity
{

    public static void main(String[] args)
    {

        for (String arg : args)
        {
            long startTime = System.nanoTime();
            double averageOpinion = 0;
            double averageTimestep = 0;
            Dataset primary = new Dataset(arg);
            ICompletionCondition condition = primary.getCompletionCondition();
            IInteractionHandler handler = primary.getInteractionHandler();

            for (int i = 1; i <= primary.getNumTrials(); i++)
            {
                Population initial = primary.shuffleData();

                while (!condition.simulationComplete(initial))
                {
                    performInteractions(handler, initial);
                    initial.incrementTimestep();
                }

                averageOpinion += initial.getAverageOpinion();
                averageTimestep += initial.getTimestep();
            }

            System.out.printf("Simulation complete! Statistics:%nFinal opinion: %f%nTimestep: %f%nTime to complete: %.5fs%n%n",
                    averageOpinion / ((double) primary.getNumTrials()),
                    averageTimestep / ((double) primary.getNumTrials()),
                    (System.nanoTime() - startTime) / (Math.pow(10., 9)));
        }
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
}

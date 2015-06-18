package com.samvbeckmann.parity;

import java.util.Map;

/**
 * Created by SAM on 6/15/2015.
 */
public class Parity
{
    public static void main(String[] args)
    {

    }

    private static void performInteractions(IInteractionHandler handler, Population population)
    {
        Map<AbstractAgent, AbstractAgent> interactions = handler.determineInteractions(population);

        for (Map.Entry<AbstractAgent, AbstractAgent> entry : interactions.entrySet())
        {
            Choices column = entry.getKey().interaction(States.COLUMN);
            Choices row = entry.getValue().interaction(States.ROW);

            entry.getKey().updateOpinion(handler.getColumnFeedback(column, row));
            entry.getValue().updateOpinion(handler.getRowFeedback(column, row));
        }
    }
}

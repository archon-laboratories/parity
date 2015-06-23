package com.samvbeckmann.parity;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by SAM on 6/15/2015.
 */
public class Parity
{
    public static void main(String[] args)
    {
        Community test = new Community();
        Community beta = new Community();
        OneWayConnection tB = new OneWayConnection();
        tB.setCommunity(beta);

        test.setNeighbours(new OneWayConnection[]{tB});

        BasicAgent[] agentList = {new BasicAgent(.3), new BasicAgent(.4), new BasicAgent()};
        test.setAgents(agentList);

        Dataset run = new Dataset("datasets/demo.json");

        System.out.println(new Gson().toJson(test));
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

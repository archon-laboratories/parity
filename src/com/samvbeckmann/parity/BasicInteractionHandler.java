package com.samvbeckmann.parity;

import com.samvbeckmann.parity.utilities.InteractionHelper;

import java.util.List;
import java.util.Map;

/**
 * parity, class made on 6/16/2015.
 *
 * @author Sam Beckmann
 */
public class BasicInteractionHandler implements IInteractionHandler
{

    public Map determineInteractions(Population p)
    {
        List<TwoWayConnection> connections = InteractionHelper.getConnectionsFromPopulation(p);



        return null;
    }

    public int getColumnFeedback(Choices columnPlayer, Choices rowPlayer)
    {
        return 0;
    }

    public int getRowFeedback(Choices columnPlayer, Choices rowPlayer)
    {
        return 0;
    }
}

package com.samvbeckmann.parity;

import com.samvbeckmann.parity.utilities.InteractionHelper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A sample implementation of {@link IInteractionHandler}.
 * Use / extend this, or make your own.
 *
 * @author Sam Beckmann
 */
public class BasicInteractionHandler implements IInteractionHandler
{
    public static final int POSITIVE_REWARD = 1;
    public static final int NEGATIVE_REWARD = -1;

    public Map determineInteractions(Population p)
    {
        List<TwoWayConnection> connections = InteractionHelper.getConnectionsFromPopulation(p);
        Map<IAgent, IAgent> map = new TreeMap<>();

        for (TwoWayConnection con : connections)
        {
            for (int i = 0; i < con.getPossibleInteractions(); i++)
            {
                IAgent agent1 = con.getCommunity1().markRandomAgentForInteraction();
                IAgent agent2 = con.getCommunity2().markRandomAgentForInteraction();
                if (agent1 != null && agent2 != null)
                {
                    map.put(agent1, agent2);
                } else
                {
                    // TODO error message
                }
            }
        }

        for (Community community : p.communities)
        {
            while (community.getNumberAvailable() >= 2)
            {
                IAgent agent1 = community.markRandomAgentForInteraction();
                IAgent agent2 = community.markRandomAgentForInteraction();
                if (agent1 != null && agent2 != null)
                {
                    map.put(agent1, agent2);
                } else
                {
                    // TODO error message
                }
            }
        }

        return map;
    }

    public int getColumnFeedback(Choices columnPlayer, Choices rowPlayer)
    {
        return columnPlayer == rowPlayer ? POSITIVE_REWARD : NEGATIVE_REWARD;
    }

    public int getRowFeedback(Choices columnPlayer, Choices rowPlayer)
    {
        return getColumnFeedback(columnPlayer, rowPlayer);
    }
}

package com.samvbeckmann.parity;

import com.samvbeckmann.parity.utilities.InteractionHelper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A sample implementation of {@link IInteractionHandler}.
 * Use / extend this, or make your own.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class BasicInteractionHandler implements IInteractionHandler
{
    public static final int POSITIVE_REWARD = 1;
    public static final int NEGATIVE_REWARD = -1;

    /**
     * @param p The population to find interactions for.
     * @return A {@link Map} of interactions from agent to agent in the population.
     */
    public Map<AbstractAgent, AbstractAgent> determineInteractions(Population p)
    {
        List<TwoWayConnection> connections = InteractionHelper.getConnectionsFromPopulation(p);
        Map<AbstractAgent, AbstractAgent> map = new TreeMap<>();

        for (TwoWayConnection con : connections)
        {
            for (int i = 0; i < con.getPossibleInteractions(); i++)
            {
                AbstractAgent agent1 = con.getCommunity1().markRandomAgentForInteraction();
                AbstractAgent agent2 = con.getCommunity2().markRandomAgentForInteraction();
                if (agent1 != null && agent2 != null)
                {
                    map.put(agent1, agent2);
                } else
                {
                    // TODO error message
                }
            }
        }

        for (Community community : p.getCommunities())
        {
            while (community.getNumberAvailable() >= 2)
            {
                AbstractAgent agent1 = community.markRandomAgentForInteraction();
                AbstractAgent agent2 = community.markRandomAgentForInteraction();
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

    /**
     * @param columnPlayer The choice of the Column Player
     * @param rowPlayer    The choice of the Row Player
     * @return If their actions align, reinforce the behavior. Else, discourage it.
     */
    public int getColumnFeedback(Choices columnPlayer, Choices rowPlayer)
    {
        return columnPlayer == rowPlayer ? POSITIVE_REWARD : NEGATIVE_REWARD;
    }

    /**
     * @param columnPlayer The choice of the Column Player
     * @param rowPlayer    The choice of the Row Player
     * @return If their actions align, reinforce the behavior. Else, discourage it.
     */
    public int getRowFeedback(Choices columnPlayer, Choices rowPlayer)
    {
        return getColumnFeedback(columnPlayer, rowPlayer);
    }
}

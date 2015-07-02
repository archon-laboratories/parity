package com.samvbeckmann.parity.demoProgram;

import com.samvbeckmann.parity.core.*;
import com.samvbeckmann.parity.utilities.InteractionHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * A sample implementation of {@link IInteractionHandler}.
 * Use/extend this, or make your own.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class BasicInteractionHandler implements IInteractionHandler
{
    public static final int POSITIVE_REWARD = 1;
    public static final int NEGATIVE_REWARD = -1;


    private void insertCrossCommunityInteractions(Map<AbstractAgent, AbstractAgent> map, Connection[] connections)
    {
        for (Connection con : connections)
        {
            for (int i = 0; i < con.getPossibleInteractions(); i++)
            {
                AbstractAgent agent1 = con.getThisCommunity().markRandomAgentForInteraction();
                AbstractAgent agent2 = con.getOtherCommunity().markRandomAgentForInteraction();

                if (agent1 != null && agent2 != null)
                    map.put(agent1, agent2);
                else
                    System.err.println("Agents null in two way connection selection!");
            }
        }
    }

    private void insertIntraCommunityInteractions(Map<AbstractAgent, AbstractAgent> map, Population currentPop)
    {
        for (Community community : currentPop.getCommunities())
        {
            while (community.getNumberAvailable() >= 2)
            {
                AbstractAgent agent1 = community.markRandomAgentForInteraction();
                AbstractAgent agent2 = community.markRandomAgentForInteraction();
                if (agent1 != null && agent2 != null)
                {
                    map.put(agent1, agent2);
                } else
                    System.err.println("Could not get agents to mark for interaction!");
            }
            community.resetAvailability();
        }
    }

    /**
     * @param currentPop The population to find interactions for.
     * @return A {@link Map} of interactions from agent to agent in the population.
     */
    public Map<AbstractAgent, AbstractAgent> determineInteractions(Population currentPop)
    {
        Connection[] connections = currentPop.getConnections();
        Map<AbstractAgent, AbstractAgent> map = new HashMap<>();

        insertCrossCommunityInteractions(map, connections);
        insertIntraCommunityInteractions(map, currentPop);

        return map;
    }

    /**
     * @param columnPlayer The choice of the Column Player
     * @param rowPlayer    The choice of the Row Player
     * @return If their actions align, reinforce the behavior (1). Else, discourage it. (-1)
     */
    public int getColumnFeedback(BasicChoices columnPlayer, BasicChoices rowPlayer)
    {
        return columnPlayer == rowPlayer ? POSITIVE_REWARD : NEGATIVE_REWARD;
    }

    /**
     * @param columnPlayer The choice of the Column Player
     * @param rowPlayer    The choice of the Row Player
     * @return If their actions align, reinforce the behavior (1). Else, discourage it. (-1)
     */
    public int getRowFeedback(BasicChoices columnPlayer, BasicChoices rowPlayer)
    {
        return getColumnFeedback(columnPlayer, rowPlayer);
    }
}

package com.samvbeckmann.parity.utilities;

import com.samvbeckmann.parity.Community;
import com.samvbeckmann.parity.OneWayConnection;
import com.samvbeckmann.parity.Population;
import com.samvbeckmann.parity.TwoWayConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods for {@link com.samvbeckmann.parity.IInteractionHandler}
 *
 * @author Sam Beckmann
 */
public final class InteractionHelper
{
    public static List<TwoWayConnection> getConnectionsFromPopulation(Population p)
    {
        List<TwoWayConnection> connections = new ArrayList<>();

        for (Community community : p.communities)
        {
            for (OneWayConnection neighbour : community.getNeighbours())
            {
                OneWayConnection reverse = neighbour.getCommunity().getConnectionByCommunity(community);

                if (reverse != null)
                {
                    int min = Math.min(neighbour.getPossibleInteractions(), reverse.getPossibleInteractions());
                    TwoWayConnection twoWay = new TwoWayConnection(community, neighbour.getCommunity(), min);
                    if (!connections.contains(twoWay))
                        connections.add(twoWay);
                }
            }
        }
        return connections;
    }
}

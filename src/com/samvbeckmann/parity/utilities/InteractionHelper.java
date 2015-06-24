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
 * @author Nate Beckemeyer & Sam Beckmann
 */
public final class InteractionHelper
{

    /**
     * If a two-way connection does not exist, then add it in. Otherwise, don't.
     *
     * @param connections The list to add potentially the new connection into.
     * @param main        The connection from the community to its neighbour.
     * @param reverse     The connection from the neighbor to the community.
     */
    private static boolean canConnect(List<TwoWayConnection> connections, OneWayConnection main, OneWayConnection reverse)
    {
        for (TwoWayConnection conn : connections)
            if ((conn.getCommunity1() == main.getCommunity() && conn.getCommunity2() == reverse.getCommunity()) ||
                    (conn.getCommunity1() == reverse.getCommunity() && conn.getCommunity2() == main.getCommunity()))
                return false;

        return true;
    }

    /**
     * Gets the unique two-way connections of a population.
     *
     * @param currentPop The population in which to get two-way connections
     * @return A list of two-way connections
     */
    public static List<TwoWayConnection> getConnectionsFromPopulation(Population currentPop)
    {
        List<TwoWayConnection> connections = new ArrayList<>();

        for (Community community : currentPop.getCommunities())
        {
            for (OneWayConnection neighbour : community.getNeighbours())
            {
                OneWayConnection reverse = neighbour.getCommunity().getConnectionByCommunity(community);

                if (reverse != null && canConnect(connections, neighbour, reverse))
                {
                    int min = Math.min(neighbour.getPossibleInteractions(), reverse.getPossibleInteractions());
                    TwoWayConnection twoWay = new TwoWayConnection(community, neighbour.getCommunity(), min);
                    connections.add(twoWay);
                }
            }
        }
        return connections;
    }
}

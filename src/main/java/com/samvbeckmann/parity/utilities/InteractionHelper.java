package com.samvbeckmann.parity.utilities;

import com.samvbeckmann.parity.core.Community;
import com.samvbeckmann.parity.core.Connection;
import com.samvbeckmann.parity.core.IInteractionHandler;
import com.samvbeckmann.parity.core.Population;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods for {@link IInteractionHandler}
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public final class InteractionHelper
{

    /**
     * Checks if a two way connection is unique, or if it's already in the arraylist found in some form.
     *
     * @param found   The list of Connection objects that have already been found
     * @param toCheck The Connection to check
     * @return False if toCheck is in found, true otherwise
     */
    private static boolean isUnique(List<Connection> found, Connection toCheck)
    {
        for (Connection current : found)
        {
            if (current.getOtherCommunity() == toCheck.getOtherCommunity()
                    && current.getThisCommunity() == toCheck.getThisCommunity())
                return false;
            if (current.getOtherCommunity() == toCheck.getThisCommunity()
                    && current.getThisCommunity() == toCheck.getOtherCommunity())
                return false;
        }
        return true;
    }

    /**
     * Gets the unique two-way connections of a population.
     *
     * @param currentPop The population in which to get two-way connections
     * @return A list of two-way connections
     */
    public static Connection[] getConnectionsFromPopulation(Population currentPop)
    {
        List<Connection> foundConnections = new ArrayList<>();

        for (Community community : currentPop.getCommunities())
            for (Connection neighbour : community.getNeighbours()) // Get the connection
                if (isUnique(foundConnections, neighbour))
                    foundConnections.add(neighbour);

        return foundConnections.toArray(new Connection[foundConnections.size()]);
    }
}

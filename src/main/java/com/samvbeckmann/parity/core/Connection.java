package com.samvbeckmann.parity.core;

/**
 * Defines a connection between two communities.
 * Should only be used by an {@link IInteractionHandler}
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class Connection
{
    /**
     * {@link Community} this community in the interaction
     * If accessed directly through a community (community.getNeighbours()), thisCommunity is the community that
     * references the connection
     * For example, if B is a neighbour of A, then A is thisCommunity in A's neighbour list.
     */
    private Community thisCommunity;

    /**
     * {@link Community} the other community in the interaction
     * If accessed directly through a community (community.getNeighbours()), neighbourCommunity is the community that
     * does not reference the connection.
     * For example, if B is a neighbour of A, then B is neighbourCommunity in A's neighbour list.
     */
    private Community neighbourCommunity;

    /**
     * The number of possible interactions in this two-way avenue
     */
    private int possibleInteractions;

    public Connection(Community com1, Community com2, int possibleInteractions)
    {
        this.thisCommunity = com1;
        this.neighbourCommunity = com2;
        this.possibleInteractions = possibleInteractions;
    }

    /**
     * @return this community
     */
    public Community getThisCommunity()
    {
        return thisCommunity;
    }

    /**
     * @return the neighbouring community
     */
    public Community getNeighbourCommunity()
    {
        return neighbourCommunity;
    }

    /**
     * @return The number of possible interactions in this two-way avenue
     */
    public int getPossibleInteractions()
    {
        return possibleInteractions;
    }

}

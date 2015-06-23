package com.samvbeckmann.parity;

/**
 * Defines a connection possible by a community
 * Should only be used by {@link Community}
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class OneWayConnection
{
    /**
     * The community to which the connection is directed
     */
    private Community community;

    /**
     * The number of possible interactions for the community
     */
    private int possibleInteractions;

    /**
     * @return The community to which the connection is directed
     */
    public Community getCommunity()
    {
        return community;
    }

    /**
     * @param community The community to which the connection is directed
     */
    public void setCommunity(Community community)
    {
        this.community = community;
    }

    /**
     * @return The number of possible interactions for this connection
     */
    public int getPossibleInteractions()
    {
        return possibleInteractions;
    }

    /**
     * @param possibleInteractions The number of possible interactions for this connection
     */
    public void setPossibleInteractions(int possibleInteractions)
    {
        this.possibleInteractions = possibleInteractions;
    }
}

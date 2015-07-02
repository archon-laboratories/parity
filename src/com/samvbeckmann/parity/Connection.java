package com.samvbeckmann.parity;

/**
 * Defines a connection between two communities.
 * Should only be used by an {@link IInteractionHandler}
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class Connection
{
    /**
     * {@link Community} (1) in the interaction
     */
    private final Community thisCommunity;

    /**
     * {@link Community} (2) in the interaction
     */
    private final Community otherCommunity;

    /**
     * The number of possible interactions in this two-way avenue
     */
    private final int possibleInteractions;

    public Connection(Community com1, Community com2, int possibleInteractions)
    {
        this.thisCommunity = com1;
        this.otherCommunity = com2;
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
     * @return other community
     */
    public Community getOtherCommunity()
    {
        return otherCommunity;
    }

    /**
     * @return The number of possible interactions in this two-way avenue
     */
    public int getPossibleInteractions()
    {
        return possibleInteractions;
    }

}

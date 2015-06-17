package com.samvbeckmann.parity;

/**
 * Defines a connection between two communities.
 * Should only be used by an {@link IInteractionHandler}
 *
 * @author Sam Beckmann
 */
public class TwoWayConnection
{
    private Community community1;
    private Community community2;
    private int possibleInteractions;

    public TwoWayConnection(Community com1, Community com2, int possibleInteractions)
    {
        this.community1 = com1;
        this.community2 = com2;
        this.possibleInteractions = possibleInteractions;
    }

    public Community getCommunity1()
    {
        return community1;
    }

    public Community getCommunity2()
    {
        return community2;
    }

    public int getPossibleInteractions()
    {
        return possibleInteractions;
    }

}

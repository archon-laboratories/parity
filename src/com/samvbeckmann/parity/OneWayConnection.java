package com.samvbeckmann.parity;

/**
 * Defines a connection possible by a community
 * Should only be used by {@link Community}
 *
 * @author Sam Beckmann
 */
public class OneWayConnection
{
    private Community community;
    private int possibleInteractions;

    public Community getCommunity()
    {
        return community;
    }

    public void setCommunity(Community community)
    {
        this.community = community;
    }

    public int getPossibleInteractions()
    {
        return possibleInteractions;
    }

    public void setPossibleInteractions(int possibleInteractions)
    {
        this.possibleInteractions = possibleInteractions;
    }
}

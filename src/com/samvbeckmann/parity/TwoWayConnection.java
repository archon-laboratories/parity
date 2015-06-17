package com.samvbeckmann.parity;

/**
 * parity, class made on 6/16/2015.
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
        this.community1 = com2;
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

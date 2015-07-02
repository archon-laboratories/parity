package main.java.com.samvbeckmann.parity;

import main.java.com.samvbeckmann.parity.Community;
import main.java.com.samvbeckmann.parity.IInteractionHandler;

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
    private Community community1;

    /**
     * {@link Community} (2) in the interaction
     */
    private Community community2;

    /**
     * The number of possible interactions in this two-way avenue
     */
    private int possibleInteractions;

    public Connection(Community com1, Community com2, int possibleInteractions)
    {
        this.community1 = com1;
        this.community2 = com2;
        this.possibleInteractions = possibleInteractions;
    }

    /**
     * @return community 1
     */
    public Community getCommunity1()
    {
        return community1;
    }

    /**
     * @return community2
     */
    public Community getCommunity2()
    {
        return community2;
    }

    /**
     * @return The number of possible interactions in this two-way avenue
     */
    public int getPossibleInteractions()
    {
        return possibleInteractions;
    }

}

package com.samvbeckmann.parity;

import java.util.Random;

/**
 * parity, class made on 6/15/2015.
 *
 * @author Sam Beckmann
 */
public abstract class AbstractAgent
{
    double opinion;

    int xPos;
    int yPos;
    static Random rnd = new Random();

    /**
     * Defines how an Agent responds in an interaction.
     *
     * @param state The state of the agent in the interaction
     * @return A response to the interaction, in the form of a choice.
     */
    abstract Choices interaction(States state);

    /**
     * @return Agent's opinion, range [0,1]
     */
    public double getOpinion()
    {
        return opinion;
    }

    protected void setOpinion(double opinion)
    {
        this.opinion = opinion;
    }

    /**
     * Updates the agents opinion after an interaction.
     *
     * @param feedback Determines if the agent is positively or negatively
     *                 reinforced for their choice in an interaction
     */
    public abstract void updateOpinion(int feedback);

    /**
     * Gets the x coordinate of the agent within its community
     */
    public int getX()
    {
        return xPos;
    }

    /**
     * Gets the y coordinate of the agent within its community
     */
    public int getY()
    {
        return yPos;
    }


    /**
     * Sets the x coordinate of the agent within its community
     *
     * @param xPos The x coordinate
     */
    protected void setX(int xPos)
    {
        this.xPos = xPos;
    }

    /**
     * Sets the y coordinate of the agent within its community
     *
     * @param yPos The y coordinate
     */
    protected void setY(int yPos)
    {
        this.yPos = yPos;
    }
}

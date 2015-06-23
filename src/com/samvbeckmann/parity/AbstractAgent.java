package com.samvbeckmann.parity;

/**
 * parity, class made on 6/15/2015.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public abstract class AbstractAgent
{
    /**
     * The opinion of the agent.
     */
    protected double opinion;

    /**
     * The xPos of the agent within its parent {@link Community}
     */
    protected int xPos;

    /**
     * The yPos of the agent within its parent {@link Community}
     */
    protected int yPos;

    /**
     * Defines how an agent responds in an interaction.
     *
     * @param state The state of the agent in the interaction
     * @return A response to the interaction, in the form of a choice.
     */
    abstract Choices interaction(States state);

    /**
     * @return agent's opinion, range [0,1]
     */
    public double getOpinion()
    {
        return opinion;
    }

    /**
     * Sets the agent's opinion.
     * For use in agent creation; not intended for use by an interaction handler.
     *
     * @param opinion The new opinion
     */
    void setOpinion(double opinion)
    {
        this.opinion = opinion;
    }

    /**
     * Updates the agent's opinion after an interaction.
     *
     * @param feedback Determines if the agent is positively or negatively
     *                 reinforced for their choice in an interaction
     */
    public abstract void updateOpinion(int feedback);

    /**
     * Gets the x coordinate of the agent within its {@link Community}
     */
    public int getX()
    {
        return xPos;
    }

    /**
     * Gets the y coordinate of the agent within its {@link Community}
     */
    public int getY()
    {
        return yPos;
    }

    /**
     * Sets the x coordinate of the agent within its {@link Community}
     *
     * @param xPos The x coordinate
     */
    protected void setX(int xPos)
    {
        this.xPos = xPos;
    }

    /**
     * Sets the y coordinate of the agent within its {@link Community}
     *
     * @param yPos The y coordinate
     */
    protected void setY(int yPos)
    {
        this.yPos = yPos;
    }
}

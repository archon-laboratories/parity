package com.samvbeckmann.parity.core;

import com.samvbeckmann.parity.demoProgram.BasicChoices;
import com.samvbeckmann.parity.demoProgram.BasicStates;

import java.util.ArrayList;

/**
 * parity, class made on 6/15/2015.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public abstract class AbstractAgent implements Cloneable
{
    /**
     * The opinion of the agent.
     */
    protected double[] opinions;

    /**
     * The xPos of the agent within its parent {@link Community}
     */
    protected int xPos;

    /**
     * The yPos of the agent within its parent {@link Community}
     */
    protected int yPos;

    /**
     * @return the opinions of the agent
     */
    public double[] getOpinions()
    {
        return opinions;
    }

    protected void setOpinions(ArrayList<Double> agentOpinions)
    {
        opinions = new double[agentOpinions.size()];
        int count = 0;
        for (double val : agentOpinions)
            opinions[count] = val;
    }

    /**
     * Defines how an agent responds in an interaction.
     *
     * @param state The state of the agent in the interaction
     * @return A response to the interaction, in the form of a choice.
     */
    protected abstract BasicChoices interaction(BasicStates state);

    /**
     * @return agent's opinion, range [0,1]
     */
    public double getOpinion()
    {
        return opinions[0];
    }

    /**
     * Sets the agent's opinion.
     * For use in agent creation; not intended for use by an interaction handler.
     *
     * @param opinion The new opinion
     */
    protected void setOpinion(double opinion)
    {
        opinions = new double[1];
        opinions[0] = opinion;
    }

    /**
     * Updates the agent's opinion after an interaction.
     *
     * @param feedback Determines if the agent is positively or negatively
     *                 reinforced for their choice in an interaction
     */
    public abstract void updateOpinions(int feedback);

    /**
     * Gets the x coordinate of the agent within its {@link Community}
     */
    public int getX()
    {
        return xPos;
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
     * Gets the y coordinate of the agent within its {@link Community}
     */
    public int getY()
    {
        return yPos;
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

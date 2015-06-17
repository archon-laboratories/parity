package com.samvbeckmann.parity;

import java.util.Random;

/**
 * A sample implementation of {@link IAgent}.
 * Use / extend this, or make your own.
 *
 * @author Sam Beckmann
 */
public class BasicAgent implements IAgent
{
    private double opinion;
    private Random rnd = new Random();

    public BasicAgent()
    {
        this(.5);
    }

    public BasicAgent(double startingOpinion)
    {
        this.opinion = startingOpinion;
    }

    public Choices interaction(States state)
    {
        return rnd.nextDouble() > opinion ? Choices.LEFT : Choices.RIGHT;
    }

    public double getOpinion()
    {
        return opinion;
    }

    public void updateOpinion(int feedback)
    {

    }
}

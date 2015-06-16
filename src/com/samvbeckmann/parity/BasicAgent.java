package com.samvbeckmann.parity;

import java.util.Random;

/**
 * parity, class made on 6/15/2015.
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

    public BasicAgent(double startOpinion)
    {
        this.opinion = startOpinion;
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

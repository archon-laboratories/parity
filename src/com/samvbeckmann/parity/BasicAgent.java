package com.samvbeckmann.parity;

/**
 * parity, class made on 6/15/2015.
 *
 * @author Sam Beckmann
 */
public class BasicAgent extends AbstractAgent
{

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

    public void updateOpinion(int feedback)
    {

    }
}
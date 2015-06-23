package com.samvbeckmann.parity;

import java.util.Random;

/**
 * A sample implementation of {@link AbstractAgent}.
 * Use / extend this, or make your own.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class BasicAgent extends AbstractAgent
{

    public BasicAgent()
    {
        this(.5);
    }

    /**
     * @param startingOpinion Sets the starting opinion of the agent
     */
    public BasicAgent(double startingOpinion)
    {
        this.opinion = startingOpinion;
    }

    /**
     * @param state The state of the agent in the interaction
     * @return The choice that the agent makes in the interaction
     */
    public Choices interaction(States state)
    {
        return Population.rnd.nextDouble() > opinion ? Choices.LEFT : Choices.RIGHT;
    }

    /**
     * The method that handles updating the agent's opinion, given the interaction handler's feedback.
     * @param feedback Determines if the agent is positively or negatively
     */
    public void updateOpinion(int feedback)
    {

    }
}

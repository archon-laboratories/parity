package com.samvbeckmann.parity.basicProgram;

import com.samvbeckmann.parity.AbstractAgent;
import com.samvbeckmann.parity.Population;

/**
 * A sample implementation of {@link AbstractAgent}. For this example, we will treat 1 is right, 0 as left.
 * Use/extend this, or make your own.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class BasicAgent extends AbstractAgent
{

    Choices prevChoice;

    public BasicAgent()
    {
        this(.5);
    }

    /**
     * @param startingOpinion Sets the starting opinion of the agent
     */
    public BasicAgent(double startingOpinion)
    {
        setOpinion(startingOpinion);
    }

    /**
     * @param state The state of the agent in the interaction
     * @return The choice that the agent makes in the interaction
     */
    public Choices interaction(States state)
    {
        if (Population.rnd.nextDouble() > getOpinion())
            prevChoice = Choices.LEFT;
        else
            prevChoice = Choices.RIGHT;

        return prevChoice;
    }

    /**
     * The method that handles updating the agent's opinion, given the interaction handler's feedback.
     *
     * @param feedback Determines if the agent is positively or negatively
     */
    public void updateOpinions(int feedback)
    {
        switch (feedback)
        {
            case 1:
                if (prevChoice == Choices.RIGHT)
                    opinions[0] = opinions[0] < 1. ? opinions[0] + .1 : opinions[0];
                else // Only two choices in this basic example
                    opinions[0] = opinions[0] > 0. ? opinions[0] - .1 : opinions[0];
                break;

            case -1:
                if (prevChoice == Choices.RIGHT)
                    opinions[0] = opinions[0] > 0. ? opinions[0] - .1 : opinions[0];
                else // Only two choices in this basic example
                    opinions[0] = opinions[0] < 1. ? opinions[0] + .1 : opinions[0];
                break;

            default:
                System.err.println("Feedback " + feedback + " not found!");
                break;
        }
    }
}
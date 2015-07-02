package com.samvbeckmann.parity.demoProgram;

import com.samvbeckmann.parity.core.AbstractAgent;
import com.samvbeckmann.parity.core.Population;

/**
 * A sample implementation of {@link AbstractAgent}. For this example, we will treat 1 is right, 0 as left.
 * Use/extend this, or make your own.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public class BasicAgent extends AbstractAgent
{

    BasicChoices prevChoice;

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
    public BasicChoices interaction(BasicStates state)
    {
        if (Population.rnd.nextDouble() > getOpinion())
            prevChoice = BasicChoices.LEFT;
        else
            prevChoice = BasicChoices.RIGHT;

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
                if (prevChoice == BasicChoices.RIGHT)
                    opinions[0] = opinions[0] < 1. ? opinions[0] + .05 : opinions[0];
                else // Only two choices in this basic example
                    opinions[0] = opinions[0] > 0. ? opinions[0] - .05 : opinions[0];
                break;

            case -1:
                if (prevChoice == BasicChoices.RIGHT)
                    opinions[0] = opinions[0] > 0. ? opinions[0] - .05 : opinions[0];
                else // Only two choices in this basic example
                    opinions[0] = opinions[0] < 1. ? opinions[0] + .05 : opinions[0];
                break;

            default:
                System.err.println("Feedback " + feedback + " not found!");
                break;
        }

        // Just some clipping, for nice pretty numbers.
        // opinions[0] = Math.round(opinions[0]*100)/100.;
    }
}

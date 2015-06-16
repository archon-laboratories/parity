package com.samvbeckmann.parity;

/**
 * parity, class made on 6/15/2015.
 *
 * @author Sam Beckmann
 */
public interface IAgent
{
    /**
     * Defines how an Agent responds in an interaction.
     * @param state The state of the agent in the interaction
     *
     * @return A response to the interaction, in the form of a choice.
     */
    Choices interaction(States state);

    /**
     * @return Agent's opinion, range [0,1]
     */
    double getOpinion();

    /**
     * Updates the agents opinion after an interaction.
     *
     * @param feedback Determines if the agent is positively or negatively
     *                 reinforced for their choice in an interaction
     */
    void updateOpinion(int feedback);
}

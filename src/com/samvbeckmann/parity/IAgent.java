package com.samvbeckmann.parity;

/**
 * Defines an agent, which makes a decision when involved in an interaction,
 * and has an opinion.
 * A sample implementation can be found at {@link BasicAgent}.
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

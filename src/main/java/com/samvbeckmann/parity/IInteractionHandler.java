package com.samvbeckmann.parity;

import com.samvbeckmann.parity.basicProgram.BasicInteractionHandler;
import com.samvbeckmann.parity.basicProgram.Choices;

import java.util.Map;

/**
 * Defines which agents interact with each other on a given timestep,
 * and gives feedback to the agents based on their responses.
 * A sample implementation can be found at {@link BasicInteractionHandler}
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public interface IInteractionHandler
{
    /**
     * Creates of map, linking agents that will interact in this timestep.
     * Called once per timestep.
     *
     * @param p The population to find interactions for.
     *
     * @return A map, where the object and key are two agents that will
     *         interact in this timestep.
     */
    Map<AbstractAgent, AbstractAgent> determineInteractions(Population p);

    /**
     * Gives feedback to the column player based on its choice.
     * Positive feedback reinforces the choice, negative discourages it.
     * In general, higher numbers make a more meaningful effect, though
     * this is left to the discretion of the agent.
     *
     * @param columnPlayer The choice of the Column Player
     * @param rowPlayer The choice of the Row Player
     *
     * @return the feedback the agent will receive
     */
    int getColumnFeedback(Choices columnPlayer, Choices rowPlayer);

    /**
     * Gives feedback to the row player based on its choice.
     * Positive feedback reinforces the choice, negative discourages it.
     * In general, higher numbers make a more meaningful effect, though
     * this is left to the discretion of the agent.
     *
     * @param columnPlayer The choice of the Column Player
     * @param rowPlayer The choice of the Row Player
     *
     * @return the feedback the agent will receive
     */
    int getRowFeedback(Choices columnPlayer, Choices rowPlayer);
}

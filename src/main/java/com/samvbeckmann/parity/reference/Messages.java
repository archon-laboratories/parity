package com.samvbeckmann.parity.reference;

/**
 * Strings for Messages, with the intention of being displayed to the user.
 *
 * @author Sam Beckmann & Nate Beckemeyer
 */
public final class Messages
{
    /**
     * Messages displayed to user by GUI alerts.
     */
    public final class Alerts
    {
        public static final String DELETE_CONFIRM = "Deletion Confirmation";
        public static final String DELETE_CONFIRM_MESSAGE = "Do you want to delete this agent?";
    }

    /**
     * Messages printed to console when parity encounters an error.
     * Not intended to be seen in daily operation.
     */
    public final class Errors
    {
        /* Reflection */
        public static final String REGISTER_CLASS_DOES_NOT_EXTEND = "Class %s does not extend Abstract Agent, does "
                + "not implement IInteractionHandler nor ICompletionCondition.%n";

        /* Invalid Fields */
        public static final String INVALID_FIELDS = "Invalid Fields";
        public static final String INVALID_FIELDS_HEADER = "Please Correct Invalid Fields";
        public static final String INVALID_NUM_TRIALS = "Not a valid number of trials";
        public static final String INVALID_AGENT = "Not a valid agent.%n";
        public static final String INVALID_OPINION = "That's not an opinion for you to have.%n";
        public static final String INVALID_NUM_AGENTS = "Not a valid number of agents to add.%n";
    }

}

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

    public final class UI
    {
        public static final String NEW_COMMUNITY = "New Community";
    }

    /**
     * Messages printed to console when parity encounters an error.
     * Not intended to be seen in daily operation.
     */
    public final class Errors
    {
        public static final String REGISTER_METHOD_ERROR =
                "Method %s in class %s not added. Note, this method should return a List<Class>\n";

        public static final String REGISTER_AGENT_CLASS_ERROR =
                "Class %s not added. Note, this class should extend AbstractAgent.\n";
        public static final String REGISTER_AGENT_ERROR = "Error in agent register %s : %s\n";

        public static final String REGISTER_HANDLER_CLASS_ERROR =
                "Class %s not added. Note, this class should extend IInteractionHandler.\n";
        public static final String REGISTER_HANDLER_ERROR = "Error in interaction handler register %s : %s\n";

        public static final String REGISTER_COMPLETION_CLASS_ERROR =
                "Class %s not added. Note, this class should extend ICompletionCondition.\n";
        public static final String REGISTER_COMPLETION_ERROR = "Error in completion condition register %s : %s\n";

        public static final String REGISTER_CLASS_DOES_NOT_EXTEND = "Class %s does not extend Basic Agent, does "
                + "not implement IInteractionHandler nor ICompletionCondition.%n";
    }

}

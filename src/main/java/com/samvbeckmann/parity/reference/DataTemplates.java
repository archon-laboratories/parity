package com.samvbeckmann.parity.reference;

/**
 * Created for parity by @author Nate Beckemeyer on 2015-08-04.
 * Note that the the DataSaver is responsible for providing appropriate separators (','s)
 * where necessary, such as in arrays; i.e, the Community and Agent arrays.
 *
 */
public class DataTemplates
{
    /**
     * Order: <br/>
     * #iterations <br/>
     * #communities <br/>
     * The classpath of the completion condition <br/>
     * The classpath of the interaction handler <br/>
     * #opinions (1 by default) <br/>
     * Community array information (see CommunityTemplate)
     */
    public static final String populationTemplate =
            "{%n" +
                "\"population\": {%n" +
                    "\"numIterations\": %d,%n" +
                    "\"numberCommunities\": %d,%n" +
                    "\"completionCondition\": \"%s\",%n" +
                    "\"interactionHandler\": \"%s\",%n" +
                    "\"opinionCount\": %d,%n" +
                    "\"communities\": [%n%s]" +
                "}%n" +
            "}";


    /**
     * Order: <br/>
     * Id - location in the array <br/>
     * #neighbors <br/>
     * neighbours array <br/>
     * #agents <br/>
     * agents array <br/>
     */
    public static final String communityTemplate =
            "{%n" +
                    "\"id\": %d,%n" +
                    "\"numNeighbours\": %d,%n" +
                    "\"neighbours\": [%n%s],%n" +
                    "\"agentCount\": %d,%n" +
                    "\"agents\": [%n%s]" +
            "}";

    /**
     * Order: <br/>
     * #cross community interactions with that community <br/>
     * Neighbor ID <br/>
     */
    public static final String neighbourTemplate = "[%d, %d]";

    /**
     * Order: <br/>
     * The classpath of the agent <br/>
     * The x position of the agent (incremental by default) <br/>
     * The y position of the agent (incremental by default) <br/>
     * The opinions array of the agent <br/>
     */
    public static final String agentTemplate =
            "{%n" +
                    "\"sourceFile\": \"%s\",%n" +
                    "\"xPos\": %d,%n" +
                    "\"yPos\": %d,%n" +
                    "\"opinions\": [%n%s]" +
            "}";

    /**
     * Order: <br/>
     * The ith opinion <br/>
     */
    public static final String opinionTemplate = "%f";

    /**
     * Order: <br/>
     * Average Opinion <br/>
     * Final Timestep
     */
    public static final String resultTemplate = "%f %d%n";
}

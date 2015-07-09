package com.samvbeckmann.parity.utilities;

import com.samvbeckmann.parity.model.AgentModel;

/**
 * parity, class made on 2015-06-26
 *
 * @author Sam Beckmann
 */
public class ModelHelper
{
    /**
     * Formats stats of an {@link AgentModel} to a readable String.
     *
     * @param agent AgentModel
     * @return String of formatted agent stats
     */
    public static String getAgentStatsAsString(AgentModel agent) // TODO generify
    {
        String s = "";
        s += agent.getClasspath();
        s += "\n";
        s += agent.getOpinion();
        return s;
    }
}
